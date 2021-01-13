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

import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
import org.openatom.ubml.model.externalapi.definition.entity.ProviderType;
import org.openatom.ubml.model.externalapi.definition.entity.Service;
import org.openatom.ubml.model.externalapi.definition.temp.lcm.FileService;
import org.openatom.ubml.model.externalapi.definition.temp.lcm.MetadataService;
import org.openatom.ubml.model.externalapi.definition.temp.vo.GspViewModel;
import org.openatom.ubml.model.externalapi.provider.viewmodel.utils.SimpleViewModelUtils;
import org.openatom.ubml.model.externalapi.provider.viewmodel.utils.ViewModelUtils;
import org.openatom.ubml.model.framework.definition.entity.MetadataProject;

/**
 * EapiBuilder
 *
 * @Author: Fynn Qi
 * @Date: 2020/12/24 9:17
 * @Version: V1.0
 */
public class ExternalApiMetadataBuilder {

    private static final String EAPI_SUFFIX_NAME = ".eapi";
    private static final String EAPI_PATH = "/eapi";
    private ProviderType resourceType;

    public GspMetadata build(GspMetadata provider, String path, ProviderType providerType) {
        this.resourceType = providerType;
        // 初始化Header信息
        GspMetadata eApi = buildHeader(provider, path);
        // 生成元数据content
        eApi = buildContent(provider, eApi);
        return eApi;
    }

    private GspMetadata buildContent(GspMetadata provider, GspMetadata eApi) {
        List<String> metadataTypeList = new ArrayList<>();
        metadataTypeList.add(EAPI_SUFFIX_NAME);
        MetadataService metadataService = SpringUtils.getBean(MetadataService.class);
        String relativePath = eApi.getRelativePath();
        List<GspMetadata> metadataList = metadataService.getMetadataList(relativePath, metadataTypeList);
        if (metadataList != null && metadataList.size() > 0) {
            String metadataCode = eApi.getHeader().getCode();
            boolean exist = metadataList.stream()
                    .anyMatch(x -> metadataCode.equalsIgnoreCase(x.getHeader().getCode()));
            if (exist) {
                metadataService.deleteMetadata(relativePath, eApi.getHeader().getCode() + EAPI_SUFFIX_NAME);
            }
        }
        // 元数据初始化
        eApi = metadataService.initializeMetadataEntity(eApi);
        // 创建元数据
        metadataService.createMetadata(relativePath, eApi);
        // 加载元数据
        eApi = metadataService.loadMetadata(eApi.getHeader().getFileName(), relativePath);
        // eapi赋值给元数据的content
        eApi.setContent(buildEapiMetadataDefine(provider, eApi));
        // 保存元数据,元数据设置扩展属性
        ObjectNode extension = JacksonJsonUtil.getMapper().createObjectNode();
        extension.put("metadataVersion", UUID.randomUUID().toString());
        extension.put("resourceType", getResourceType(resourceType));
        eApi.setExtendProperty(JacksonJsonUtil.toJson(extension));
        // 元数据保存所需要的完整路径
        String fullPath = String.format("%s/%s%s", relativePath, eApi.getHeader().getCode(), EAPI_SUFFIX_NAME);
        metadataService.saveMetadata(eApi, fullPath);
        return eApi;
    }

    private ExternalApi buildEapiMetadataDefine(GspMetadata vo, GspMetadata eapiMetadata) {
        ExternalApi eApi = new ExternalApi();
        // 定义SG外部服务基本信息
        eApi.setId(eapiMetadata.getHeader().getId());
        eApi.setCode(eapiMetadata.getHeader().getCode());
        eApi.setName(eapiMetadata.getHeader().getName());
        eApi.setBusinessObject(eapiMetadata.getHeader().getBizObjectId());
        //TODO:set app&su
        eApi.setApplication(null);
        eApi.setMicroServiceUnit(null);
        //TODO 后续修改为下面的生成方式
        // route的默认生成方式：BO的编号_VO元数据的编号
        eApi.setBaseHttpPath(vo.getHeader().getCode().toLowerCase());
        eApi.setVersion("1.0");
        eApi.setDescription(vo.getHeader().getCode() + "服务");
        // 生成服务定义
        eApi.setService(this.buildServiceDefine(vo));
        // 获取服务模型列表
        eApi.getModels().addAll(getServiceModels(eApi));
        return eApi;
    }

    private List<Model> getServiceModels(ExternalApi eapiContent) {
        List<Model> models = new ArrayList<>();
        if (eapiContent.getService().getOperations().size() <= 0) {
            return models;
        }
        for (Operation operation : eapiContent.getService().getOperations()) {
            if (operation.getParameters() == null || operation.getParameters().size() <= 0) {
                continue;
            }
            for (Parameter parameter : operation.getParameters()) {
                if (parameter.isPrimitiveType()) {
                    continue;
                }
                Model model = getModelProperty(parameter);
                if (models.size() <= 0) {
                    models.add(model);
                } else {
                    long count =
                            eapiContent.getModels().stream()
                                    .filter(x -> x.getId().equalsIgnoreCase(model.getId()))
                                    .count();
                    if (count <= 0) {
                        models.add(model);
                    }
                }
            }
        }
        return models;
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

    private Service buildServiceDefine(GspMetadata vo) {
        Service service = new Service();
        service.setId(UUID.randomUUID().toString());
        service.setResourceId(vo.getHeader().getId());
        service.setResourceType(getResourceType(resourceType));
        service.setResourceCode(((GspViewModel)vo.getContent()).getGeneratedConfigID());
        service.setResourceName(vo.getHeader().getName());
        service.setOperations(new ArrayList<>());
        switch (resourceType) {
            case VO_ADVANCE:
                service.getOperations().addAll(ViewModelUtils.getAllOperations(vo));
                break;
            case VO_SIMPLE:
                service.getOperations().addAll(SimpleViewModelUtils.getAllOperations(vo));
                break;
            default:
                throw new RuntimeException("不支持的资源类型:" + resourceType);
        }
        return service;
    }

    private GspMetadata buildHeader(GspMetadata vo, String path) {
        GspMetadata metadata = new GspMetadata();
        metadata.setHeader(new MetadataHeader());
        metadata.getHeader().setNameSpace(vo.getHeader().getNameSpace());
        metadata.getHeader().setCode(vo.getHeader().getCode());
        metadata.getHeader().setName(vo.getHeader().getName());
        metadata.getHeader().setExtendable(vo.getHeader().isExtendable());
        if (StringUtils.isBlank(vo.getHeader().getBizObjectId())) {
            throw new RuntimeException(
                    String.format("VO元数据【%s(ID:%s)】的业务对象不能为空", vo.getHeader().getCode(), vo.getHeader().getId()));
        }
        metadata.getHeader().setBizObjectId(vo.getHeader().getBizObjectId());
        metadata.getHeader().setType(Constants.EXTERNAL_API_METADATA_TYPE);
        metadata.getHeader().setFileName(vo.getHeader().getCode() + EAPI_SUFFIX_NAME);
        metadata.setRelativePath(getEapiRelativePath(path));

        FileService fileService = SpringUtils.getBean(FileService.class);
        // 如果该路径上不存在api文件夹则直接创建新的api文件夹
        if (!fileService.isDirectoryExist(metadata.getRelativePath())) {
            fileService.createDirectory(metadata.getRelativePath());
        }
        return metadata;
    }

    private String getResourceType(ProviderType providerType) {
        switch (providerType) {
            case VO_ADVANCE:
                return Constants.EXTERNAL_API_RESOURCE_TYPE_VO;
            case VO_SIMPLE:
                return Constants.EXTERNAL_API_RESOURCE_TYPE_VO_SIMPLE;
            default:
                throw new RuntimeException("不支持的资源类型:" + providerType);
        }
    }

    private String getEapiRelativePath(String path) {
        MetadataProject metadataProject = MetadataUtils.getMetadataProject(path);
        // 拼接eapi元数据路径:工程路径+eapi文件路径（/eapi）
        return metadataProject.getProjectPath() + EAPI_PATH;
    }
}
