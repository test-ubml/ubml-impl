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

package org.openatom.ubml.model.externalapi.devtime.core.initializer;

import java.util.UUID;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.externalapi.definition.entity.ExternalApi;
import org.openatom.ubml.model.externalapi.definition.temp.lcm.MetadataContentManager;

/**
 * 功能描述:
 *
 * @ClassName: EapiInitializer
 * @Author: Fynn Qi
 * @Date: 2020/12/24 14:54
 * @Version: V1.0
 */
public class ExternalApiInitializer implements MetadataContentManager {

    @Override
    public void build(GspMetadata metadata) {
        ExternalApi eapi = new ExternalApi();
        eapi.setId(metadata.getHeader().getId());
        eapi.setCode(metadata.getHeader().getCode());
        eapi.setName(metadata.getHeader().getName());
        eapi.setVersion("1.0");
        eapi.getService().setId(UUID.randomUUID().toString());
        eapi.setBusinessObject(metadata.getHeader().getBizObjectId());
        //TODO:后续添加SU/APP/RouteURL信息
        metadata.setContent(eapi);
    }
}
