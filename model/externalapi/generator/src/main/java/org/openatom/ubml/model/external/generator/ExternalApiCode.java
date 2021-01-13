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

package org.openatom.ubml.model.external.generator;

import java.text.MessageFormat;
import java.util.UUID;
import org.openatom.ubml.common.util.StringUtils;
import org.openatom.ubml.model.externalapi.definition.entity.ExternalApi;
import org.openatom.ubml.model.externalapi.definition.entity.Operation;

/**
 * 功EapiCode
 *
 * @Author: Fynn Qi
 * @Date: 2020/9/17 14:16
 * @Version: V1.0
 */
public class ExternalApiCode {

    private static final String EAPI_SERVICE_CONFIGURATION_FILE_NAME = "ExternalApiConfiguration";

    public static String getServiceCode(ExternalApi eapi, String packageName, String className) {
        StringBuilder sb = new StringBuilder();
        // 添加package
        sb.append(ExternalApiPackage.getPackage(packageName)).append(System.lineSeparator());
        // 添加imports
        sb.append(ExternalApiImports.getServiceImports()).append(System.lineSeparator());
        // 添加class
        sb.append(ExternalApiClass.getClass(eapi, className)).append(System.lineSeparator());
        return sb.toString();
    }

    public static String getConfigCode(
            ExternalApi metadata,
            String packageName,
            String serviceClassName,
            String serviceClassFullName,
            String sourceJavaCode) {
        // 如果传入的sourceJavaCode不为空，则追加写，否在直接写
        StringBuilder sb = new StringBuilder();
        // 直接写
        if (StringUtils.isBlank(sourceJavaCode)) {
            // 添加package
            sb.append(ExternalApiPackage.getPackage(packageName)).append(System.lineSeparator());
            // 添加imports
            sb.append(ExternalApiImports.getConfigImports()).append(System.lineSeparator());
            sb.append(getConfigClassAnnotation());
            sb.append(MessageFormat.format("public class {0} '{'", EAPI_SERVICE_CONFIGURATION_FILE_NAME))
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
            // 生成Config的构造函数
            sb.append(getEApiConfigCtorCode(metadata, serviceClassFullName))
                    .append(System.lineSeparator());
            sb.append("}");
        }
        // 追加写
        else {
            sb.append(sourceJavaCode);
            // 添加RESTEndpoint注册
            appendWirteCode(
                    MessageFormat.format(
                            "    restServiceRegistrar.registerRestEndpoint(new RESTEndpoint(\"{0}\",new {1}()));",
                            metadata.getEndPoint(), serviceClassFullName),
                    sb);
        }
        return sb.toString();
    }

    private static void appendWirteCode(String appendJavaCode, StringBuilder sb) {
        int index = sb.lastIndexOf(";");
        sb.replace(index + 1, sb.length(), System.lineSeparator() + appendJavaCode).append(System.lineSeparator());
        sb.append("  }").append(System.lineSeparator());
        sb.append("}");
    }

    private static StringBuilder getConfigClassAnnotation() {
        StringBuilder sb = new StringBuilder();
        sb.append(MessageFormat.format("@Configuration(\"{0}\")", UUID.randomUUID().toString()));
        sb.append(System.lineSeparator());
        sb.append("@Lazy(false)");
        sb.append(System.lineSeparator());
        return sb;
    }

    private static String getEndPointCode(
            ExternalApi metadata, String className, String fullClassName) {
        StringBuilder sb = new StringBuilder();
        sb.append(MessageFormat.format("  @Bean(\"{0}EndPoint\")", metadata.getId()))
                .append(System.lineSeparator());
        String camelCode = ExternalApiCompileUtils.getCamelCode(className);
        sb.append(MessageFormat.format("  public RESTEndpoint {0}EndPoint() '{'", camelCode))
                .append(System.lineSeparator());
        sb.append(
                MessageFormat.format(
                        "    return new RESTEndpoint(\"{0}\",new {1}());",
                        metadata.getEndPoint(), fullClassName))
                .append(System.lineSeparator());
        sb.append("  }").append(System.lineSeparator());
        return sb.toString();
    }

    private static String getEApiConfigCtorCode(ExternalApi metadata, String fullClassName) {
        StringBuilder sb = new StringBuilder();
        sb.append(MessageFormat.format("  public {0}() '{'", EAPI_SERVICE_CONFIGURATION_FILE_NAME))
                .append(System.lineSeparator());
        sb.append(
                "    RESTServiceRegistrar restServiceRegistrar = RESTServiceRegistrar.getSingleton();")
                .append(System.lineSeparator());
        sb.append(
                MessageFormat.format(
                        "    restServiceRegistrar.registerRestEndpoint(new RESTEndpoint(\"{0}\",new {1}()));",
                        metadata.getEndPoint(), fullClassName))
                .append(System.lineSeparator());
        sb.append("  }").append(System.lineSeparator());
        return sb.toString();
    }

    private static String getServiceBeanDeclaredCode(
            ExternalApi metadata, String className, String fullClassName) {
        StringBuilder sb = new StringBuilder();
        sb.append(MessageFormat.format("  @Bean(\"{0}{1}\")", metadata.getId(), metadata.getCode()))
                .append(System.lineSeparator());
        String camelCode = ExternalApiCompileUtils.getCamelCode(className);
        sb.append(MessageFormat.format("  public {0} {1}() '{'", fullClassName, camelCode))
                .append(System.lineSeparator());
        sb.append(MessageFormat.format("    return new {0}();", fullClassName))
                .append(System.lineSeparator());
        sb.append("  }").append(System.lineSeparator());
        return sb.toString();
    }

    private static boolean hasGlobalTransactionalAction(ExternalApi metadata) {
        if (metadata.getService() == null
                || metadata.getService().getOperations() == null
                || metadata.getService().getOperations().size() <= 0) {
            return false;
        }
        return metadata.getService().getOperations().stream().anyMatch(Operation::isTransactional);
    }
}
