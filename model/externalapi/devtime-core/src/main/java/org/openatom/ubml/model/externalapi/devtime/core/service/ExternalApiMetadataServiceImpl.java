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

package org.openatom.ubml.model.externalapi.devtime.core.service;

import java.util.ArrayList;
import java.util.List;
import org.openatom.ubml.common.util.StringUtils;
import org.openatom.ubml.externalapi.devtime.api.ExternalApiMetadataService;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.externalapi.definition.Constants;
import org.openatom.ubml.model.externalapi.definition.dto.Summary;
import org.openatom.ubml.model.externalapi.definition.entity.ExternalApi;
import org.openatom.ubml.model.externalapi.definition.entity.Operation;
import org.openatom.ubml.model.externalapi.definition.entity.ProviderType;
import org.openatom.ubml.model.externalapi.devtime.core.manager.ExternalApiProviderManager;
import org.openatom.ubml.model.externalapi.devtime.core.utils.MetadataProjectUtils;

/**
 * EapiMetadataDtServiceImpl
 *
 * @Author: Fynn Qi
 * @Date: 2020/12/23 9:55
 * @Version:V1.0
 */
public class ExternalApiMetadataServiceImpl implements ExternalApiMetadataService {

    public static ExternalApiMetadataServiceImpl instance;

    static {
        instance = new ExternalApiMetadataServiceImpl();
    }

    public void publish(String metadataId) {
        GspMetadata metadata = MetadataProjectUtils.getMetadata(metadataId);
        //TODO 发布外部服务文档
    }

    @Override
    public GspMetadata create(GspMetadata metadata, String path, ProviderType providerType) {
        this.validateMetadata(metadata);
        if (StringUtils.isBlank(path)) {
            throw new RuntimeException("EAPI创建时参数校验不通过：依赖元数据的相对路径不能为空");
        }
        String type = getProviderType(providerType);
        GspMetadata eapiMetadata = ExternalApiProviderManager.instance.createEapi(metadata, path, type);
        //TODO 发布外部服务文档
        return eapiMetadata;
    }


    private String getBasePath(GspMetadata metadata) {
        ExternalApi eApi = (ExternalApi)metadata.getContent();
        return eApi.getRouter();
    }

    public List<Operation> getSummaryOperations(String resourceType, String resourceId) {
        List<Operation> operations = new ArrayList<>();
        List<Summary> summaryInfos = ExternalApiProviderManager.instance.getSummaryOperations(resourceType, resourceId);
        if (summaryInfos != null) {
            for (Summary summaryInfo : summaryInfos) {
                Operation operation = new Operation();
                operation.setId(summaryInfo.getId());
                operation.setCode(summaryInfo.getCode());
                operation.setName(summaryInfo.getName());
                operations.add(operation);
            }
        }
        return operations;
    }

    public List<Operation> getSelectOperations(String resourceType, String resourceId, List<String> operationIds) {
        return ExternalApiProviderManager.instance.getOperations(resourceType, resourceId, operationIds);
    }

    private void validateMetadata(GspMetadata metadata) {
        if (metadata == null) {
            throw new RuntimeException("参数校验不通过：元数据不能为空");
        }
        if (StringUtils.isBlank(metadata.getRelativePath())) {
            throw new RuntimeException("参数校验不通过：元数据的相对路径字段不能为空");
        }
        if (metadata.getHeader() == null) {
            throw new RuntimeException("参数校验不通过：元数据的Header字段不能为空");
        }
        if (StringUtils.isBlank(metadata.getHeader().getType())) {
            throw new RuntimeException("参数校验不通过：元数据类型字段不能为空");
        }
        if (StringUtils.isBlank(metadata.getHeader().getId())) {
            throw new RuntimeException("参数校验不通过：元数据ID字段不能为空");
        }
    }

    /**
     * 获取资源类型
     *
     * @param resourceType 资源类型
     * @return 资源类型解析结果
     */
    private String getProviderType(ProviderType resourceType) {
        switch (resourceType) {
            case VO_ADVANCE:
                return Constants.EXTERNAL_API_RESOURCE_TYPE_VO;
            case VO_SIMPLE:
                return Constants.EXTERNAL_API_RESOURCE_TYPE_VO_SIMPLE;
            default:
                throw new RuntimeException("不支持的资源类型:" + resourceType);
        }
    }
}
