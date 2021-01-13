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

package org.openatom.ubml.model.externalapi.devtime.core.manager;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import org.openatom.ubml.mode.externalapi.devtime.spi.ResourceProvider;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.externalapi.definition.dto.Summary;
import org.openatom.ubml.model.externalapi.definition.entity.Operation;
import org.openatom.ubml.model.externalapi.devtime.core.cache.ResourceProviderCache;

/**
 * EapiProviderManager
 *
 * @Author: Fynn Qi
 * @Date: 2020/12/23 11:45
 * @Version: V1.0
 */
public class ExternalApiProviderManager {

    public static ExternalApiProviderManager instance;

    static {
        instance = new ExternalApiProviderManager();
    }

    public List<Summary> getSummaryOperations(String resourceType, String resourceId) {
        ResourceProvider provider = getResourceProvider(resourceType);
        return provider.getSummaryOperations(resourceId);
    }

    public List<Operation> getOperations(String resourceType, String resourceId) {
        ResourceProvider provider = getResourceProvider(resourceType);
        return provider.getOperations(resourceId);
    }

    public List<Operation> getOperations(
            String resourceType, String resourceId, List<String> operationIds) {
        ResourceProvider provider = getResourceProvider(resourceType);
        return provider.getOperations(resourceId, operationIds);
    }

    public GspMetadata createEapi(GspMetadata metadata, String path, String resourceType) {
        ResourceProvider provider = getResourceProvider(resourceType);
        return provider.create(metadata, path);
    }

    /**
     * 获取资源定义适配器
     *
     * @param resourceType
     * @return
     */
    private ResourceProvider getResourceProvider(String resourceType) {
        ResourceProvider provider = ResourceProviderCache.getInstance().getResourceProvider(resourceType);
        if (Objects.isNull(provider)) {
            throw new RuntimeException(MessageFormat.format("获取{0}外部服务定义资源适配器失败。", resourceType));
        }
        return provider;
    }
}
