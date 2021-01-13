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

package org.openatom.ubml.model.externalapi.runtime.spi;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

/**
 * ResourceInvoker
 *
 * @Author: Fynn Qi
 * @Date: 2019/7/188:56
 * @Version: V1.0
 */
public interface ResourceInvoker {

    /**
     * 资源调用
     *
     * @param resourceId   资源ID
     * @param resourceOpId 资源操作的ID
     * @param paramList    参数列表，所有的参数都必须按照调用操作的参数顺序传参，不能打乱顺序
     * @return 返回值
     */
    Object invokeByJsonNode(final String resourceId, final String resourceOpId, final List<JsonNode> paramList);

    /**
     * 资源调用
     *
     * @param resourceId   资源ID
     * @param resourceOpId 资源操作的ID
     * @param paramList    参数列表，所有的参数都必须按照调用操作的参数顺序传参，不能打乱顺序
     * @return 返回值
     */
    Object invokeByObject(final String resourceId, final String resourceOpId, final List<Object> paramList);
}
