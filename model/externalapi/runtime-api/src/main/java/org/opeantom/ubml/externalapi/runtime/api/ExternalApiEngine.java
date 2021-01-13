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

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

/**
 * The unified engine for external api invoking.
 *
 * @Author: Fynn Qi
 * @Date: 2019/7/22 10:33
 * @Version: V1.0
 */
public interface ExternalApiEngine {

    /**
     * Invoke EApi service using JSON payload
     *
     * @param resourceType Type of EApi adaptation resources
     * @param resourceId   resourceId
     * @param resourceOpId resourceOpId
     * @param paramList    list of param(json type)
     * @return the result of invoke
     */
    Object invokeByJsonNode(String resourceType, String resourceId, String resourceOpId, List<JsonNode> paramList);

    /**
     * Invoke EApi service using Object payload
     *
     * @param resourceType Type of EApi adaptation resources
     * @param resourceId   resourceId
     * @param resourceOpId resourceOpId
     * @param paramList    list of param(object type)
     * @return the result of invoke
     */
    Object invokeByObject(String resourceType, String resourceId, String resourceOpId, List<Object> paramList);

}
