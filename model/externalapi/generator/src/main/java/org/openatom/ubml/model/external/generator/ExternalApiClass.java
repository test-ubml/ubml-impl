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

import org.openatom.ubml.model.externalapi.definition.entity.ExternalApi;

/**
 * EapiClass
 *
 * @Author: Fynn Qi
 * @Date: 2020/9/17 14:06
 * @Version: V1.0
 */
public class ExternalApiClass {

    public static String getClass(ExternalApi eapi, String className) {

        StringBuilder sb = new StringBuilder();

        // 添加类注解
        sb.append(ExternalApiClassAnnotation.getEapiClassAnnotation(eapi));

        // 构建类结构
        sb.append(String.format("public class %s{", getClassName(eapi, className)))
                .append(System.lineSeparator());
        sb.append(String.format("%s", getProperties(eapi))).append(System.lineSeparator());
        sb.append(String.format("%s", getMethods(eapi))).append(System.lineSeparator());
        sb.append("}").append(System.lineSeparator());

        return sb.toString();
    }

    /**
     * 构建类名，可能存在继承的类
     *
     * @param eapi
     * @param className
     * @return
     */
    private static String getClassName(ExternalApi eapi, String className) {
        StringBuilder sb = new StringBuilder();
        if (ExternalApiExtension.hasBaseClass(eapi)) {
            sb.append(String.format("%s extends %s", className, ExternalApiExtension.getBaseClass(eapi)));
        } else {
            sb.append(className);
        }
        return sb.toString();
    }

    private static String getMethods(ExternalApi eapi) {
        return ExternalApiMethod.getMethods(eapi);
    }

    private static String getProperties(ExternalApi eapi) {
        StringBuilder sb = new StringBuilder();
        if (ExternalApiExtension.hasProperties(eapi)) {
            sb.append(ExternalApiExtension.getProperties(eapi));
        }
        return sb.toString();
    }
}
