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

package org.openatom.ubml.externalapi.runtime.core.jit.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.apache.commons.io.IOUtils;

/**
 * The unzip manager
 *
 * @author haozhibei
 */
public class UnZipManager {

    private static Object lockObj = new Object();
    private static List<String> UNZIPED_LIST = new ArrayList<>();

    public static List<String> unZip(List<String> jarNames) throws IOException {
        List<String> realyJars = new ArrayList<>();
        List<String> needJars = getNeedJars(jarNames, realyJars);
        if (needJars.size() == 0) {
            return realyJars;
        }
        synchronized (lockObj) {
            JarFile jar = new JarFile(getBootJarDirectory());
            Enumeration<JarEntry> entryEnumeration = jar.entries();
            List<JarEntry> entries = new ArrayList<>();
            while (entryEnumeration.hasMoreElements()) {
                entries.add(entryEnumeration.nextElement());
            }
            final String entryLibPrefix = "BOOT-INF/lib/";
            String bootlibs = DirectoryManager.getUnZipBootJarDirectory();

            for (String jarPrefixName : needJars) {
                String entryNamePrefix = entryLibPrefix + jarPrefixName;
                boolean isFind = false;
                for (JarEntry entry : entries) {
                    String entryName = entry.getName();
                    if (entryName.startsWith(entryNamePrefix) && entryName.endsWith(".jar")) {
                        //找到后保存jar包
                        String jarFileName = entryName.substring(entryLibPrefix.length());
                        File jarFile = new File(bootlibs, jarFileName);
                        if (!jarFile.exists()) {
                            try {
                                FileOutputStream fos = new FileOutputStream(jarFile);
                                IOUtils.copy(jar.getInputStream(entry), fos);
                                fos.close();
                            } catch (Exception ex) {
                                throw new RuntimeException("spring boot unzip error:", ex);
                            }
                            //增加到缓存
                            UNZIPED_LIST.add(entryName);
                        }
                        realyJars.add(jarFileName);
                        isFind = true;
                    }
                }
                if (!isFind) {
                    throw new RuntimeException("SpringBoot无法找到【" + jarPrefixName + "】");
                }
            }
        }
        return realyJars;
    }

    /**
     * 得到未缓存的jar包。已经存在的jar包路径存入入参realyNames中。
     *
     * @param jarNames   所有需要的jar包列表
     * @param realyNames 已经存在的jar包路径。在方法中被填入值
     * @return 未缓存的jar包名称
     */
    private static List<String> getNeedJars(List<String> jarNames, List<String> realyNames) {
        List<String> needJars = new ArrayList<>();
        for (String jarName : jarNames) {
            boolean unZiped = false;
            for (String unZipName : UNZIPED_LIST) {
                if (unZipName.toLowerCase().startsWith(jarName.toLowerCase())) {
                    unZiped = true;
                    realyNames.add(unZipName);
                    //此处不break，避免引用具有相同前缀的错误的jar包
                }
            }
            if (!unZiped) {
                needJars.add(jarName);
            }
        }
        return needJars;
    }

    public static String getBootJarDirectory() {
        return DirectoryManager.getHomeDir() + File.separator + "runtime" + File.separator + "caf-bootstrap.jar";
    }

}
