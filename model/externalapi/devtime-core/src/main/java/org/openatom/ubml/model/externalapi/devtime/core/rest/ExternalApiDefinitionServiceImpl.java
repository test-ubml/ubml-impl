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

package org.openatom.ubml.model.externalapi.devtime.core.rest;

import java.util.Arrays;
import java.util.List;
import org.openatom.ubml.common.util.StringUtils;
import org.openatom.ubml.model.externalapi.definition.entity.Operation;
import org.openatom.ubml.model.externalapi.devtime.core.service.ExternalApiMetadataServiceImpl;

/**
 * EapiDefinitionServiceImpl
 *
 * @Author: Fynn Qi
 * @Date: 2020/12/23 10:56
 * @Version: V1.0
 */
public class ExternalApiDefinitionServiceImpl implements ExternalApiDefinitionService {

    @Override
    public List<Operation> getOperations(
            String resourceType, String resourceId, String opIdListstr) {
        if (StringUtils.isBlank(opIdListstr)) {
            return ExternalApiMetadataServiceImpl.instance.getSummaryOperations(resourceType, resourceId);
        } else {
            List<String> ids = Arrays.asList(opIdListstr.split(","));
            return ExternalApiMetadataServiceImpl.instance.getSelectOperations(resourceType, resourceId, ids);
        }
    }

    @Override
    public void publish(String metadataId) {
        ExternalApiMetadataServiceImpl.instance.publish(metadataId);
    }
}
