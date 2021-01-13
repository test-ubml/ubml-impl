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

import java.util.List;
import java.util.stream.Collectors;
import org.openatom.ubml.model.externalapi.provider.viewmodel.utils.ViewModelUtils;
import org.openatom.ubml.mode.externalapi.devtime.spi.ExternalApiResourceProvider;
import org.openatom.ubml.mode.externalapi.devtime.spi.ResourceProvider;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.externalapi.definition.Constants;
import org.openatom.ubml.model.externalapi.definition.dto.Summary;
import org.openatom.ubml.model.externalapi.definition.entity.Operation;
import org.openatom.ubml.model.externalapi.definition.entity.ProviderType;

/**
 * ViewModelProvider
 *
 * @Author: Fynn Qi
 * @Date:2020/2/3 10:21
 * @Version: V1.0
 */
@ExternalApiResourceProvider(resourceType = Constants.EXTERNAL_API_RESOURCE_TYPE_VO,
        value = "org.openatom.ubml.model.externalapi.devtime.core.provider.ViewModelProvider")
public class ViewModelProvider implements ResourceProvider {

    @Override
    public List<Summary> getSummaryOperations(String resourceId) {
        GspMetadata metadata = MetadataUtils.getDtVoMetadata(resourceId);
        return ViewModelUtils.getAllSummaryOperations(metadata);
    }

    @Override
    public List<Operation> getOperations(String resourceId) {
        GspMetadata metadata = MetadataUtils.getDtVoMetadata(resourceId);
        return ViewModelUtils.getAllOperations(metadata);
    }

    @Override
    public List<Operation> getOperations(String resourceId, List<String> operationIds) {
        GspMetadata metadata = MetadataUtils.getDtVoMetadata(resourceId);
        List<Operation> operations = ViewModelUtils.getAllOperations(metadata);
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
        return builder.build(metadata, path, ProviderType.VO_ADVANCE);
    }

}
