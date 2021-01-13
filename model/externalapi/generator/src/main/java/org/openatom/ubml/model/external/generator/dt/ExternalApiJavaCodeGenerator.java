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

package org.openatom.ubml.model.external.generator.dt;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Objects;
import org.openatom.ubml.common.spring.SpringUtils;
import org.openatom.ubml.common.util.StringUtils;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.external.generator.ExternalApiCompileUtils;
import org.openatom.ubml.model.externalapi.definition.temp.lcm.FileService;
import org.openatom.ubml.model.externalapi.definition.temp.lcm.GenerateService;
import org.openatom.ubml.model.externalapi.definition.temp.lcm.JitAction;
import org.openatom.ubml.model.externalapi.definition.temp.lcm.JitContext;
import org.openatom.ubml.model.externalapi.definition.entity.ExternalApi;
import org.openatom.ubml.model.externalapi.definition.util.JavaIdentifierValidator;
import org.springframework.stereotype.Component;

@Component
/**
 * EapiCompile
 * @Author: Fynn Qi
 * @Date: 2019/7/26 11:29
 * @Version: V1.0
 *
 * */
public class ExternalApiJavaCodeGenerator implements JitAction {

    private static final String EAPI_SERVICE_PREFIX_PACKAGE_NAME = "EAPI_SERVICE_PREFIX_PACKAGE_NAME";

    private static final String EAPI_SERVICE_CONFIGURATION_FILE_NAME = "ExternalApiConfiguration.java";

    private static final String EAPI_SERVICE_CONFIGURATION_CLASS_NAME = "ExternalApiConfiguration";

    private static final String SPRING_FACTORY_CONFIG = "org.springframework.boot.autoconfigure" +
            ".EnableAutoConfiguration";

    private JitContext context;

    private FileService fileService = SpringUtils.getBean(FileService.class);

    @Override
    public void generateApi(JitContext jitContext) {
    }

    @Override
    public void generateCore(JitContext jitContext) {
        this.context = jitContext;
        // 元数据校验
        this.check(jitContext);
        try {
            // 获取当前Eapi元数据
            GspMetadata gspMetadata = jitContext.getMetadata();
            // 获取元数据实体
            ExternalApi metadata = (ExternalApi)gspMetadata.getContent();
            // 获取Module的Java路径
            String javaFilePath = jitContext.getCodePath();
            String packageName = getMetadataProjectPkgName(jitContext, gspMetadata);
            if (!JavaIdentifierValidator.isValidJavaIdentifierName(packageName)) {
                throw new RuntimeException(String.format("元数据包名[%s]不符合Java包路径的命名规则,请修改元数据包名", packageName));
            }
            String prefixPackageName = "";
            String prefixPackageNameCache =
                    (String)jitContext.getExtendProperty().get(EAPI_SERVICE_CONFIGURATION_FILE_NAME);
            if (StringUtils.isNotBlank(prefixPackageNameCache)) {
                // 从缓存获取命名空间
                prefixPackageName = prefixPackageNameCache;
            } else {
                // 拼接命名空间
                prefixPackageName = String.format("com.%s", packageName.toLowerCase());
                // 放入JIT上下文的自定义扩展属性
                jitContext.getExtendProperty().put(EAPI_SERVICE_CONFIGURATION_FILE_NAME, prefixPackageName);
            }
            String servicePackageName = prefixPackageName + ".rest";
            String configPackageName = prefixPackageName + ".config";
            // Rest目录下的java文件生成
            createEapiServiceFile(javaFilePath, metadata, servicePackageName);
            // Config目录下java文件生成或者追写发布的RESTEndpoint
            createConfigPathFile(javaFilePath, metadata, servicePackageName, configPackageName);
        } catch (Exception e) {
            throw new RuntimeException(
                    String.format(
                            "EApi元数据【%s】(ID:%s)编译报错：%s",
                            this.context.getMetadata().getHeader().getCode(),
                            this.context.getMetadata().getHeader().getId(),
                            e.getMessage()),
                    e);
        }
    }

    private String getMetadataProjectPkgName(JitContext jitContext, GspMetadata gspMetadata) {
        if (jitContext.getMetadataProjectInfo() == null) {
            throw new RuntimeException(
                    String.format(
                            "外部服务【%s】(ID:%s)代码生成前检验不通过：元数据接口jitContext中的MetadataProject为空",
                            gspMetadata.getHeader().getCode(), gspMetadata.getHeader().getId()));
        }
        if (jitContext.getMetadataProjectInfo().getMetadataPackageInfo() == null) {
            throw new RuntimeException(
                    String.format(
                            "外部服务【%s】(ID:%s)代码生成前检验不通过：元数据接口jitContext中的MetadataPackageHeader为空",
                            gspMetadata.getHeader().getCode(), gspMetadata.getHeader().getId()));
        }
        String packageName = jitContext.getMetadataProjectInfo().getMetadataPackageInfo().getName();
        if (StringUtils.isBlank(packageName)) {
            throw new RuntimeException(
                    String.format(
                            "外部服务【%s】(ID:%s)代码生成前检验不通过：元数据接口jitContext中MetadataPackageHeader的包名为空，无法进行代码生成",
                            gspMetadata.getHeader().getCode(), gspMetadata.getHeader().getId()));
        }
        return packageName;
    }

    /**
     * 创建Config以及config类文件
     *
     * @param javaFilePath
     * @param metadata
     * @param servicePackageName
     * @param configPackageName
     * @throws IOException
     */
    private void createConfigPathFile(String javaFilePath, ExternalApi metadata, String servicePackageName, String configPackageName) throws IOException {
        // config路径，不包含文件名
        String configPathDir = fileService.getCombinePath(javaFilePath, configPackageName.replace(".", "/"));
        if (!fileService.isDirectoryExist(configPathDir)) {
            // 创建config文件夹
            fileService.createDirectory(configPathDir);
        }
        // 创建ServiceConfig java文件
        createServiceConfigFile(metadata, servicePackageName, configPackageName, configPathDir);
        // 所有打了@Configuration标签的类都要添加到spring.factories
        writeToServiceSpringFactoriesFile(configPackageName);
    }

    private void writeToServiceSpringFactoriesFile(String configPackageName) {
        GenerateService service = SpringUtils.getBean(GenerateService.class);
        String fullClassName = String.format("%s.%s", configPackageName, EAPI_SERVICE_CONFIGURATION_CLASS_NAME);
        service.addProperties(this.context.getCodePath(), SPRING_FACTORY_CONFIG, fullClassName);
    }

    /**
     * 创建Service文件
     *
     * @param metadata
     * @param servicePackageName
     * @param configPackageName
     * @param configPathDir
     * @throws IOException
     */
    private void createServiceConfigFile(
            ExternalApi metadata,
            String servicePackageName,
            String configPackageName,
            String configPathDir)
            throws IOException {
        // 优化后，所有的Eapi服务的声明都放置在ExternalApiConfiguration.java文件中，不再使用一个Eapi服务一个Config文件的策略
        String path = fileService.getCombinePath(configPathDir, EAPI_SERVICE_CONFIGURATION_FILE_NAME);
        if (!fileService.isFileExist(path)) {
            fileService.createFile(configPathDir, EAPI_SERVICE_CONFIGURATION_FILE_NAME);
        }
        // 服务配置类源码
        String serviceName = getServiceName(metadata.getCode());
        String serviceClassFullName = String.format("%s.%s", servicePackageName, serviceName);
        String configJavaCode =
                ExternalApiDtCodeGenerator.getServiceConfigCode(
                        metadata,
                        configPackageName,
                        serviceName,
                        serviceClassFullName,
                        fileService.fileRead(path));
        // 覆盖写，不能直接追写
        fileService.fileUpdate(path, configJavaCode, false);
        // 此处兼容处理之前生成的配置文件，如果存在，就删除，如果不存在，不处理
        deleteOldConfigFile(metadata, configPathDir);
    }

    private void deleteOldConfigFile(ExternalApi metadata, String configPathDir) throws IOException {
        String oldServiceConfigName = getOldServiceConfigJavaFileName(metadata.getCode());
        String oldFilePath = fileService.getCombinePath(configPathDir, oldServiceConfigName);
        if (fileService.isFileExist(oldFilePath)) {
            fileService.fileDelete(oldFilePath);
        }
    }

    private void createEapiServiceFile(
            String javaFilePath, ExternalApi metadata, String servicePackageName) throws IOException {
        // rest路径，不包含文件名
        String restPathDir =
                fileService.getCombinePath(javaFilePath, servicePackageName.replace(".", "/"));
        if (!fileService.isDirectoryExist(restPathDir)) {
            // 创建在rest路径
            fileService.createDirectory(restPathDir);
        }
        // 创建Eapi服务名.java文件
        createServiceFile(metadata, servicePackageName, restPathDir);
    }

    private void createServiceFile(ExternalApi metadata, String packageName, String restPathDir)
            throws IOException {
        String path =
                fileService.getCombinePath(restPathDir, getServiceJavaFileName(metadata.getCode()));
        if (fileService.isFileExist(path)) {
            fileService.fileDelete(path);
        }
        fileService.createFile(restPathDir, getServiceJavaFileName(metadata.getCode()));
        // 服务源码
        String serviceJavaCode =
                ExternalApiDtCodeGenerator.getServiceCode(
                        metadata, packageName, getServiceName(metadata.getCode()), context);
        fileService.fileUpdate(path, serviceJavaCode);
    }

    private String getServiceJavaFileName(String name) {
        return MessageFormat.format("{0}.java", getServiceName(name));
    }

    private String getServiceName(String name) {
        return MessageFormat.format("{0}Service", ExternalApiCompileUtils.upperFirstChar(name));
    }

    private String getOldServiceConfigJavaFileName(String name) {
        return MessageFormat.format("{0}.java", getOldConfigName(name));
    }

    private String getOldConfigName(String name) {
        return MessageFormat.format("{0}EapiConfig", ExternalApiCompileUtils.upperFirstChar(name));
    }

    private void check(JitContext context) {
        if (Objects.isNull(context)) {
            throw new RuntimeException("传入的Context为空。");
        }
        if (Objects.isNull(context.getMetadata())) {
            throw new RuntimeException("Context中metadata为空。");
        }
        if (StringUtils.isBlank(context.getGspProjectpath())) {
            throw new RuntimeException("Context中工程路径为空。");
        }
    }

    /**
     * 生成一个Eapi+6位随机数字的文件夹，所有的Eapi代码生成产物全放置在此文件夹中，暂不使用
     *
     * @return
     */
    private String buildJavaCodeRandomFileName() {
        return String.format("eapi%s", StringUtils.getFixedLengthString(6));
    }
}
