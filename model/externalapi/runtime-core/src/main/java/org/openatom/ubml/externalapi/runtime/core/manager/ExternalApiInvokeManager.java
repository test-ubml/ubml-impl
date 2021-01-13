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

package org.openatom.ubml.externalapi.runtime.core.manager;

import com.fasterxml.jackson.databind.JsonNode;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import org.openatom.ubml.externalapi.runtime.core.cache.ResourceInvokerCache;
import org.openatom.ubml.model.externalapi.runtime.spi.ResourceInvoker;

/**
 * 功能描述:
 *
 * @ClassName: EapiInovkerMananger
 * @Author: Fynn Qi
 * @Date: 2020/9/2 9:53
 * @Version: V1.0
 */
public class ExternalApiInvokeManager {

    /**
     * 服务调用
     *
     * @param resourceType 资源类型
     * @param resourceId   资源ID
     * @param resourceOpId 资源操作ID
     * @param paramList    参数列表，JsonNode类型
     * @return 服务调用返回值
     */
    public static Object invokeByJsonNode(String resourceType, String resourceId, String resourceOpId, List<JsonNode> paramList) {
        ResourceInvoker invoker = getResourceInvoker(resourceType);
        return invoker.invokeByJsonNode(resourceId, resourceOpId, paramList);
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
    public static Object invokeByObject(String resourceType, String resourceId, String resourceOpId, List<Object> paramList) {
        ResourceInvoker invoker = getResourceInvoker(resourceType);
        return invoker.invokeByObject(resourceId, resourceOpId, paramList);
    }

    /**
     * 获取资源调用适配器
     *
     * @param resourceType 资源类型
     * @return 资源适配器
     */
    private static ResourceInvoker getResourceInvoker(String resourceType) {
        ResourceInvoker invoker = ResourceInvokerCache.getInstance().getResourceInvoker(resourceType);
        if (Objects.isNull(invoker)) {
            throw new RuntimeException(MessageFormat.format("获取{0}外部服务资源调用适配器失败。", resourceType));
        }
        return invoker;
    }

}
