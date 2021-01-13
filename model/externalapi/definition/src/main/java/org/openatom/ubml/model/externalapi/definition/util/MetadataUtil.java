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

package org.openatom.ubml.model.externalapi.definition.util;

import java.util.HashMap;
import org.openatom.ubml.common.util.JacksonJsonUtil;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.externalapi.definition.Constants;

/**
 * Metadata Util for EApi.
 *
 * @author zhangweiqing.
 */
public class MetadataUtil {

    /**
     * 判断是否是Eapi元数据
     */
    public static boolean isEApiMetadata(GspMetadata metadata) {
        if (metadata == null) {
            return false;
        }
        return Constants.EXTERNAL_API_METADATA_TYPE.equals(metadata.getHeader().getType());
    }

    public static String getEApiMetadataVersion(GspMetadata metadata) {
        if (metadata == null) {
            return "null";
        }
        String extendProperty = metadata.getExtendProperty();
        if (extendProperty == null || extendProperty.isEmpty()) {
            return "null";
        }
        try {
            HashMap properties = JacksonJsonUtil.toObject(extendProperty, HashMap.class);
            if (properties == null) {
                return "null";
            }
            Object version = properties.get("metadataVersion");
            return String.valueOf(version);
        } catch (Exception e) {
            return "un_known";
        }
    }
}
