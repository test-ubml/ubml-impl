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
import org.openatom.ubml.common.spring.SpringUtils;

/**
 * 代码编译调用的Client，已被EApi生成代码引用，请勿修改
 *
 * @ClassName: ServiceInvoker
 * @Author: Fynn Qi
 * @Date: 2020/2/4 10:02
 * @Version: V1.0
 */
public class ExternalApiInvokeAgent {

    public static Object invokeByJsonNode(String resourceType, String resourceId, String resourceOpId, List<JsonNode> paramList) {
        ExternalApiEngine externalApiEngine = SpringUtils.getBean(ExternalApiEngine.class);
        return externalApiEngine.invokeByJsonNode(resourceType, resourceId, resourceOpId, paramList);
    }

    public static Object invokeByObject(String resourceType, String resourceId, String resourceOpId, List<Object> paramList) {
        ExternalApiEngine externalApiEngine = SpringUtils.getBean(ExternalApiEngine.class);
        return externalApiEngine.invokeByObject(resourceType, resourceId, resourceOpId, paramList);
    }
}
