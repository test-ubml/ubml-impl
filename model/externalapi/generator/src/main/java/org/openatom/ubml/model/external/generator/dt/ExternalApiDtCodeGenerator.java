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

import org.openatom.ubml.model.external.generator.ExternalApiChecker;
import org.openatom.ubml.model.external.generator.ExternalApiCode;
import org.openatom.ubml.model.externalapi.definition.temp.lcm.JitContext;
import org.openatom.ubml.model.externalapi.definition.entity.ExternalApi;

/**
 * 功能描述:
 *
 * @ClassName: EapiDtCodeGenerator
 * @Author: Fynn Qi
 * @Date: 2020/9/18 12:31
 * @Version: V1.0
 */
public class ExternalApiDtCodeGenerator {

    public static String getServiceCode(ExternalApi metadata, String packageName, String className, JitContext context) {

        ExternalApi currentMetadata = (ExternalApi)metadata.clone();

        // 生成前校验
        ExternalApiChecker.check(currentMetadata);

        // 元数据处理
        ExternalApiDtHandler.handler(currentMetadata, context);

        // 获取代码
        return ExternalApiCode.getServiceCode(currentMetadata, packageName, className);
    }

    public static String getServiceConfigCode(
            ExternalApi metadata,
            String packageName,
            String serviceClassName,
            String serviceClassFullName,
            String sourceJavaCode) {
        return ExternalApiCode.getConfigCode(
                metadata, packageName, serviceClassName, serviceClassFullName, sourceJavaCode);
    }

}
