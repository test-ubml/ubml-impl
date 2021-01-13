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

package org.openatom.ubml.model.externalapi.devtime.core.refer;

import java.util.List;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.common.definition.entity.MetadataReference;
import org.openatom.ubml.model.externalapi.definition.temp.lcm.IMetadataReferenceManager;

/**
 * 功能描述:
 *
 * @ClassName: EapiRefConstraint
 * @Author: Fynn Qi
 * @Date: 2021/1/4 10:54
 * @Version: V1.0
 */
public class ExternalApiReferenceConstraint implements IMetadataReferenceManager {

    @Override
    public List<MetadataReference> getConstraint(GspMetadata metadata) {
        return metadata.getRefs();
    }

}
