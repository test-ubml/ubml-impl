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

package org.openatom.ubml.externalapi.runtime.core.serializer;

import org.openatom.ubml.common.util.JacksonJsonUtil;
import org.openatom.ubml.common.util.StringUtils;
import org.openatom.ubml.model.common.definition.entity.IMetadataContent;
import org.openatom.ubml.model.externalapi.definition.entity.ExternalApi;
import org.openatom.ubml.model.framework.spi.MetadataTransferSerializer;

/**
 * TransferSerializer
 *
 * @Author: Fynn Qi
 * @Date: 2020/12/24 15:09
 * @Version: V1.0
 */
public class TransferSerializer implements MetadataTransferSerializer {

    @Override
    public String serialize(IMetadataContent iMetadataContent) {
        return JacksonJsonUtil.toJson(iMetadataContent);
    }

    @Override
    public IMetadataContent deserialize(String s) {
        if (StringUtils.isBlank(s)) {
            return null;
        }
        return JacksonJsonUtil.toObject(s, ExternalApi.class);
    }
}
