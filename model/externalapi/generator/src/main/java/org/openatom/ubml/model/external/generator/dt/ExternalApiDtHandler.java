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

package org.openatom.ubml.model.external.generator.dt;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.openatom.ubml.common.util.StringUtils;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.common.definition.entity.IMetadataContent;
import org.openatom.ubml.model.externalapi.definition.Constants;
import org.openatom.ubml.model.externalapi.definition.entity.ExternalApi;
import org.openatom.ubml.model.externalapi.definition.entity.Operation;
import org.openatom.ubml.model.externalapi.definition.entity.Parameter;
import org.openatom.ubml.model.externalapi.definition.temp.lcm.JitContext;
import org.openatom.ubml.model.externalapi.definition.temp.vo.GspViewModel;

/**
 * 功能描述:
 *
 * @ClassName: EapiDtHandler
 * @Author: Fynn Qi
 * @Date: 2020/9/18 9:13
 * @Version: V1.0
 */
public class ExternalApiDtHandler {

    private static final String[] WHITE_VO_OPERATION_NAME_LIST =
            new String[]{"CloseSession", "DeleteAndSave", "RetrieveWithChildPagination", "QueryChild"};

    private static final String REQUEST_INFO = "RequestInfo";

    public static ExternalApi handler(ExternalApi metadata, JitContext context) {

        String resourceType = metadata.getService().getResourceType();
        String resourceId = metadata.getService().getResourceId();
        switch (resourceType) {
            case Constants.EXTERNAL_API_RESOURCE_TYPE_VO:
                resourceIdHandler(metadata, resourceId, context);
                operationHandler(metadata);
                paramHandler(metadata);
                break;
            case Constants.EXTERNAL_API_RESOURCE_TYPE_VO_SIMPLE:
                resourceIdHandler(metadata, resourceId, context);
                break;
            default:
                break;
        }
        return metadata;
    }

    private static void resourceIdHandler(ExternalApi metadata, String resourceId, JitContext context) {
        metadata.getService().setResourceId(getViewModelConfigId(resourceId, context));
    }

    private static void operationHandler(ExternalApi metadata) {
        metadata
                .getService()
                .setOperations(
                        metadata.getService().getOperations() != null
                                ? metadata.getService().getOperations()
                                : new ArrayList<>());
        List<Operation> filterOperations =
                metadata.getService().getOperations().stream()
                        .filter(x -> !isInWhiteList(x.getCode()))
                        .collect(Collectors.toList());
        metadata.getService().getOperations().clear();
        metadata.getService().getOperations().addAll(filterOperations);
    }

    private static void paramHandler(ExternalApi metadata) {
        if (metadata.getService().getOperations() == null
                || metadata.getService().getOperations().size() <= 0) {
            return;
        }
        for (Operation operation : metadata.getService().getOperations()) {
            if (operation.getParameters() == null || operation.getParameters().size() <= 0) {
                return;
            }
            //兼容处理requestInfo
            for (Parameter parameter : operation.getParameters()) {
                if (REQUEST_INFO.equals(parameter.getCode())) {
                    parameter.setCode("requestInfo");
                }
            }
        }
    }

    private static boolean isInWhiteList(String operationName) {
        if (StringUtils.isBlank(operationName)) {
            return false;
        }
        return Arrays.asList(WHITE_VO_OPERATION_NAME_LIST).contains(operationName);
    }

    private static String getViewModelConfigId(String metadataId, JitContext context) {
        GspViewModel viewModel = getGspViewModel(metadataId, context);
        return viewModel.getGeneratedConfigID();
    }

    public static GspViewModel getGspViewModel(String metadataId, JitContext context) {
        // 获取设计时运行环境中的元数据
        GspMetadata voMetadata = context.getMetadata(metadataId);
        if (Objects.isNull(voMetadata)) {
            throw new RuntimeException(MessageFormat.format("获取设计时VO元数据{0}失败！", metadataId));
        }
        IMetadataContent content = voMetadata.getContent();
        if (Objects.isNull(content)) {
            throw new RuntimeException(MessageFormat.format("设计时VO元数据{0}的Content为空！", metadataId));
        }
        try {
            GspViewModel viewModel = (GspViewModel)content;
            return viewModel;
        } catch (Exception e) {
            throw new RuntimeException(
                    MessageFormat.format("设计时VO元数据(ID:{0})的Content转换为GspViewModel失败！", metadataId), e);
        }
    }
}
