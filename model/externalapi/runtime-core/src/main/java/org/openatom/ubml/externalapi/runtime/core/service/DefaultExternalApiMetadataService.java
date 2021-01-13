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
import java.util.UUID;
import org.opeantom.ubml.externalapi.runtime.api.ExternalApiCustomizedPublishService;
import org.opeantom.ubml.externalapi.runtime.api.ExternalApiMetadataService;
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
import org.openatom.ubml.model.externalapi.definition.temp.lcm.DimensionExtendEntity;
import org.openatom.ubml.model.externalapi.definition.temp.vo.DimensionInfo;
import org.openatom.ubml.model.externalapi.definition.temp.vo.GspViewModel;
import org.openatom.ubml.model.externalapi.provider.viewmodel.utils.ViewModelUtils;

/**
 * VoApiSynchronizationImpl
 *
 * @Author: Fynn Qi
 * @Date: 2020/2/3 15:59
 * @Version: V1.0
 */
public class DefaultExternalApiMetadataService implements ExternalApiMetadataService {

    @Override
    public ExternalApi create(
            String parentEapiId, GspMetadata voMetadata, DimensionInfo dimensionInfo) {
        // 获取Parent Eapi元数据
        GspMetadata parentEapiMetadata = getEapiMetadata(parentEapiId);
        // 获取Parent Eapi元数据的实体结构
        ExternalApi parentEapiEntity = (ExternalApi)parentEapiMetadata.getContent();
        // 初始化运行时定制的扩展元数据
        GspMetadata extendEapiMetadata = new GspMetadata();
        // 获取Eapi依赖的VO元数据
        GspViewModel vo = (GspViewModel)voMetadata.getContent();
        // 说明：元数据依赖暂不设置
        // 初始化扩展Eapi的元数据Header
        extendEapiMetadata.setHeader(metadataHeaderInit(voMetadata, parentEapiMetadata));
        // 设置RelativePath，目前所有的运行时定制Eapi元数据都是存储在数据库中，已经没有使用场景
        extendEapiMetadata.setRelativePath(voMetadata.getRelativePath());
        // 运行时定制元数据设置与VO同步
        extendEapiMetadata.setExtended(voMetadata.isExtended());
        // 构建Eapi元数据实体结构
        ExternalApi eapiEntity =
                buildEapiEntity(voMetadata, dimensionInfo, extendEapiMetadata, parentEapiEntity, vo);
        // 更新Eapi元数据实体结构到Content
        extendEapiMetadata.setContent(eapiEntity);
        // 元数据扩展属性，赋值到元数据的扩展属性ExtendProperty中
        extendEapiMetadata.setExtendProperty(getEapiExtendProperty(extendEapiMetadata));
        // 构建运行时Eapi元数据定制维度扩展实体
        DimensionExtendEntity dimensionExtendEntity = buildDimensionExtendEntity(dimensionInfo, parentEapiMetadata, extendEapiMetadata);
        SpringUtils.getBean(CustomizationService.class).saveExtMetadataWithDimensions(dimensionExtendEntity);
        //TODO 临时先执行发布，后续等WEB组提交运行时定制的改造补丁后，删除此处的发布EAPI功能
        publish(eapiEntity.getId());
        return eapiEntity;
    }

    private ExternalApi buildEapiEntity(GspMetadata voMetadata, DimensionInfo dimensionInfo, GspMetadata extendEapiMetadata, ExternalApi parentEapiEntity, GspViewModel vo) {
        ExternalApi newEapiEntity = new ExternalApi();
        getBasicInfos(dimensionInfo, extendEapiMetadata, parentEapiEntity, vo, newEapiEntity);
        getServiceDefinition(voMetadata, vo, newEapiEntity);
        getRefModels(newEapiEntity, vo);
        return newEapiEntity;
    }

    private ExternalApi updateEapiEntity(GspMetadata voMetadata, ExternalApi eapiEntity, GspViewModel vo) {
        upateServiceDefinition(voMetadata, vo, eapiEntity);
        getRefModels(eapiEntity, vo);
        return eapiEntity;
    }

    private MetadataHeader metadataHeaderInit(GspMetadata voMetadata, GspMetadata parentEapiMetadata) {
        // 初始化扩展Eapi的元数据Header
        // 说明：certId现在未使用，设置为空
        MetadataHeader header = new MetadataHeader();
        // 运行时定制Eapi元数据的ID为新ID
        header.setId(UUID.randomUUID().toString());
        // 命名空间保持Parent 元数据的命名空间
        header.setNameSpace(parentEapiMetadata.getHeader().getNameSpace());
        // 编号和名称取自运行时定制扩展的VO元数据，VO的编号和名称已作出对应的扩展调整
        header.setCode(voMetadata.getHeader().getCode());
        header.setName(voMetadata.getHeader().getName());
        // 运行时定制Eapi元数据文件名称
        // 说明：目前所有的运行时定制Eapi元数据都是存储在数据库中，该文件名暂时没有使用场景，文件名称：{VO元数据编号}.eapi
        header.setFileName(String.format("%s.eapi", voMetadata.getHeader().getCode()));
        // 业务对象，元数据类型，语种，是否翻译属性保持不变
        header.setType(parentEapiMetadata.getHeader().getType());
        header.setBizObjectId(parentEapiMetadata.getHeader().getBizObjectId());
        header.setLanguage(parentEapiMetadata.getHeader().getLanguage());
        header.setTranslating(parentEapiMetadata.getHeader().getTranslating());
        // 是否允许扩展,与VO同步
        header.setExtendable(voMetadata.getHeader().isExtendable());

        return header;
    }

    private MetadataHeader metadataHeaderUpdate(GspMetadata voMetadata, GspMetadata eapiMetadata) {
        //获取当前需要更新的运行时定制元数据的Header
        MetadataHeader header = eapiMetadata.getHeader();
        // 编号和名称取自运行时定制扩展的VO元数据，VO的编号和名称已作出对应的扩展调整
        header.setCode(voMetadata.getHeader().getCode());
        header.setName(voMetadata.getHeader().getName());
        // 运行时定制Eapi元数据文件名称
        // 说明：目前所有的运行时定制Eapi元数据都是存储在数据库中，该文件名暂时没有使用场景，文件名称：{VO元数据编号}.eapi
        header.setFileName(String.format("%s.eapi", voMetadata.getHeader().getCode()));
        // 是否允许扩展,与VO同步
        header.setExtendable(voMetadata.getHeader().isExtendable());
        return header;
    }

    private String getEapiExtendProperty(GspMetadata extendMetadata) {
        ObjectNode node = JacksonJsonUtil.getMapper().createObjectNode();
        node.put("metadataVersion", UUID.randomUUID().toString());
        node.put("resourceType", ((ExternalApi)extendMetadata.getContent()).getService().getResourceType());
        return JacksonJsonUtil.toJson(node);
    }

    private DimensionExtendEntity buildDimensionExtendEntity(DimensionInfo dimensionInfo, GspMetadata parentEapiMetadata, GspMetadata extendEapiMetadata) {
        DimensionExtendEntity dimensionExtendEntity = new DimensionExtendEntity();
        dimensionExtendEntity.setBasicMetadataId(parentEapiMetadata.getHeader().getId());
        dimensionExtendEntity.setBasicMetadataCode(parentEapiMetadata.getHeader().getCode());
        dimensionExtendEntity.setBasicMetadataNamespace(parentEapiMetadata.getHeader().getNameSpace());
        dimensionExtendEntity.setBasicMetadataTypeStr(parentEapiMetadata.getHeader().getType());
        dimensionExtendEntity.setExtendMetadataEntity(extendEapiMetadata);
        dimensionExtendEntity.setFirstDimension(dimensionInfo.getFirstDimension());
        dimensionExtendEntity.setFirstDimensionCode(dimensionInfo.getFirstDimensionCode());
        dimensionExtendEntity.setFirstDimensionName(dimensionInfo.getFirstDimensionName());
        dimensionExtendEntity.setSecondDimension(dimensionInfo.getSecondDimension());
        dimensionExtendEntity.setSecondDimensionCode(dimensionInfo.getSecondDimensionCode());
        dimensionExtendEntity.setSecondDimensionName(dimensionInfo.getSecondDimensionName());
        return dimensionExtendEntity;
    }

    private void getServiceDefinition(GspMetadata voMetadata, GspViewModel vo, ExternalApi newEapiEntity) {
        newEapiEntity.setService(new Service());
        newEapiEntity.getService().setId(UUID.randomUUID().toString());
        newEapiEntity.getService().setResourceId(vo.getID());
        newEapiEntity.getService().setResourceType(Constants.EXTERNAL_API_RESOURCE_TYPE_VO);
        newEapiEntity.getService().setResourceCode(vo.getGeneratedConfigID());
        newEapiEntity.getService().setResourceName(vo.getName());
        newEapiEntity.getService().setOperations(new ArrayList<>());
        newEapiEntity.getService().getOperations().addAll(ViewModelUtils.getAllOperations(voMetadata));
    }

    private void upateServiceDefinition(GspMetadata voMetadata, GspViewModel vo, ExternalApi eapiEntity) {
        eapiEntity.getService().setResourceId(vo.getID());
        eapiEntity.getService().setResourceCode(vo.getGeneratedConfigID());
        eapiEntity.getService().setResourceName(vo.getName());
        eapiEntity.getService().setOperations(new ArrayList<>());
        //重新获取一遍，全量更新了，目前识别到的服务操作的ID都不会重新改变
        eapiEntity.getService().getOperations().addAll(ViewModelUtils.getAllOperations(voMetadata));
    }

    private void getBasicInfos(DimensionInfo dimensionInfo, GspMetadata extendEapiMetadata, ExternalApi parentEapiEntity, GspViewModel vo, ExternalApi newEapiEntity) {
        newEapiEntity.setId(extendEapiMetadata.getHeader().getId());
        newEapiEntity.setCode(extendEapiMetadata.getHeader().getCode());
        newEapiEntity.setName(extendEapiMetadata.getHeader().getName());
        newEapiEntity.setBusinessObject(extendEapiMetadata.getHeader().getBizObjectId());
        newEapiEntity.setApplication(parentEapiEntity.getApplication());
        newEapiEntity.setMicroServiceUnit(parentEapiEntity.getMicroServiceUnit());
        newEapiEntity.setVersion(parentEapiEntity.getVersion());
        String route = vo.getCode().toLowerCase();
        if (StringUtils.isNotBlank(dimensionInfo.getFirstDimension())) {
            route = route + "/" + dimensionInfo.getFirstDimension();
        }
        if (StringUtils.isNotBlank(dimensionInfo.getSecondDimension())) {
            route = route + "/" + dimensionInfo.getSecondDimension();
        }
        newEapiEntity.setBaseHttpPath(route.toLowerCase());
    }

    private void getRefModels(ExternalApi eapEntity, GspViewModel viewModel) {
        eapEntity.setModels(new ArrayList<>());
        if (eapEntity.getService() == null
                || eapEntity.getService().getOperations() == null
                || eapEntity.getService().getOperations().size() <= 0) {
            return;
        }
        for (Operation operation : eapEntity.getService().getOperations()) {
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
                if (eapEntity.getModels().size() <= 0) {
                    eapEntity.getModels().add(refModel);
                } else
                    if (eapEntity.getModels().size() > 0) {
                        long count = eapEntity.getModels()
                                .stream()
                                .filter(x -> x.getId().equals(refModel.getId()))
                                .count();
                        if (count <= 0) {
                            eapEntity.getModels().add(refModel);
                        }
                    }
            }
        }
    }

    private Model getModelProperty(Parameter param) {
        Model sgModel = new Model();
        sgModel.setId(param.getStructuredTypeRefId());
        sgModel.setCode(param.getRefCode());
        sgModel.setName(param.getRefName());
        sgModel.setModelId(param.getModelId());
        sgModel.setModelCode(param.getModelCode());
        sgModel.setModelName(param.getModelName());
        sgModel.setModelType(param.getModelType());
        return sgModel;
    }

    public GspMetadata getEapiMetadata(String metadataId) {
        CustomizationService service = SpringUtils.getBean(CustomizationService.class);
        if (service == null) {
            throw new RuntimeException("未获取到元数据运行时定制服务【CustomizationService】");
        }
        GspMetadata metadata = service.getMetadata(metadataId);
        if (metadata == null) {
            throw new RuntimeException(String.format("根据Eapi元数据ID:%s获取Eapi元数据为空", metadataId));
        }
        return metadata;
    }

    @Override
    public void delete(String eapiId) {
        //TODO: impl
    }

    @Override
    public void update(String parentEapiId, String eapiId, GspMetadata voMetadata, DimensionInfo dimensionInfo) {
        // 获取Parent Eapi元数据
        GspMetadata parentEapiMetadata = getEapiMetadata(parentEapiId);
        // 获取运行时定制Eapi元数据
        GspMetadata eapiMetadata = getEapiMetadata(eapiId);
        // 获取运行时定制Eapi元数据的实体结构
        ExternalApi eapiEntity = (ExternalApi)eapiMetadata.getContent();
        // 获取Eapi依赖的VO元数据
        GspViewModel vo = (GspViewModel)voMetadata.getContent();
        // 说明：元数据依赖暂不设置
        // 初始化扩展Eapi的元数据Header
        eapiMetadata.setHeader(metadataHeaderUpdate(voMetadata, eapiMetadata));
        // 设置RelativePath，目前所有的运行时定制Eapi元数据都是存储在数据库中，已经没有使用场景
        eapiMetadata.setRelativePath(voMetadata.getRelativePath());
        // 运行时定制元数据设置与VO同步
        eapiMetadata.setExtended(voMetadata.isExtended());
        // 构建Eapi元数据实体结构
        ExternalApi updatedEapiEntity =
                updateEapiEntity(voMetadata, eapiEntity, vo);
        // 更新Eapi元数据实体结构到Content
        eapiMetadata.setContent(updatedEapiEntity);
        // 元数据扩展属性，赋值到元数据的扩展属性ExtendProperty中
        eapiMetadata.setExtendProperty(getEapiExtendProperty(eapiMetadata));
        // 构建运行时Eapi元数据定制维度扩展实体
        DimensionExtendEntity dimensionExtendEntity = buildDimensionExtendEntity(dimensionInfo, parentEapiMetadata, eapiMetadata);
        SpringUtils.getBean(CustomizationService.class).saveExtMetadataWithDimensions(dimensionExtendEntity);
    }

    @Override
    public void publish(String eapiId) {
        //将运行时定制的EApi元数据由设计时发布为运行时
        CustomizationService service = SpringUtils.getBean(CustomizationService.class);
        if (service == null) {
            throw new RuntimeException("未获取到元数据运行时定制服务【CustomizationService】");
        }
        service.releaseMetadataToRt(eapiId, null);

        // 部署Eapi元数据服务
        ExternalApiCustomizedPublishService publishService = SpringUtils.getBean(ExternalApiCustomizedPublishService.class);
        publishService.publish(eapiId);
    }
}
