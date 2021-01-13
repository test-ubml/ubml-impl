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

package org.openatom.ubml.model.framework.spi;

import org.openatom.ubml.model.common.definition.entity.IMetadataContent;

/**
 * Classname MetadataTransferSerializer
 * Description 元数据调用的序列化器，转换成各元数据实体
 * Date 2019/11/18 8:50
 *
 * @author zhongchq
 * @version 1.0
 */
public interface MetadataTransferSerializer {

    /***
     * @author zhongchq
     * 序列化元数据时根据元数据类型读取各元数据配置，根据各元数据序列化方式实现序列化元数据
     * @param metadataContent  元数据
     * @return java.lang.String
     * 序列化后的字符串
     **/
    String serialize(IMetadataContent metadataContent);

    /***
     * @author zhongchq
     * 元数据反序列化
     * 反序列化元数据时根据元数据类型读取各元数据配置，根据各元数据序列化方式实现反序列化元数据
     * @param contentString
     * 元数据序列化串
     * @return com.inspur.edp.lcm.metadata.api.IMetadataContent
     **/
    IMetadataContent deserialize(String contentString);
}
