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

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import org.opeantom.ubml.externalapi.runtime.api.ExternalApiEngine;
import org.openatom.ubml.externalapi.runtime.core.manager.ExternalApiInvokeManager;

/**
 * ExternalApiEngineImpl
 *
 * @Author: Fynn Qi
 * @Date: 2019/7/22 14:03
 * @Version: V1.0
 */
public class DefaultExternalApiEngine implements ExternalApiEngine {

    /**
     * 服务调用
     *
     * @param resourceType 资源类型
     * @param resourceId   资源ID
     * @param resourceOpId 资源操作ID
     * @param paramList    参数列表，JsonNode类型
     * @return 服务调用返回值
     */
    @Override
    public Object invokeByJsonNode(String resourceType, String resourceId, String resourceOpId, List<JsonNode> paramList) {
        return ExternalApiInvokeManager.invokeByJsonNode(resourceType, resourceId, resourceOpId, paramList);
    }

    /**
     * 服务调用
     *
     * @param resourceType 资源类型
     * @param resourceId   资源ID
     * @param resourceOpId 资源操作ID
     * @param paramList    参数列表，Object类型
     * @return 服务调用返回值
     */
    @Override
    public Object invokeByObject(String resourceType, String resourceId, String resourceOpId, List<Object> paramList) {
        return ExternalApiInvokeManager.invokeByObject(resourceType, resourceId, resourceOpId, paramList);
    }

}
