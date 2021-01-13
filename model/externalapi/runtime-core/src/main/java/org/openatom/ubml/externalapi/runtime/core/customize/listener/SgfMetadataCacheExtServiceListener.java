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

package org.openatom.ubml.externalapi.runtime.core.customize.listener;

import org.openatom.ubml.externalapi.runtime.core.customize.deploy.CustomizeDeploy;
import org.openatom.ubml.externalapi.runtime.core.customize.entity.CustomizeUnit;
import org.openatom.ubml.externalapi.runtime.core.customize.entity.CustomizeUnitGetter;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.externalapi.definition.temp.lcm.IMetadataCacheExtService;
import org.openatom.ubml.model.externalapi.definition.util.MetadataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SgfMetadataCacheExtServiceListener implements IMetadataCacheExtService {

    private final Logger logger = LoggerFactory.getLogger(SgfMetadataCacheExtServiceListener.class);

    @Override
    public void removeMetadataCacheExt(GspMetadata gspMetadata) {
        try {
            boolean isEapiMetadata = MetadataUtil.isEApiMetadata(gspMetadata);
            if (!isEapiMetadata) {
                //如果不是Eapi元数据更新，直接忽略
                return;
            }
            CustomizeUnit customizeUnit = new CustomizeUnitGetter().fromMetadataId(gspMetadata.getHeader().getId());
            CustomizeDeploy.getInstance().deploy(customizeUnit);
        } catch (Exception e) {
            logger.error("更新运行时定制EAPI元数据缓存失败：" + e.getMessage(), e);
        }
    }
}
