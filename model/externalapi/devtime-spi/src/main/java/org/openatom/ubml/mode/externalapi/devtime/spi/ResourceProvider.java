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

package org.openatom.ubml.mode.externalapi.devtime.spi;

import java.util.List;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.externalapi.definition.dto.Summary;
import org.openatom.ubml.model.externalapi.definition.entity.Operation;

/**
 * 功能描述:设计时的资源适配接口
 *
 * @ClassName: ProviderService
 * @Author: Fynn Qi
 * @Date: 2020/8/26 14:42
 * @Version: V1.0
 */
public interface ResourceProvider {

    /**
     * 获取操作的概要信息集合
     *
     * @param resourceId 资源ID
     * @return 返回值
     */
    List<Summary> getSummaryOperations(String resourceId);

    /**
     * 获取操作的集合
     *
     * @param resourceId 资源ID
     * @return 返回值
     */
    List<Operation> getOperations(String resourceId);

    /**
     * 获取指定操作的集合
     *
     * @param resourceId   资源ID
     * @param operationIds 资源操作ID集合
     * @return 返回值
     */
    List<Operation> getOperations(String resourceId, List<String> operationIds);

    /**
     * 创建EAPI
     *
     * @param metadata 依赖的元数据
     * @param path     创建EAPI的目标路径
     * @return EAPI元数据
     */
    GspMetadata create(GspMetadata metadata, String path);

}
