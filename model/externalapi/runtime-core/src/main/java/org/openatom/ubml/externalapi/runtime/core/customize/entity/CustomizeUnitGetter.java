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

package org.openatom.ubml.externalapi.runtime.core.customize.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openatom.ubml.common.spring.SpringUtils;
import org.openatom.ubml.externalapi.runtime.core.jit.compile.JavaSourceCode;
import org.openatom.ubml.externalapi.runtime.core.service.ExternalApiJavaCodeHandler;
import org.openatom.ubml.externalapi.runtime.core.service.JavaCodeInfo;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.externalapi.definition.temp.lcm.CustomizationRtService;
import org.openatom.ubml.model.externalapi.definition.util.MetadataUtil;

public class CustomizeUnitGetter {

    private static CustomizationRtService customizationRtService;

    public Map<String, CustomizeUnit> fromMetadataIds(List<String> metadataIds) {
        Map<String, CustomizeUnit> units = new HashMap<>(metadataIds.size());
        for (String metadataId : metadataIds) {
            CustomizeUnit unit = fromMetadataId(metadataId);
            units.put(metadataId, unit);
        }
        return units;
    }

    public Map<String, CustomizeUnit> fromMetadatas(List<GspMetadata> metadatas) {
        Map<String, CustomizeUnit> units = new HashMap<>(metadatas.size());
        for (GspMetadata metadata : metadatas) {
            String metadataId = metadata.getHeader().getId();
            CustomizeUnit unit = fromMetadata(metadata);
            units.put(metadataId, unit);
        }
        return units;
    }

    public CustomizeUnit fromMetadata(GspMetadata metadata) {
        String metadataId = metadata.getHeader().getId();
        ExternalApiJavaCodeHandler eapiCode = getEapiCodeService();
        String endpointAddress = eapiCode.getEndPoint(metadataId);
        JavaCodeInfo serviceJava = eapiCode.getJavaCode(metadataId);

        String version = MetadataUtil.getEApiMetadataVersion(metadata);

        CustomizeUnit unit = new CustomizeUnit();
        unit.setId(metadataId);
        unit.setVersion(version);
        unit.setServiceInfo(toJavaSourceCode(serviceJava));
        unit.setEndpointAddress(endpointAddress);

        return unit;
    }

    public CustomizeUnit fromMetadataId(String metadataId) {
        GspMetadata metadata = getCustomizationRtService().getMetadata(metadataId);
        return fromMetadata(metadata);
    }


    private JavaSourceCode toJavaSourceCode(JavaCodeInfo info) {
        JavaSourceCode code = new JavaSourceCode();
        code.setFullClassName(info.getPackageName() + "." + info.getClassName());
        code.setSourceCode(info.getJavaCode());
        return code;
    }

    private static ExternalApiJavaCodeHandler getEapiCodeService() {
        return ExternalApiJavaCodeHandler.INSTANCE;
    }

    private static CustomizationRtService getCustomizationRtService() {
        if (customizationRtService == null) {
            customizationRtService = SpringUtils.getBean(CustomizationRtService.class);
        }
        return customizationRtService;
    }
}
