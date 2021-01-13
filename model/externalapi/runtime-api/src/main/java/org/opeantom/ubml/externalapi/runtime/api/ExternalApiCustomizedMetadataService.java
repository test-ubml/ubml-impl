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

package org.opeantom.ubml.externalapi.runtime.api;

import org.openatom.ubml.model.common.definition.entity.GspMetadata;

/**
 * 运行时元数据服务接口
 *
 * @ClassName: ExternalApiRuntimeCustomizationService
 * @Author: Fynn Qi
 * @Date: 2020/6/23 15:56
 * @Version: V1.0
 */
public interface ExternalApiCustomizedMetadataService {

    /**
     * 创建运行时EApi元数据
     *
     * @param voMetadata VO元数据
     * @return 元数据
     */
    GspMetadata create(GspMetadata voMetadata);

    /**
     * 根据依赖的VO元数据更新运行时Eapi元数据
     *
     * @param metadataId 更新的元数据ID
     * @param voMetadata VO元数据
     * @return 元数据
     */
    GspMetadata update(String metadataId, GspMetadata voMetadata);
}
