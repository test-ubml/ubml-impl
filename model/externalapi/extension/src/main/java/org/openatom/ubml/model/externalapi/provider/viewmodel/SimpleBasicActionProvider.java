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
import java.util.List;
import org.openatom.ubml.common.util.StringUtils;
import org.openatom.ubml.model.externalapi.definition.temp.vo.GspViewModel;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.externalapi.definition.entity.ModelType;
import org.openatom.ubml.model.externalapi.definition.entity.Operation;
import org.openatom.ubml.model.externalapi.definition.entity.Parameter;

/**
 * 功能描述:
 *
 * @ClassName: SimpleBasicActionProvider
 * @Author: Fynn Qi
 * @Date: 2020/7/31 10:42
 * @Version: V1.0
 */
public class SimpleBasicActionProvider {

    public static List<Operation> getSimpleBasicActions(GspMetadata gspMetadata) {
        List<Operation> operations = new ArrayList<>();
        //增加
        operations.add(create(gspMetadata));
        //删除
        operations.add(delete(gspMetadata));
        //批量删除
        operations.add(batchDelete(gspMetadata));
        //保存
        operations.add(update(gspMetadata));
        //查询
        operations.add(retrieve(gspMetadata));
        //过滤查询
        operations.add(query(gspMetadata));
        //增加默认值
        operations.add(createDefaultValue(gspMetadata));
        return operations;
    }


    private static Operation query(GspMetadata vmMetadata) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        Operation operation = new Operation();

        operation.setId(MessageFormat.format("{0}&^^&Query", vmMetadata.getHeader().getId()));
        operation.setCode("Query");
        operation.setName("过滤查询");
        operation.setDescription("过滤查询");
        operation.setResourceOperationId(MessageFormat.format("{0}&^^&Query", vmMetadata.getHeader().getId()));
        operation.setHttpMethod("GET");
        operation.setHttpPath("");
        operation.setDeprecated(false);
        operation.setExtend1("Basic");

        operation.setParameters(new ArrayList<>());

        Parameter parameter1 = new Parameter();
        parameter1.setId(MessageFormat.format("{0}&^^&entityFilter", vmMetadata.getHeader().getId()));
        parameter1.setCode("entityFilter");
        parameter1.setName("过滤条件");
        parameter1.setDescription("过滤条件");
        parameter1.setReturnValue(false);
        parameter1.setPrimitiveType(true);
        parameter1.setCollection(false);
        parameter1.setCollectionDepth(0);
        parameter1.setModelId("string");
        parameter1.setModelName("string");
        parameter1.setModelType(ModelType.VO);
        parameter1.setLocation("Query");
        parameter1.setRequired(true);
        operation.getParameters().add(parameter1);

        Parameter parameter2 = new Parameter();
        parameter2.setId(MessageFormat.format("{0}&^^&{1}", vmMetadata.getHeader().getId(), StringUtils.toCamelCase(viewModel.getMainObject().getCode())));
        parameter2.setCode(StringUtils.toCamelCase(viewModel.getMainObject().getCode()));
        parameter2.setName(viewModel.getMainObject().getName());
        parameter2.setDescription("实体模型");
        parameter2.setReturnValue(true);
        parameter2.setPrimitiveType(false);
        parameter2.setCollection(true);
        parameter2.setCollectionDepth(1);
        parameter2.setModelId(vmMetadata.getHeader().getId());
        parameter2.setModelCode(vmMetadata.getHeader().getCode());
        parameter2.setModelName(vmMetadata.getHeader().getName());
        parameter2.setModelType(ModelType.VO);
        parameter2.setStructuredTypeRefId(viewModel.getMainObject().getId());
        parameter2.setRefCode(viewModel.getMainObject().getCode());
        parameter2.setRefName(viewModel.getMainObject().getName());
        parameter2.setLocation("");
        parameter2.setRequired(true);
        operation.getParameters().add(parameter2);

        return operation;
    }

    private static Operation retrieve(GspMetadata vmMetadata) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        Operation operation = new Operation();

        operation.setId(MessageFormat.format("{0}&^^&Retrieve", vmMetadata.getHeader().getId()));
        operation.setCode("Retrieve");
        operation.setName("数据检索");
        operation.setDescription("数据检索");
        operation.setResourceOperationId(
                MessageFormat.format("{0}&^^&Retrieve", vmMetadata.getHeader().getId()));
        operation.setHttpMethod("GET");
        operation.setHttpPath("/{dataId}");
        operation.setDeprecated(false);
        operation.setExtend1("Basic");

        operation.setParameters(new ArrayList<>());

        Parameter param1 = new Parameter();
        param1.setId(MessageFormat.format("{0}&^^&dataId", vmMetadata.getHeader().getId()));
        param1.setCode("dataId");
        param1.setName("单据内码");
        param1.setDescription("单据内码");
        param1.setReturnValue(false);
        param1.setPrimitiveType(true);
        param1.setCollection(false);
        param1.setCollectionDepth(0);
        param1.setModelId("string");
        param1.setModelName("string");
        param1.setModelType(ModelType.VO);
        param1.setLocation("Path");
        param1.setRequired(true);
        operation.getParameters().add(param1);

        Parameter param2 = new Parameter();
        param2.setId(MessageFormat.format("{0}&^^&{1}", vmMetadata.getHeader().getId(), StringUtils.toCamelCase(viewModel.getMainObject().getCode())));
        //code要做ToCamel处理
        param2.setCode(StringUtils.toCamelCase(viewModel.getMainObject().getCode()));
        param2.setName(viewModel.getMainObject().getName());
        param2.setDescription("实体模型");
        param2.setReturnValue(true);
        param2.setPrimitiveType(false);
        param2.setCollection(false);
        param2.setCollectionDepth(0);
        param2.setModelId(vmMetadata.getHeader().getId());
        param2.setModelCode(vmMetadata.getHeader().getCode());
        param2.setModelName(vmMetadata.getHeader().getName());
        param2.setModelType(ModelType.VO);
        param2.setStructuredTypeRefId(viewModel.getMainObject().getId());
        param2.setRefCode(viewModel.getMainObject().getCode());
        param2.setRefName(viewModel.getMainObject().getName());
        param2.setLocation("");
        param2.setRequired(true);
        operation.getParameters().add(param2);

        return operation;
    }

    private static Operation update(GspMetadata vmMetadata) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        Operation operation = new Operation();

        operation.setId(MessageFormat.format("{0}&^^&Update", vmMetadata.getHeader().getId()));
        operation.setCode("Update");
        operation.setName("更新");
        operation.setDescription("更新");
        operation.setResourceOperationId(
                MessageFormat.format("{0}&^^&Update", vmMetadata.getHeader().getId()));
        operation.setHttpMethod("PUT");
        operation.setHttpPath("");
        operation.setDeprecated(false);
        operation.setExtend1("Basic");

        operation.setParameters(new ArrayList<>());


        Parameter param = new Parameter();
        param.setId(MessageFormat.format("{0}&^^&{1}", vmMetadata.getHeader().getId(), StringUtils.toCamelCase(viewModel.getMainObject().getCode())));
        param.setCode(StringUtils.toCamelCase(viewModel.getMainObject().getCode()));
        param.setName(viewModel.getMainObject().getName());
        param.setDescription(viewModel.getMainObject().getDescription());
        param.setReturnValue(false);
        param.setPrimitiveType(false);
        param.setCollection(false);
        param.setCollectionDepth(0);
        param.setModelId(vmMetadata.getHeader().getId());
        param.setModelCode(vmMetadata.getHeader().getCode());
        param.setModelName(vmMetadata.getHeader().getName());
        param.setModelType(ModelType.VO);
        param.setStructuredTypeRefId(viewModel.getMainObject().getId());
        param.setRefCode(viewModel.getMainObject().getCode());
        param.setRefName(viewModel.getMainObject().getName());
        param.setLocation("Body");
        param.setRequired(true);
        operation.getParameters().add(param);

        Parameter retParam = new Parameter();
        retParam.setId(MessageFormat.format("{0}&^^&retParam", vmMetadata.getHeader().getId()));
        retParam.setCode("return");
        retParam.setName("返回值");
        retParam.setDescription("返回值");
        retParam.setReturnValue(true);
        retParam.setPrimitiveType(false);
        retParam.setCollection(false);
        retParam.setCollectionDepth(0);
        retParam.setModelId(vmMetadata.getHeader().getId());
        retParam.setModelCode(vmMetadata.getHeader().getCode());
        retParam.setModelName(vmMetadata.getHeader().getName());
        retParam.setModelType(ModelType.VO);
        retParam.setStructuredTypeRefId(viewModel.getMainObject().getId());
        retParam.setRefCode(viewModel.getMainObject().getCode());
        retParam.setRefName(viewModel.getMainObject().getName());
        retParam.setLocation("");
        retParam.setRequired(true);
        operation.getParameters().add(retParam);

        return operation;
    }

    private static Operation batchDelete(GspMetadata vmMetadata) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        Operation operation = new Operation();

        operation.setId(MessageFormat.format("{0}&^^&BatchDelete", vmMetadata.getHeader().getId()));
        operation.setCode("BatchDelete");
        operation.setName("批量删除");
        operation.setDescription("批量删除");
        operation.setResourceOperationId(
                MessageFormat.format("{0}&^^&BatchDelete", vmMetadata.getHeader().getId()));
        operation.setHttpMethod("DELETE");
        operation.setHttpPath("");
        operation.setDeprecated(false);
        operation.setExtend1("Basic");

        operation.setParameters(new ArrayList<>());

        Parameter param = new Parameter();
        param.setId(
                MessageFormat.format("{0}&^^&ids", vmMetadata.getHeader().getId()));
        param.setCode("ids");
        param.setName("单据内码列表");
        param.setDescription("单据内码列表");
        param.setReturnValue(false);
        param.setPrimitiveType(true);
        param.setCollection(false);
        param.setCollectionDepth(0);
        param.setModelId("string");
        param.setModelName("string");
        param.setModelType(ModelType.VO);
        param.setLocation("Query");
        param.setRequired(true);
        operation.getParameters().add(param);

        return operation;
    }

    private static Operation delete(GspMetadata vmMetadata) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        Operation operation = new Operation();

        operation.setId(MessageFormat.format("{0}&^^&Delete", vmMetadata.getHeader().getId()));
        operation.setCode("Delete");
        operation.setName("删除");
        operation.setDescription("删除");
        operation.setResourceOperationId(
                MessageFormat.format("{0}&^^&Delete", vmMetadata.getHeader().getId()));
        operation.setHttpMethod("DELETE");
        operation.setHttpPath("/{dataId}");
        operation.setDeprecated(false);
        operation.setExtend1("Basic");

        operation.setParameters(new ArrayList<>());

        Parameter param = new Parameter();
        param.setId(MessageFormat.format("{0}&^^&dataId", vmMetadata.getHeader().getId()));
        param.setCode("dataId");
        param.setName("单据内码");
        param.setDescription("单据内码");
        param.setReturnValue(false);
        param.setPrimitiveType(true);
        param.setCollection(false);
        param.setCollectionDepth(0);
        param.setModelId("string");
        param.setModelName("string");
        param.setModelType(ModelType.VO);
        param.setLocation("Path");
        param.setRequired(true);
        operation.getParameters().add(param);

        return operation;
    }

    private static Operation create(GspMetadata vmMetadata) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        Operation operation = new Operation();

        operation.setId(MessageFormat.format("{0}&^^&Create", vmMetadata.getHeader().getId()));
        operation.setCode("Create");
        operation.setName("新增");
        operation.setDescription("新增");
        operation.setResourceOperationId(
                MessageFormat.format("{0}&^^&Create", vmMetadata.getHeader().getId()));
        operation.setHttpMethod("POST");
        operation.setHttpPath("");
        operation.setDeprecated(false);
        operation.setExtend1("Basic");

        operation.setParameters(new ArrayList<>());

        Parameter param = new Parameter();
        param.setId(MessageFormat.format("{0}&^^&{1}", vmMetadata.getHeader().getId(), StringUtils.toCamelCase(viewModel.getMainObject().getCode())));
        param.setCode(StringUtils.toCamelCase(viewModel.getMainObject().getCode()));
        param.setName(viewModel.getMainObject().getName());
        param.setDescription(viewModel.getMainObject().getDescription());
        param.setReturnValue(false);
        param.setPrimitiveType(false);
        param.setCollection(false);
        param.setCollectionDepth(0);
        param.setModelId(vmMetadata.getHeader().getId());
        param.setModelCode(vmMetadata.getHeader().getCode());
        param.setModelName(vmMetadata.getHeader().getName());
        param.setModelType(ModelType.VO);
        param.setStructuredTypeRefId(viewModel.getMainObject().getId());
        param.setRefCode(viewModel.getMainObject().getCode());
        param.setRefName(viewModel.getMainObject().getName());
        param.setLocation("Body");
        param.setRequired(true);
        operation.getParameters().add(param);


        Parameter retParam = new Parameter();
        retParam.setId(MessageFormat.format("{0}&^^&retParam", vmMetadata.getHeader().getId()));
        retParam.setCode("return");
        retParam.setName("返回值");
        retParam.setDescription("返回值");
        retParam.setReturnValue(true);
        retParam.setPrimitiveType(false);
        retParam.setCollection(false);
        retParam.setCollectionDepth(0);
        retParam.setModelId(vmMetadata.getHeader().getId());
        retParam.setModelCode(vmMetadata.getHeader().getCode());
        retParam.setModelName(vmMetadata.getHeader().getName());
        retParam.setModelType(ModelType.VO);
        retParam.setStructuredTypeRefId(viewModel.getMainObject().getId());
        retParam.setRefCode(viewModel.getMainObject().getCode());
        retParam.setRefName(viewModel.getMainObject().getName());
        retParam.setLocation("");
        retParam.setRequired(true);
        operation.getParameters().add(retParam);

        return operation;
    }

    private static Operation createDefaultValue(GspMetadata vmMetadata) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        Operation operation = new Operation();

        operation.setId(MessageFormat.format("{0}&^^&CreateDefaultValue", vmMetadata.getHeader().getId()));
        operation.setCode("CreateDefaultValue");
        operation.setName("新增");
        operation.setDescription("新增(返回默认数据)");
        operation.setResourceOperationId(
                MessageFormat.format("{0}&^^&CreateDefaultValue", vmMetadata.getHeader().getId()));
        operation.setHttpMethod("PUT");
        operation.setHttpPath("/service/defaultvalue");
        operation.setDeprecated(false);
        operation.setExtend1("Basic");

        operation.setParameters(new ArrayList<>());

        Parameter retParam = new Parameter();
        retParam.setId(MessageFormat.format("{0}&^^&retParam", vmMetadata.getHeader().getId()));
        retParam.setCode("return");
        retParam.setName("返回值");
        retParam.setDescription("返回值");
        retParam.setReturnValue(true);
        retParam.setPrimitiveType(false);
        retParam.setCollection(false);
        retParam.setCollectionDepth(0);
        retParam.setModelId(vmMetadata.getHeader().getId());
        retParam.setModelCode(vmMetadata.getHeader().getCode());
        retParam.setModelName(vmMetadata.getHeader().getName());
        retParam.setModelType(ModelType.VO);
        retParam.setStructuredTypeRefId(viewModel.getMainObject().getId());
        retParam.setRefCode(viewModel.getMainObject().getCode());
        retParam.setRefName(viewModel.getMainObject().getName());
        retParam.setLocation("");
        retParam.setRequired(true);
        operation.getParameters().add(retParam);

        return operation;
    }
}
