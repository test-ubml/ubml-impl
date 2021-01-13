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

package org.openatom.ubml.externalapi.runtime.core.constraint;

import java.util.ArrayList;
import java.util.List;
import org.openatom.ubml.common.spring.SpringUtils;
import org.openatom.ubml.common.util.StringUtils;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.common.definition.entity.MetadataReference;
import org.openatom.ubml.model.externalapi.definition.temp.lcm.CustomizationService;
import org.openatom.ubml.model.externalapi.definition.temp.lcm.IMetadataRtReferenceManager;
import org.openatom.ubml.model.externalapi.definition.Constants;
import org.openatom.ubml.model.externalapi.definition.entity.ExternalApi;

/**
 * MetadataRtRefConstraint
 *
 * @Author: Fynn Qi
 * @Date: 2020/9/21 16:49
 * @Version: V1.0
 */
public class ExternalApiRTRefConstraint implements IMetadataRtReferenceManager {

    @Override
    public List<MetadataReference> getConstraint(GspMetadata gspMetadata) {
        if (gspMetadata == null) {
            return new ArrayList<>();
        }
        if (gspMetadata.getContent() == null
                || ((ExternalApi)gspMetadata.getContent()).getService() == null
                || StringUtils.isBlank(
                ((ExternalApi)gspMetadata.getContent()).getService().getResourceId())) {
            return gspMetadata.getRefs();
        }
        ExternalApi eapi = (ExternalApi)gspMetadata.getContent();
        if (Constants.EXTERNAL_API_RESOURCE_TYPE_VO.equals(eapi.getService().getResourceType())
                || Constants.EXTERNAL_API_RESOURCE_TYPE_VO_SIMPLE.equals(eapi.getService().getResourceType())) {
            MetadataReference reference = new MetadataReference();
            reference.setMetadata(gspMetadata.getHeader());
            GspMetadata voMetadata = SpringUtils.getBean(CustomizationService.class).getMetadata(eapi.getService().getResourceId());
            reference.setDependentMetadata(voMetadata.getHeader());
            if (gspMetadata.getRefs() == null) {
                gspMetadata.setRefs(new ArrayList<>());
            }
            gspMetadata.getRefs().add(reference);
            return gspMetadata.getRefs();
        } else {
            return gspMetadata.getRefs();
        }
    }
}
