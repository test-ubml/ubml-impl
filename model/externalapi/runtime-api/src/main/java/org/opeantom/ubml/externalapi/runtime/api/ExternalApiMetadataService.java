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

import org.openatom.ubml.model.externalapi.definition.temp.vo.DimensionInfo;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.externalapi.definition.entity.ExternalApi;

/**
 * The ExternalApiService
 *
 * @author haozhibei
 */
public interface ExternalApiMetadataService {

    /**
     * 创建运行时定制元数据
     *
     * @param parentEapiId  上一级Eapi元数据ID
     * @param voMetadata    当前层级的VO元数据
     * @param dimensionInfo 维度信息
     * @return 返回值
     */
    ExternalApi create(String parentEapiId, GspMetadata voMetadata, DimensionInfo dimensionInfo);

    /**
     * 删除元数据
     *
     * @param eapiId EApi元数据ID
     */
    void delete(String eapiId);

    /**
     * 更新元数据
     *
     * @param parentEapiId  上一级EApi元数据ID
     * @param eapiId        当前层级的EApi元数据ID
     * @param voMetadata    当前层级的VO元数据
     * @param dimensionInfo 维度信息
     */
    void update(String parentEapiId, String eapiId, GspMetadata voMetadata, DimensionInfo dimensionInfo);

    /**
     * 运行时定制元数据发布：由设计时发布为运行时
     *
     * @param eapiId EApi元数据ID
     */
    void publish(String eapiId);

}
