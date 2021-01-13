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

package org.openatom.ubml.model.externalapi.devtime.core.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.openatom.ubml.common.util.StringUtils;
import org.openatom.ubml.model.externalapi.provider.viewmodel.utils.SimpleViewModelUtils;
import org.openatom.ubml.mode.externalapi.devtime.spi.ExternalApiResourceProvider;
import org.openatom.ubml.mode.externalapi.devtime.spi.ResourceProvider;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.externalapi.definition.Constants;
import org.openatom.ubml.model.externalapi.definition.dto.Summary;
import org.openatom.ubml.model.externalapi.definition.entity.Operation;
import org.openatom.ubml.model.externalapi.definition.entity.ProviderType;

/**
 * 设计时和运行时的元数据定义先方法一个SPI的实现
 *
 * @Author: Fynn Qi
 * @Date: 2020/8/31 15:29
 * @Version: V1.0
 */
@ExternalApiResourceProvider(resourceType = Constants.EXTERNAL_API_RESOURCE_TYPE_VO_SIMPLE,
        value = "org.openatom.ubml.model.externalapi.devtime.core.provider.SimpleViewModelProvider")
public class SimpleViewModelProvider implements ResourceProvider {

    @Override
    public List<Summary> getSummaryOperations(String resourceId) {
        if (StringUtils.isBlank(resourceId)) {
            throw new RuntimeException("外部服务获取服务操作概要信息的资源ID为空");
        }
        List<Summary> summaryInfos = new ArrayList<>();
        GspMetadata metadata = MetadataUtils.getDtVoMetadata(resourceId);
        summaryInfos.addAll(SimpleViewModelUtils.getAllSummaryOperations(metadata));
        return summaryInfos;
    }

    @Override
    public List<Operation> getOperations(String resourceId) {
        if (StringUtils.isBlank(resourceId)) {
            throw new RuntimeException("外部服务获取服务操作的资源ID为空");
        }
        List<Operation> operations = new ArrayList<>();
        GspMetadata metadata = MetadataUtils.getDtVoMetadata(resourceId);
        operations.addAll(SimpleViewModelUtils.getAllOperations(metadata));
        return operations;
    }

    @Override
    public List<Operation> getOperations(String resourceId, List<String> operationIds) {
        if (StringUtils.isBlank(resourceId)) {
            throw new RuntimeException("外部服务获取服务操作的资源ID为空");
        }
        GspMetadata metadata = MetadataUtils.getDtVoMetadata(resourceId);
        List<Operation> operations = new ArrayList<>();
        operations.addAll(SimpleViewModelUtils.getAllOperations(metadata));
        if (operationIds == null || operationIds.size() <= 0) {
            return null;
        }
        // 返回过滤的服务操作
        return operations.stream()
                .filter(x -> operationIds.stream().anyMatch(id -> id.equals(x.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public GspMetadata create(GspMetadata metadata, String path) {
        ExternalApiMetadataBuilder builder = new ExternalApiMetadataBuilder();
        return builder.build(metadata, path, ProviderType.VO_SIMPLE);
    }
}
