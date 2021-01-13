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

package org.openatom.ubml.model.externalapi.devtime.core.cache;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.openatom.ubml.common.spring.SpringUtils;
import org.openatom.ubml.common.util.StringUtils;
import org.openatom.ubml.mode.externalapi.devtime.spi.ExternalApiResourceProvider;
import org.openatom.ubml.mode.externalapi.devtime.spi.ResourceProvider;

/**
 * The Design-Time Cache for EApi ResourceProvider
 *
 * @Author: Fynn Qi
 * @Date: 2020/9/1 17:56
 * @Version: V1.0
 */
public class ResourceProviderCache {

    private static Map<String, ResourceProvider> providerMap = new HashMap<>();

    private static final Object LOCK_OBJ = new Object();

    private static ResourceProviderCache instance;

    public static ResourceProviderCache getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (LOCK_OBJ) {
            if (instance != null) {
                return instance;
            }
            instance = new ResourceProviderCache();
            return instance;
        }
    }

    public ResourceProvider getResourceProvider(String resType) {
        if (providerMap.containsKey(resType)) {
            return providerMap.get(resType);
        } else {
            synchronized (LOCK_OBJ) {
                if (providerMap.containsKey(resType)) {
                    return providerMap.get(resType);
                }
                Map<String, Object> resProviderMap = SpringUtils.getApplicationContext().getBeansWithAnnotation(ExternalApiResourceProvider.class);
                resProviderMap.values().stream()
                        .forEach(o -> {
                            ExternalApiResourceProvider annotation = o.getClass().getDeclaredAnnotation(ExternalApiResourceProvider.class);
                            Optional.ofNullable(annotation)
                                    .map(ExternalApiResourceProvider::resourceType)
                                    .filter(StringUtils::isNotBlank)
                                    .ifPresent(resourceType -> providerMap.put(resourceType, (ResourceProvider)o));
                        });
                return providerMap.get(resType);
            }
        }
    }
}
