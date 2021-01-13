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
import org.openatom.ubml.model.externalapi.definition.temp.vo.GspViewModel;
import org.openatom.ubml.model.externalapi.definition.temp.vo.IViewModelParameter;
import org.openatom.ubml.model.externalapi.definition.temp.vo.VMCollectionParameterType;
import org.openatom.ubml.model.externalapi.definition.temp.vo.VMParameterType;
import org.openatom.ubml.model.externalapi.definition.temp.vo.ViewModelAction;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.externalapi.definition.entity.ModelType;
import org.openatom.ubml.model.externalapi.definition.entity.Operation;
import org.openatom.ubml.model.externalapi.definition.entity.Parameter;

/**
 * SimpleCustomActionProvider
 *
 * @Author: Fynn Qi
 * @Date: 2020/7/31 10:44
 * @Version: V1.0
 */
public class SimpleCustomActionProvider {

    public static List<Operation> getSimpleCustomActions(GspMetadata gspMetadata) {
        GspViewModel viewModel = (GspViewModel)gspMetadata.getContent();
        List<Operation> operations = new ArrayList<>();
        List<ViewModelAction> actions = viewModel.getActions();
        for (ViewModelAction action : actions) {
            long count =
                    SimpleBasicActionSummary.getSimpleBasicActions(gspMetadata).stream()
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
        if (retParam != null) {
            parameters.add(retParam);
        }
        return parameters;
    }

    public static List<Parameter> getInputParams(ViewModelAction action) {
        List<Parameter> parameters = new ArrayList<>();
        if (action.getParameterCollection() != null) {
            Iterator<Object> it = action.getParameterCollection().iterator();
            while (it.hasNext()) {
                IViewModelParameter param = (IViewModelParameter)it.next();
                Parameter serviceParam = getActionParam(param);
                parameters.add(serviceParam);
            }
        }
        return parameters;
    }

    private static Parameter getActionReturnParam(
            ViewModelAction action, GspMetadata gspMetadata) {
        if (action.getReturnValue() == null) {
            return null;
        }
        Parameter retParam = new Parameter();
        retParam.setId(MessageFormat.format("{0}&^^&retParam", action.getID()));
        retParam.setCode("return");
        retParam.setName("返回值");
        retParam.setDescription("返回值");
        retParam.setReturnValue(true);
        retParam.setPrimitiveType(false);
        retParam.setCollection(false);
        retParam.setCollectionDepth(0);
        retParam.setModelId("object");
        retParam.setModelCode("object");
        retParam.setModelName("object");
        retParam.setModelType(ModelType.DTO);
        retParam.setStructuredTypeRefId("object");
        retParam.setRefCode("object");
        retParam.setRefName("object");
        retParam.setLocation("");
        retParam.setRequired(true);

        return retParam;
    }

    private static Parameter getActionParam(IViewModelParameter viewModelParam) {
        Parameter param = new Parameter();
        param.setId(
                MessageFormat.format("{0}&^^&{1}", viewModelParam.getID(), viewModelParam.getParamCode()));
        param.setCode(viewModelParam.getParamCode());
        param.setName(viewModelParam.getParamName());
        param.setDescription(viewModelParam.getParamDescription());
        param.setReturnValue(false);
        param.setCollection(false);
        param.setCollectionDepth(0);

        if (viewModelParam.getCollectionParameterType() == VMCollectionParameterType.List
                || viewModelParam.getCollectionParameterType() == VMCollectionParameterType.Array) {
            param.setCollection(true);
            param.setCollectionDepth(1);
        }
        param.setLocation("Body");
        param.setRequired(true);
        boolean isBasicType = isBasicType(viewModelParam.getParameterType());
        String typeDesc = getTypeDesc(viewModelParam.getParameterType());
        if (isBasicType) {
            param.setPrimitiveType(true);
            param.setModelId(typeDesc);
            param.setModelName(typeDesc);
            param.setModelType(ModelType.VO);
        } else {
            param.setPrimitiveType(false);
            param.setModelType(ModelType.DTO);
            param.setModelId(typeDesc);
            param.setModelCode(typeDesc);
            param.setModelName(typeDesc);
            param.setStructuredTypeRefId(typeDesc);
            param.setRefCode(typeDesc);
            param.setRefName(typeDesc);
        }
        return param;
    }

    private static String getTypeDesc(VMParameterType vMParameterType) {
        String typestring = "";
        switch (vMParameterType) {
            case Boolean:
                typestring = "bool";
                break;
            case DateTime:
                typestring = "dateTime";
                break;
            case Decimal:
                typestring = "decimal";
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
