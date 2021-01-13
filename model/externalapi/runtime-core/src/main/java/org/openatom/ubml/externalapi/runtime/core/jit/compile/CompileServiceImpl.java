/*
 *  Copyright 1999-2020 org.openatom.ubml Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.openatom.ubml.externalapi.runtime.core.jit.compile;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.openatom.ubml.externalapi.runtime.core.jit.common.DirectoryManager;
import org.openatom.ubml.externalapi.runtime.core.jit.common.UnZipManager;
import org.openatom.ubml.externalapi.runtime.core.jit.load.ClassLoaderManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("org.openatom.ubml.externalapi.runtime.core.jit.compile.CompileServiceImpl")
public class CompileServiceImpl implements CompileService {

    private static Logger logger = LoggerFactory.getLogger(CompileServiceImpl.class);

    private JavaCompiler compiler;

    public CompileServiceImpl() {
        logger.info("new CompileServiceImpl");
        compiler = ToolProvider.getSystemJavaCompiler();
    }

    @Override
    public void compile(CompileContext context) {
        List<String> referJars = parseClassPathList(context);

        //构造StringJavaFileObject
        List<StringJavaFileObject> javaFileObjects = new ArrayList<>();
        context.getSourceCodes().forEach(sourceCode -> {
            javaFileObjects.add(new StringJavaFileObject(sourceCode.getFullClassName(), sourceCode.getSourceCode()));
        });

        //创建目录
        String destDir = getOutDirPath(context);
        DirectoryManager.createDir(destDir);

        //编译
        try {
            compile(destDir, referJars, javaFileObjects);
        } catch (Exception e) {
            //编译出错时移除空文件夹
            //如果之前存在编译结果，则不移除
            deleteEmptyDirWithoutThrows(destDir);
            throw e;
        }

        //更新manifest.json
        ManifestUtil.updateManifest(context);

        //卸载ClassLoader
        ClassLoaderManager.unLoad(context.getFunctionType(), context.getFunctionId());
    }

    @Override
    public void batchCompile(List<CompileContext> contexts) {
        //按照类名分组，类名不同的分在同一组，相同的分在不同组
        List<List<CompileContext>> compileListGroup = buildCompileGroups(contexts);

        logger.info("bat compile {} split to {} groups", contexts.size(), compileListGroup.size());
        compileListGroup.forEach(group -> {
            //编译到默认位置（第一个compileContext目录中）
            CompileContext firstContext = group.get(0);
            String destDir = getOutDirPath(firstContext);
            DirectoryManager.createDir(destDir);

            //合并依赖
            List<String> referJars = mergeAndParseClassPathList(group);

            //合并java文件
            List<StringJavaFileObject> javaFileObjects = new ArrayList<>();
            group.forEach(context -> {
                context.getSourceCodes().forEach(sourceCode -> {
                    javaFileObjects.add(new StringJavaFileObject(sourceCode.getFullClassName(), sourceCode.getSourceCode()));
                });
            });

            //批量编译
            try {
                compile(destDir, referJars, javaFileObjects);
            } catch (Exception e) {
                //编译出错时移除空文件夹
                //如果之前存在编译结果，则不移除
                //TODO 下次修改JIT时统一提交该特性
//                deleteEmptyDirWithoutThrows(destDir);
                throw e;
            }

            //处理除第一个外的所有CompileContext
            //提取文件，将编译后的类从默认位置转移到功能包位置
            for (int i = 1; i < group.size(); i++) {
                CompileContext context = group.get(i);
                context.getSourceCodes().forEach(javaSourceCode -> {
                    String oldDirPath = getJavaSourceCodeCompileDir(firstContext, javaSourceCode);
                    String newDirPath = getJavaSourceCodeCompileDir(context, javaSourceCode);

                    File oldDir = new File(oldDirPath);
                    File newDir = new File(newDirPath);

                    String simpleClassName = getSimpleClassName(javaSourceCode);
                    String classFileName = simpleClassName + ".class";
                    String innerClassPrefixFileName = simpleClassName + "$";

                    if (!oldDir.exists()) {
                        logger.warn("目标路径【{}】不存在", oldDirPath);
                        return;
                    }
                    File[] files = oldDir.listFiles();
                    for (File file : files) {
                        if (file.isDirectory()) {
                            continue;
                        }

                        String fileName = file.getName();
                        if (fileName.equals(classFileName) || fileName.startsWith(innerClassPrefixFileName)) {
                            //转移类本身及其内部类
                            try {
                                //复制到目标位置
                                File newFile = new File(newDir, file.getName());
                                if (newFile.exists()) {
                                    newFile.delete();
                                }
                                FileUtils.moveFile(file, newFile);
                            } catch (IOException e) {
                                throw new RuntimeException(e.getMessage(), e);
                            }
                        }
                    }
                    recursiveDeleteEmptyDirWithoutThrows(oldDir);
                });
            }
        });

        //更新manifest.json
        contexts.forEach(ManifestUtil::updateManifest);

        //卸载classLoader
        contexts.forEach(context -> ClassLoaderManager.unLoad(context.getFunctionType(), context.getFunctionId()));
    }

    private void deleteEmptyDirWithoutThrows(String dirPath) {
        try {
            File dir = new File(dirPath);
            String[] list = dir.list();
            if (list != null && list.length == 0) {
                dir.delete();
            }
        } catch (Exception e) {
            logger.error("删除文件夹【" + dirPath + "】出错：" + e.getMessage(), e);
        }
    }

    /**
     * 递归删除空文件夹
     */
    private void recursiveDeleteEmptyDirWithoutThrows(File dir) {
        try {
            String[] list = dir.list();
            if (list != null && list.length == 0) {
                File parentFile = dir.getParentFile();
                dir.delete();
                recursiveDeleteEmptyDirWithoutThrows(parentFile);
            }
        } catch (Exception e) {
            logger.error("删除文件夹【" + dir.getAbsolutePath() + "】出错：" + e.getMessage(), e);
        }
    }

    private String getSimpleClassName(JavaSourceCode javaSourceCode) {
        String fullClassName = javaSourceCode.getFullClassName();
        int index = fullClassName.lastIndexOf(".");
        if (index >= 0) {
            return fullClassName.substring(index + 1);
        } else {
            return fullClassName;
        }
    }

    /**
     * 合并并解析所有的依赖
     */
    private List<String> mergeAndParseClassPathList(List<CompileContext> group) {
        Set<String> referBootSet = new HashSet<>();
        Set<String> referSet = new HashSet<>();

        group.forEach(context -> {
            if (context.getReferBoots() != null) {
                referBootSet.addAll(context.getReferBoots());
            }
            if (context.getRefers() != null) {
                referSet.addAll(context.getRefers());
            }
        });

        return parseClassPathList(new ArrayList<>(referBootSet), new ArrayList<>(referSet));
    }

    private List<List<CompileContext>> buildCompileGroups(List<CompileContext> contexts) {
        List<Set<String>> classNameSetGroup = new ArrayList<>();
        List<List<CompileContext>> compileListGroup = new ArrayList<>();
        for (CompileContext context : contexts) {
            boolean needNewGroup = true;
            for (int i = 0; i < classNameSetGroup.size(); i++) {
                Set<String> classNameSet = classNameSetGroup.get(i);
                boolean isDistinct = true;
                for (JavaSourceCode javaSourceCode : context.getSourceCodes()) {
                    if (classNameSet.contains(javaSourceCode.getFullClassName())) {
                        isDistinct = false;
                        break;
                    }
                }
                if (isDistinct) {
                    for (JavaSourceCode javaSourceCode : context.getSourceCodes()) {
                        classNameSet.add(javaSourceCode.getFullClassName());
                    }

                    compileListGroup.get(i).add(context);
                    needNewGroup = false;
                }
            }
            if (needNewGroup) {
                Set<String> classNameSet = new HashSet<>();
                List<CompileContext> compileContexts = new ArrayList<>();

                for (JavaSourceCode javaSourceCode : context.getSourceCodes()) {
                    classNameSet.add(javaSourceCode.getFullClassName());
                }
                compileContexts.add(context);

                classNameSetGroup.add(classNameSet);
                compileListGroup.add(compileContexts);

            }
        }
        return compileListGroup;
    }

    /**
     * 得到在编译上下文中，指定的Java源文件的编译结果class文件的最终存放目录。
     *
     * @param context        编译上下文
     * @param javaSourceCode 编译上下文
     * @return class文件实际存放目录
     */
    private String getJavaSourceCodeCompileDir(CompileContext context, JavaSourceCode javaSourceCode) {
        String packageName = getPackage(javaSourceCode);
        String packagePath = packageName.replace(".", File.separator);
        String rootPath = DirectoryManager.getJitDirectory(context.getFunctionType(), context.getFunctionId());
        return rootPath + File.separator + packagePath;
    }

    /**
     * 得到源文件的包名
     *
     * @param javaSourceCode java类源文件
     * @return 包名
     */
    private String getPackage(JavaSourceCode javaSourceCode) {
        String fullClassName = javaSourceCode.getFullClassName();
        int index = fullClassName.lastIndexOf('.');
        String packageName = fullClassName.substring(0, index);
        return packageName;
    }

    /**
     * 得到编译上下文的编译结果存放目录
     *
     * @param context 编译上下文
     * @return 结果存放目录
     */
    private String getOutDirPath(CompileContext context) {
        return DirectoryManager.getJitDirectory(context.getFunctionType(), context.getFunctionId());
    }

    /**
     * 解析所有的依赖jar包，将其转换为jar包的绝对路径列表。
     *
     * @param context 编译上下文
     * @return context的依赖jar包的绝对路径
     */
    private List<String> parseClassPathList(CompileContext context) {
        List<String> refers = context.getRefers();
        List<String> referBoots = context.getReferBoots();
        List<String> referJars = parseClassPathList(referBoots, refers);
        return referJars;
    }

    /**
     * 解析所有的依赖jar包，将其转换为jar包的绝对路径列表。
     *
     * @param referBoots
     * @param refers
     * @return
     */
    private List<String> parseClassPathList(List<String> referBoots, List<String> refers) {
        List<String> referJars = new ArrayList<>();
        //jar包解压
        if (referBoots != null && referBoots.size() > 0) {
            try {
                List<String> zips = UnZipManager.unZip(referBoots);
                zips.forEach(name -> {
                    referJars.add(DirectoryManager.getUnZipBootJarDirectory() + File.separator + name);
                });
            } catch (Exception ex) {
                throw new RuntimeException("编译时解压boot包出错：", ex);
            }
        }
        if (refers != null && refers.size() > 0) {
            refers.forEach(name -> {
                referJars.add(DirectoryManager.getHomeDir() + File.separator + name);
            });
        }
        return referJars;
    }

    public void compile(String destDir, List<String> refers, List<? extends JavaFileObject> javaFiles) {
        DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnosticCollector, null, null);
        String classPath = StringUtils.join(refers, File.pathSeparator);
        List<String> options = new ArrayList<>();
        options.add("-d");
        options.add(destDir);
        options.add("-classpath");
        options.add(classPath);

        logger.info("compile outPath: {}", destDir);
        logger.info("compile classPath: {}", classPath);
        boolean isSuccess = compiler.getTask(null, fileManager, diagnosticCollector, options, null, javaFiles).call();
        logger.info("compile end: {}", isSuccess);

        try {
            fileManager.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        if (!isSuccess) {
            List<Diagnostic<? extends JavaFileObject>> compileError = diagnosticCollector.getDiagnostics();
            String errorInfo = getCompileErrorInfo(compileError);
            logCompileErrorClassSourceCode(compileError);
            throw new RuntimeException("编译Java文件出错:" + errorInfo);
        }
    }

    private String getCompileErrorInfo(List<Diagnostic<? extends JavaFileObject>> compileError) {
        Map<String, List<String>> javaLinesMap = new HashMap<>();
        StringBuilder compileErrorRes = new StringBuilder();
        for (Diagnostic<? extends JavaFileObject> diagnostic : compileError) {
            compileErrorRes.append("lineNumber:").append(diagnostic.getLineNumber()).append(System.lineSeparator());
            compileErrorRes.append("columnNumber:").append(diagnostic.getColumnNumber()).append(System.lineSeparator());
            try {
                Object source = diagnostic.getSource();
                if (source instanceof StringJavaFileObject) {
                    String className = ((StringJavaFileObject)source).getClassName();
                    String sourceCode = ((StringJavaFileObject)source).getSourceCode();
                    compileErrorRes.append("className:").append(className).append(System.lineSeparator());
                    if (!javaLinesMap.containsKey(className)) {
                        List<String> lines = IOUtils.readLines(new StringReader(sourceCode));
                        javaLinesMap.put(className, lines);
                    }
                    List<String> lines = javaLinesMap.get(className);
                    long lineNumber = diagnostic.getLineNumber() - 1;//错误行号从1开始，减去1才是真实行号
                    if (lineNumber >= 0 && lineNumber < lines.size()) {
                        compileErrorRes.append("line:").append(lines.get((int)lineNumber)).append(System.lineSeparator());
                    }
                }
            } catch (Exception e) {
                logger.error("提取【" + diagnostic.getSource() + "】中的编译错误信息出错：" + e.getMessage(), e);
            }
            compileErrorRes.append("message:").append(diagnostic.getMessage(null)).append(System.lineSeparator());
        }
        return compileErrorRes.toString();
    }

    private void logCompileErrorClassSourceCode(List<Diagnostic<? extends JavaFileObject>> compileError) {
        Set<String> classNameSet = new HashSet<>();
        StringBuilder compileErrorRes = new StringBuilder();
        for (Diagnostic<? extends JavaFileObject> diagnostic : compileError) {
            Object source = diagnostic.getSource();
            if (source instanceof StringJavaFileObject) {
                String className = ((StringJavaFileObject)source).getClassName();
                String sourceCode = ((StringJavaFileObject)source).getSourceCode();
                if (!classNameSet.contains(className)) {
                    classNameSet.add(className);
                    compileErrorRes.append("类名：").append(className).append(System.lineSeparator());
                    compileErrorRes.append(sourceCode).append(System.lineSeparator());
                }
            }
        }
        logger.error("编译Java文件出错，涉及的类源码有：" + System.lineSeparator() + compileErrorRes.toString());
    }

}
