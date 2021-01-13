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

import org.opeantom.ubml.externalapi.runtime.api.ExternalApiPublishService;
import org.openatom.ubml.externalapi.runtime.core.customize.deploy.CustomizeDeploy;
import org.openatom.ubml.externalapi.runtime.core.customize.entity.CustomizeUnit;
import org.openatom.ubml.externalapi.runtime.core.customize.entity.CustomizeUnitGetter;

/**
 * EapiRuntimeDeploymentImpl
 *
 * @Author: Fynn Qi
 * @Date: 2020/9/11 17:02
 * @Version: V1.0
 */
public class DefaultExternalApiPublishService implements ExternalApiPublishService {

    @Override
    public void publish(String metadataId) {
        CustomizeUnit unit = new CustomizeUnitGetter().fromMetadataId(metadataId);

        CustomizeDeploy.getInstance().deploy(unit);
    }

    @Override
    public void unpublish(String metadataId) {
        CustomizeDeploy.getInstance().removeDeploy(metadataId);
    }
}
