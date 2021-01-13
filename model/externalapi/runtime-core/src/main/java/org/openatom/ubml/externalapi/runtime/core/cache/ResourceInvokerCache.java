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

package org.openatom.ubml.externalapi.runtime.core.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.openatom.ubml.common.spring.SpringUtils;
import org.openatom.ubml.common.util.StringUtils;
import org.openatom.ubml.model.externalapi.runtime.spi.ExternalApiResourceInvoker;
import org.openatom.ubml.model.externalapi.runtime.spi.ResourceInvoker;

/**
 * ResourceInvokerCache
 *
 * @Author: Fynn Qi
 * @Date: 2020/9/1 16:28
 * @Version: V1.0
 */
public class ResourceInvokerCache {

    private static Map<String, ResourceInvoker> resourceMap = new HashMap<>();

    private static final Object LOCK_OBJ = new Object();

    private static ResourceInvokerCache instance;

    public static ResourceInvokerCache getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (LOCK_OBJ) {
            if (instance != null) {
                return instance;
            }
            instance = new ResourceInvokerCache();
            return instance;
        }
    }

    public ResourceInvoker getResourceInvoker(String resType) {
        if (resourceMap.containsKey(resType)) {
            return resourceMap.get(resType);
        } else {
            synchronized (LOCK_OBJ) {
                if (resourceMap.containsKey(resType)) {
                    return resourceMap.get(resType);
                }
                Map<String, Object> resMap = SpringUtils.getApplicationContext().getBeansWithAnnotation(ExternalApiResourceInvoker.class);
                resMap.values().forEach(o -> {
                    ExternalApiResourceInvoker annotation = o.getClass().getDeclaredAnnotation(ExternalApiResourceInvoker.class);
                    Optional.ofNullable(annotation)
                            .map(ExternalApiResourceInvoker::resourceType)
                            .filter(StringUtils::isNotBlank)
                            .ifPresent(resourceType ->
                                    resourceMap.put(resourceType, (ResourceInvoker)o));
                });
                return resourceMap.get(resType);
            }
        }
    }
}
