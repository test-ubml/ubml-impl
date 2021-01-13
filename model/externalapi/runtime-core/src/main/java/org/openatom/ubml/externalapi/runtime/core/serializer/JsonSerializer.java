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

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Objects;
import org.openatom.ubml.common.util.JacksonJsonUtil;
import org.openatom.ubml.model.common.definition.entity.IMetadataContent;
import org.openatom.ubml.model.externalapi.definition.entity.ExternalApi;
import org.openatom.ubml.model.framework.spi.MetadataContentSerializer;

/**
 * JsonSerializer
 *
 * @Author: Fynn Qi
 * @Date: 2019/7/26 11:32
 * @Version: V1.0
 */
public class JsonSerializer implements MetadataContentSerializer {

    /**
     * 序列化
     *
     * @param iMetadataContent
     * @return
     */
    @Override
    public JsonNode Serialize(IMetadataContent iMetadataContent) {
        if (Objects.isNull(iMetadataContent)) {
            return null;
        }
        ExternalApi metadata = (ExternalApi)iMetadataContent;
        JsonNode node = JacksonJsonUtil.getMapper().convertValue(metadata, JsonNode.class);
        return node;
    }

    /**
     * 反序列化
     *
     * @param jsonNode
     * @return
     */
    @Override
    public IMetadataContent DeSerialize(JsonNode jsonNode) {
        if (Objects.isNull(jsonNode)) {
            return null;
        }
        return JacksonJsonUtil.toObject(jsonNode, ExternalApi.class);
    }
}
