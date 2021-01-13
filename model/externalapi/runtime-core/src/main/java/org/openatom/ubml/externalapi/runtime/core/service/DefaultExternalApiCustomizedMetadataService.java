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

package org.openatom.ubml.externalapi.runtime.core.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.opeantom.ubml.externalapi.runtime.api.ExternalApiCustomizedMetadataService;
import org.openatom.ubml.common.spring.SpringUtils;
import org.openatom.ubml.common.util.JacksonJsonUtil;
import org.openatom.ubml.common.util.StringUtils;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.common.definition.entity.MetadataHeader;
import org.openatom.ubml.model.externalapi.definition.Constants;
import org.openatom.ubml.model.externalapi.definition.entity.ExternalApi;
import org.openatom.ubml.model.externalapi.definition.entity.Model;
import org.openatom.ubml.model.externalapi.definition.entity.Operation;
import org.openatom.ubml.model.externalapi.definition.entity.Parameter;
import org.openatom.ubml.model.externalapi.definition.entity.Service;
import org.openatom.ubml.model.externalapi.definition.temp.lcm.CustomizationService;
import org.openatom.ubml.model.externalapi.definition.temp.vo.GspViewModel;
import org.openatom.ubml.model.externalapi.definition.temp.vo.ViewModelAction;
import org.openatom.ubml.model.externalapi.provider.viewmodel.utils.ViewModelUtils;

/**
 * EapiMetadataRtServiceImpl
 *
 * @Author: Fynn Qi
 * @Date: 2020/6/23 16:04
 * @Version: V1.0
 */
public class DefaultExternalApiCustomizedMetadataService implements ExternalApiCustomizedMetadataService {

    @Override
    public GspMetadata create(GspMetadata voMetadata) {
        voValidator(voMetadata);
        GspMetadata metadata = initMetadata(voMetadata);
        metadata.setExtendProperty(buildExtendProperty(metadata));
        SpringUtils.getBean(CustomizationService.class).save(metadata);
        return metadata;
    }

    @Override
    public GspMetadata update(String metadataId, GspMetadata voMetadata) {
        voValidator(voMetadata);
        GspMetadata metadata = updateMetadata(voMetadata, metadataId);
        metadata.setExtendProperty(buildExtendProperty(metadata));
        SpringUtils.getBean(CustomizationService.class).save(metadata);
        return metadata;
    }

    private String buildExtendProperty(GspMetadata metadata) {
        ObjectNode node = JacksonJsonUtil.getMapper().createObjectNode();
        node.put("metadataVersion", UUID.randomUUID().toString());
        node.put("resourceType", ((ExternalApi)metadata.getContent()).getService().getResourceType());
        return JacksonJsonUtil.toJson(node);
    }

    private GspMetadata initMetadata(GspMetadata voMetadata) {
        GspMetadata metadata = new GspMetadata();
        buildMetadataHeader(voMetadata, metadata);
        buildMetadataContent(voMetadata, metadata);
        return metadata;
    }

    private GspMetadata updateMetadata(GspMetadata voMetadata, String metadataId) {
        GspMetadata metadata = new GspMetadata();
        updateMetadataHeader(voMetadata, metadata, metadataId);
        buildMetadataContent(voMetadata, metadata);
        return metadata;
    }

    private void buildMetadataContent(GspMetadata voMetadata, GspMetadata metadata) {
        GspViewModel vo = (GspViewModel)voMetadata.getContent();

        ExternalApi eapi = new ExternalApi();

        // 基本信息
        eapi.setId(metadata.getHeader().getId());
        eapi.setCode(voMetadata.getHeader().getCode());
        eapi.setName(voMetadata.getHeader().getName());
        eapi.setBusinessObject(voMetadata.getHeader().getBizObjectId());
        // TODO:add su、app
        eapi.setApplication(null);
        eapi.setMicroServiceUnit(null);
        eapi.setBaseHttpPath(voMetadata.getHeader().getCode().toLowerCase());
        eapi.setVersion("1.0");

        // 服务定义
        eapi.setService(new Service());
        eapi.getService().setId(UUID.randomUUID().toString());
        eapi.getService().setResourceId(voMetadata.getHeader().getId());
        eapi.getService().setResourceCode(voMetadata.getHeader().getCode());
        eapi.getService().setResourceName(voMetadata.getHeader().getName());
        eapi.getService().setResourceType(Constants.EXTERNAL_API_RESOURCE_TYPE_VO);
        eapi.getService().setResourceCode(vo.getGeneratedConfigID());
        eapi.getService().setResourceName(voMetadata.getHeader().getName());
        eapi.getService().setOperations((ArrayList<Operation>)getEapiOperations(voMetadata));

        // 引用模型
        getRefModels(eapi, vo);

        metadata.setContent(eapi);
    }

    private List<Operation> getEapiOperations(GspMetadata voMetadata) {
        return ViewModelUtils.getAllOperations(voMetadata);
    }

    private void buildMetadataHeader(GspMetadata voMetadata, GspMetadata metadata) {
        metadata.setHeader(new MetadataHeader());
        metadata.getHeader().setId(UUID.randomUUID().toString());
        metadata.getHeader().setNameSpace(voMetadata.getHeader().getNameSpace());
        metadata.getHeader().setCode(voMetadata.getHeader().getCode());
        metadata.getHeader().setName(voMetadata.getHeader().getName());
        metadata.getHeader().setBizObjectId(voMetadata.getHeader().getBizObjectId());
        metadata.getHeader().setType(Constants.EXTERNAL_API_METADATA_TYPE);
        metadata.getHeader().setExtendable(voMetadata.getHeader().isExtendable());
    }

    private void updateMetadataHeader(GspMetadata voMetadata, GspMetadata metadata, String metadataId) {
        metadata.setHeader(new MetadataHeader());
        metadata.getHeader().setId(metadataId);
        metadata.getHeader().setNameSpace(voMetadata.getHeader().getNameSpace());
        metadata.getHeader().setCode(voMetadata.getHeader().getCode());
        metadata.getHeader().setName(voMetadata.getHeader().getName());
        metadata.getHeader().setBizObjectId(voMetadata.getHeader().getBizObjectId());
        metadata.getHeader().setType(Constants.EXTERNAL_API_METADATA_TYPE);
        metadata.getHeader().setExtendable(voMetadata.getHeader().isExtendable());
    }

    private void getRefModels(ExternalApi eapi, GspViewModel viewModel) {
        eapi.setModels(new ArrayList<>());
        if (eapi.getService() == null
                || eapi.getService().getOperations() == null
                || eapi.getService().getOperations().size() <= 0) {
            return;
        }
        for (Operation operation : eapi.getService().getOperations()) {
            if (operation.getParameters() == null || operation.getParameters().size() <= 0) {
                continue;
            }
            for (Parameter param : operation.getParameters()) {
                // 先判断是否为基本类型，基本类型字段控制这接下来的所有的模型解析过程
                if (param.isPrimitiveType()) {
                    continue;
                }
                Model refModel = getModelProperty(param);
                if (StringUtils.isBlank(refModel.getModelId())) {
                    throw new RuntimeException(
                            MessageFormat.format("服务操作{0}的参数{1}的modelId为空", operation.getCode(), param.getCode()));
                }
                if ("object".equals(refModel.getModelId())) {
                    continue;
                }
                if (eapi.getModels().size() <= 0) {
                    eapi.getModels().add(refModel);
                } else
                    if (eapi.getModels().size() > 0) {
                        long count =
                                eapi.getModels().stream().filter(x -> x.getId().equals(refModel.getId())).count();
                        if (count <= 0) {
                            eapi.getModels().add(refModel);
                        }
                    }
            }
        }
    }

    private Model getModelProperty(Parameter param) {
        Model model = new Model();
        model.setId(param.getStructuredTypeRefId());
        model.setCode(param.getRefCode());
        model.setName(param.getRefName());
        model.setModelId(param.getModelId());
        model.setModelCode(param.getModelCode());
        model.setModelName(param.getModelName());
        model.setModelType(param.getModelType());
        return model;
    }

    private void voValidator(GspMetadata voMetadata) {
        if (voMetadata == null) {
            throw new RuntimeException("VO元数据为空，请检查传入的VO元数据参数");
        }
        if (voMetadata.getHeader() == null) {
            throw new RuntimeException("VO元数据的Header为空,请检查传入的VO元数据参数");
        }
        if (voMetadata.getContent() == null) {
            throw new RuntimeException("VO元数据的Content为空,请检查传入的VO元数据参数");
        }
        List<ViewModelAction> actions = ((GspViewModel)voMetadata.getContent()).getActions();
        if (actions == null || actions.size() <= 0) {
            return;
        }
    }
}
