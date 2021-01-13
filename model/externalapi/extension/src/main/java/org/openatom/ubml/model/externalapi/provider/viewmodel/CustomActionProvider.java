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

package org.openatom.ubml.model.externalapi.provider.viewmodel;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jdk.nashorn.internal.runtime.PropertyMap;
import org.openatom.ubml.common.util.StringUtils;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.externalapi.definition.entity.ModelType;
import org.openatom.ubml.model.externalapi.definition.entity.Operation;
import org.openatom.ubml.model.externalapi.definition.entity.Parameter;
import org.openatom.ubml.model.externalapi.definition.temp.vo.GspViewModel;
import org.openatom.ubml.model.externalapi.definition.temp.vo.IViewModelParameter;
import org.openatom.ubml.model.externalapi.definition.temp.vo.VMCollectionParameterType;
import org.openatom.ubml.model.externalapi.definition.temp.vo.VMParameterType;
import org.openatom.ubml.model.externalapi.definition.temp.vo.ViewModelAction;

/**
 * CustomActionProvider
 *
 * @Author: Fynn Qi
 * @Date: 2020/2/3 16:54
 * @Version: V1.0
 */
public class CustomActionProvider {

    public static List<Operation> getCustomActions(GspMetadata gspMetadata) {
        GspViewModel viewModel = (GspViewModel)gspMetadata.getContent();
        List<Operation> operations = new ArrayList<>();
        List<ViewModelAction> actions = viewModel.getActions();
        for (ViewModelAction action : actions) {
            long count =
                    BasicActionSummary.getBasicActions(gspMetadata).stream()
                            .filter(x -> x.getCode().equals(action.getCode()))
                            .count();
            if (count > 0) {
                throw new RuntimeException(
                        String.format(
                                "操作编号%s为预置的基本操作编号，请修改%s[ID:%s]的自定义动作编号",
                                action.getCode(), viewModel.getCode(), viewModel.getID()));
            }

            Operation operation = new Operation();
            operation.setId(MessageFormat.format("{0}&^^&{1}", action.getID(), action.getCode()));
            operation.setCode(action.getCode());
            operation.setName(action.getName());
            operation.setResourceOperationId(
                    MessageFormat.format("{0}&^^&{1}", action.getID(), action.getCode()));
            operation.setDescription(action.getName());
            operation.setHttpMethod("PUT");
            operation.setHttpPath("/service/" + action.getCode().toLowerCase());
            operation.setDeprecated(false);
            operation.setExtend1("Custom");
            operation.setParameters(getParams(action, gspMetadata));

            operations.add(operation);
        }
        return operations;
    }

    public static List<Parameter> getParams(ViewModelAction action, GspMetadata gspMetadata) {
        List<Parameter> parameters = new ArrayList<>(getInputParams(action));
        Parameter retParam = getActionReturnParam(action, gspMetadata);
        if (retParam != null && StringUtils.isNotBlank(retParam.getId())) {
            parameters.add(retParam);
        }
        return parameters;
    }

    public static List<Parameter> getInputParams(ViewModelAction action) {
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(buildRequestInfoParam(action));
        action.getParameterCollection();
        if (action.getParameterCollection() != null && PropertyMap.getCount() > 0) {
            Iterator<Object> it = action.getParameterCollection().iterator();
            while (it.hasNext()) {
                IViewModelParameter param = (IViewModelParameter)it.next();
                Parameter serviceParam = getActionParam(param);
                if (serviceParam != null && StringUtils.isNotBlank(serviceParam.getId())) {
                    parameters.add(serviceParam);
                }
            }
        }
        return parameters;
    }

    public static Parameter buildRequestInfoParam(ViewModelAction action) {
        Parameter requestInfoParam = new Parameter();
        requestInfoParam.setId(MessageFormat.format("{0}&^^&RequestInfo", action.getID()));
        requestInfoParam.setCode("requestInfo");
        requestInfoParam.setName("请求信息");
        requestInfoParam.setDescription("请求信息");
        requestInfoParam.setReturnValue(false);
        requestInfoParam.setPrimitiveType(false);
        requestInfoParam.setCollection(false);
        requestInfoParam.setCollectionDepth(0);
        requestInfoParam.setModelId(MessageFormat.format("{0}&^^&RequestInfo", action.getID()));
        requestInfoParam.setModelCode("RequestInfo");
        requestInfoParam.setModelName("RequestInfo");
        requestInfoParam.setModelType(ModelType.DTO);
        requestInfoParam.setStructuredTypeRefId(MessageFormat.format("{0}&^^&RequestInfo", action.getID()));
        requestInfoParam.setRefCode("RequestInfo");
        requestInfoParam.setRefName("RequestInfo");
        requestInfoParam.setLocation("Body");
        requestInfoParam.setRequired(false);
        return requestInfoParam;
    }

    private static Parameter getActionReturnParam(
            ViewModelAction action, GspMetadata gspMetadata) {
        Parameter responseInfoParam = new Parameter();
        responseInfoParam.setId(MessageFormat.format("{0}&^^&ResponseInfo", action.getID()));
        responseInfoParam.setCode("responseInfo");
        responseInfoParam.setName("响应信息");
        responseInfoParam.setDescription("响应信息");
        responseInfoParam.setReturnValue(true);
        responseInfoParam.setPrimitiveType(false);
        responseInfoParam.setCollection(false);
        responseInfoParam.setCollectionDepth(0);
        responseInfoParam.setModelId(
                MessageFormat.format("{0}&^^&ResponseInfo", gspMetadata.getHeader().getId()));
        responseInfoParam.setModelCode("ResponseInfo");
        responseInfoParam.setModelName("ResponseInfo");
        responseInfoParam.setModelType(ModelType.DTO);
        responseInfoParam.setStructuredTypeRefId(
                MessageFormat.format("{0}&^^&ResponseInfo", gspMetadata.getHeader().getId()));
        responseInfoParam.setRefCode("ResponseInfo");
        responseInfoParam.setRefName("ResponseInfo");
        responseInfoParam.setLocation("");
        responseInfoParam.setRequired(true);

        return responseInfoParam;
    }

    private static Parameter getActionParam(IViewModelParameter param) {
        Parameter serviceParam = new Parameter();
        serviceParam.setId(MessageFormat.format("{0}&^^&{1}", param.getID(), param.getParamCode()));
        serviceParam.setCode(param.getParamCode());
        serviceParam.setName(param.getParamName());
        serviceParam.setDescription(param.getParamDescription());
        serviceParam.setReturnValue(false);
        serviceParam.setCollection(false);
        serviceParam.setCollectionDepth(0);

        if (param.getCollectionParameterType() == VMCollectionParameterType.List
                || param.getCollectionParameterType() == VMCollectionParameterType.Array) {
            serviceParam.setCollection(true);
            serviceParam.setCollectionDepth(1);
        }
        serviceParam.setLocation("Body");
        serviceParam.setRequired(true);
        boolean isBasicType = isBasicType(param.getParameterType());
        String typeString = getTypeInfo(param.getParameterType());
        if (isBasicType) {
            serviceParam.setPrimitiveType(true);
            serviceParam.setModelId(typeString);
            serviceParam.setModelName(typeString);
        } else {
            serviceParam.setPrimitiveType(false);
            serviceParam.setModelType(ModelType.DTO);
            serviceParam.setModelId(typeString);
            serviceParam.setModelCode(typeString);
            serviceParam.setModelName(typeString);
            serviceParam.setStructuredTypeRefId(typeString);
            serviceParam.setRefCode(typeString);
            serviceParam.setRefName(typeString);
        }
        return serviceParam;
    }

    private static String getTypeInfo(VMParameterType vMParameterType) {
        String typestring = "";
        switch (vMParameterType) {
            case Boolean:
                typestring = "bool";
                break;
            case DateTime:
                typestring = "DateTime";
                break;
            case Decimal:
                typestring = "Decimal";
                break;
            case Double:
                typestring = "double";
                break;
            case Int32:
                typestring = "int";
                break;
            case String:
                typestring = "string";
                break;
            case Object:
            case Custom:
            default:
                typestring = "object";
                break;
        }
        return typestring;
    }

    private static boolean isBasicType(VMParameterType vMParameterType) {
        switch (vMParameterType) {
            case Boolean:
            case DateTime:
            case Decimal:
            case Double:
            case Int32:
            case String:
            case Object:
                return true;
            default:
                return false;
        }
    }
}
