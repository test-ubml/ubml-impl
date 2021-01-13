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

package org.openatom.ubml.externalapi.devtime.api;

import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.externalapi.definition.entity.ProviderType;

/**
 * Eapi设计时元数据接口服务
 *
 * @Author: Fynn Qi
 * @Date: 2020/12/23 9:28
 * @Version: V1.0
 */
public interface ExternalApiMetadataService {

    /**
     * 创建元数据
     *
     * @param metadata     依赖的元数据
     * @param path         EAPI需要创建的路径
     * @param providerType 外部服务provider类型
     * @return 外部服务元数据实体
     */
    GspMetadata create(GspMetadata metadata, String path, ProviderType providerType);

}
