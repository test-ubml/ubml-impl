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

package org.openatom.ubml.externalapi.runtime.core.service;

import java.text.MessageFormat;
import org.openatom.ubml.common.spring.SpringUtils;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.external.generator.ExternalApiRtCodeGenerator;
import org.openatom.ubml.model.externalapi.definition.entity.ExternalApi;
import org.openatom.ubml.model.externalapi.definition.temp.lcm.CustomizationRtService;
import org.openatom.ubml.model.externalapi.definition.util.JavaIdentifierValidator;

/**
 * ExternalApiJavaCodeHandler
 *
 * @Author: Fynn Qi @Date: 2020/2/4
 * 10:22 @Version: V1.0
 */
public class ExternalApiJavaCodeHandler {


    public static final ExternalApiJavaCodeHandler INSTANCE = new ExternalApiJavaCodeHandler();

    /**
     * Get the Endpoint of eapi
     *
     * @param metadataId eapi id
     * @return endpoint path
     */
    public String getEndPoint(String metadataId) {
        GspMetadata metadata = getRtMetadata(metadataId);
        ExternalApi eApi = (ExternalApi)metadata.getContent();
        return eApi.getEndPoint();
    }

    /**
     * Gets the generated java code of eapi.
     *
     * @param metadataId eapi id
     * @return java code string
     */
    public JavaCodeInfo getJavaCode(String metadataId) {
        GspMetadata metadata = getRtMetadata(metadataId);
        if (metadata == null) {
            throw new RuntimeException(MessageFormat.format("根据ID{0}获取的EApi元数据为空", metadataId));
        }
        String metadataNameSpace = metadata.getHeader().getNameSpace().toLowerCase();
        if (!JavaIdentifierValidator.isValidJavaIdentifierName(metadataNameSpace)) {
            throw new RuntimeException(
                    String.format(
                            "EApi元数据%s[ID:%s]命名空间[%s]不符合Java包路径的命名规则,请检查。",
                            metadata.getHeader().getCode(), metadataId, metadataNameSpace));
        }
        // 生成Java包名
        String packageName = String.format("com.%s.rest", metadataNameSpace);
        ExternalApi eApi = (ExternalApi)metadata.getContent();
        JavaCodeInfo javaCodeInfo = new JavaCodeInfo();
        javaCodeInfo.setPackageName(packageName);
        javaCodeInfo.setClassName(String.format("%sDynamicService", eApi.getCode()));
        String javaCode = ExternalApiRtCodeGenerator.getServiceCode(eApi, packageName, javaCodeInfo.getClassName());
        javaCodeInfo.setJavaCode(javaCode);
        return javaCodeInfo;
    }

    private GspMetadata getRtMetadata(String metadataId) {
        CustomizationRtService service = SpringUtils.getBean(CustomizationRtService.class);
        GspMetadata metadata = service.getMetadata(metadataId);
        return metadata;
    }


}
