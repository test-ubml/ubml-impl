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
import org.openatom.ubml.model.externalapi.definition.temp.vo.GspViewObject;
import org.openatom.ubml.model.externalapi.definition.temp.vo.IGspCommonObject;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.externalapi.definition.entity.ModelType;
import org.openatom.ubml.model.externalapi.definition.entity.Operation;
import org.openatom.ubml.model.externalapi.definition.entity.Parameter;

/**
 * BasicActionProvider
 *
 * @Author: Fynn Qi
 * @Date: 2020/2/3 10:43
 * @Version: V1.0
 */
public class BasicActionProvider {

    public static List<Operation> getBasicActions(GspMetadata gspMetadata) {
        List<Operation> operations = new ArrayList();
        // 创建BeSession
        operations.add(createSession(gspMetadata));
        // 关闭Session
        operations.add(closeSession(gspMetadata));
        // 增加
        operations.add(create(gspMetadata));
        // 编辑
        operations.add(edit(gspMetadata));
        // 删除
        operations.add(delete(gspMetadata));
        // 批量删除
        operations.add(batchDelete(gspMetadata));
        // 删除并保存
        operations.add(deleteAndSave(gspMetadata));
        // 修改
        operations.add(update(gspMetadata));
        // 查询
        operations.add(retrieve(gspMetadata));
        // 查询（所有数据）
        operations.add(retrieveWithChildPagination(gspMetadata));
        // 查询子表
        operations.add(retrieveChildByIndex(gspMetadata));
        // 过滤查询
        operations.add(query(gspMetadata));
        // 保存
        operations.add(save(gspMetadata));
        // 取消
        operations.add(cancel(gspMetadata));
        // 获取字段帮助操作
        operations.add(getElementHelp(gspMetadata));
        // 新增子对象数据
        operations.addAll(getCreateChildOperationList(gspMetadata));
        // 删除子对象数据
        operations.addAll(getDeleteChildOperationList(gspMetadata));
        // 扩展删除
        operations.add(extendDelete(gspMetadata));
        // 扩展批量删除
        operations.add(extendBatchDelete(gspMetadata));
        // 扩展查询
        operations.add(extendRetrieve(gspMetadata));
        // 扩展过滤查询
        operations.add(extendQuery(gspMetadata));
        // 查询数据
        operations.add(extensionFilter(gspMetadata));
        // 扩展帮助
        operations.add(extendElementHelp(gspMetadata));
        // 扩展删除子对象数据
        operations.addAll(getExtendDeleteChildOperationList(gspMetadata));
        return operations;
    }

    private static List<Operation> getExtendDeleteChildOperationList(GspMetadata vmMetadata) {
        List<Operation> operations = new ArrayList<>();
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        GspViewObject mainObject = viewModel.getMainObject();
        if (mainObject == null || mainObject.getContainChildObjects().size() <= 0) {
            return operations;
        }
        for (IGspCommonObject obj : mainObject.getContainChildObjects()) {
            String nodesCodesStringBuidler = "";
            getExtendDeleteChildActions(
                    (GspViewObject)obj, operations, nodesCodesStringBuidler, vmMetadata);
        }
        return operations;
    }

    private static void getExtendDeleteChildActions(
            GspViewObject viewObject,
            List<Operation> operations,
            String nodesCodesStringBuidler,
            GspMetadata vmMetadata) {
        nodesCodesStringBuidler =
                StringUtils.isBlank(nodesCodesStringBuidler)
                        ? viewObject.getCode()
                        : nodesCodesStringBuidler + "#" + viewObject.getCode();
        Operation operation =
                getExtendDeleteChildOperation(vmMetadata, viewObject.getCode(), nodesCodesStringBuidler);
        if (operation != null) {
            operations.add(operation);
        }
        if (viewObject.getContainChildObjects().size() > 0) {
            for (IGspCommonObject obj : viewObject.getContainChildObjects()) {
                getExtendDeleteChildActions(
                        (GspViewObject)obj, operations, nodesCodesStringBuidler, vmMetadata);
            }
        }
    }

    private static Operation getExtendDeleteChildOperation(
            GspMetadata vmMetadata, String currentNodeCode, String nodeCodesStringBuilder) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        String[] nodeCodes = nodeCodesStringBuilder.split("#");
        if (nodeCodes.length <= 0) {
            return null;
        }
        Operation operation = new Operation();
        operation.setParameters(new ArrayList<>());
        // RequestInfo参数必须放在第一位置
        Parameter requestInfoParam = new Parameter();
        requestInfoParam.setId(
                MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        requestInfoParam.setCode("requestInfo");
        requestInfoParam.setName("请求信息");
        requestInfoParam.setDescription("请求信息");
        requestInfoParam.setReturnValue(false);
        requestInfoParam.setPrimitiveType(false);
        requestInfoParam.setCollection(false);
        requestInfoParam.setCollectionDepth(0);
        requestInfoParam.setModelId(
                MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        requestInfoParam.setModelCode("RequestInfo");
        requestInfoParam.setModelName("RequestInfo");
        requestInfoParam.setModelType(ModelType.DTO);
        requestInfoParam.setStructuredTypeRefId(
                MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        requestInfoParam.setRefCode("RequestInfo");
        requestInfoParam.setRefName("RequestInfo");
        requestInfoParam.setLocation("Body");
        requestInfoParam.setRequired(false);

        operation.getParameters().add(requestInfoParam);
        operation.setHttpPath("/extension/{rootId}");
        getDeleteChildParam(viewModel, operation, "rootId");
        String methodCode = "Extend_DeleteChild";
        String methodId = "Extend_DeleteChild";
        for (String nodeCode : nodeCodes) {
            methodCode = methodCode + String.format("_%s", nodeCode);
            methodId = methodId + MessageFormat.format("##{0}", nodeCode);
            operation.setHttpPath(
                    operation.getHttpPath()
                            + MessageFormat.format("/{0}", nodeCode.toLowerCase())
                            + "/{"
                            + MessageFormat.format(
                            "{0}Id", StringUtils.toCamelCase(nodeCode))
                            + "}");
            getDeleteChildParam(
                    viewModel,
                    operation,
                    StringUtils.toCamelCase(nodeCode) + "Id");
        }

        operation.setId(MessageFormat.format("{0}&^^&{0}", vmMetadata.getHeader().getId(), methodId));
        operation.setCode(methodCode);
        operation.setName(MessageFormat.format("扩展删除从表{0}数据", currentNodeCode));
        operation.setDescription(MessageFormat.format("扩展删除从表{0}数据", currentNodeCode));
        operation.setResourceOperationId(
                MessageFormat.format("{0}&^^&{1}", vmMetadata.getHeader().getId(), methodId));
        operation.setHttpMethod("PUT");
        operation.setDeprecated(false);
        operation.setExtend1("Basic"); // 表示该操作是基本操作中的增加从表数据的操作
        operation.setExtend2(nodeCodesStringBuilder); // 用于存储NodeCode拼接信息（A#B#C）

        // 返回值都放在最后位置
        Parameter responseInfoParam = new Parameter();
        responseInfoParam.setId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        responseInfoParam.setCode("responseInfo");
        responseInfoParam.setName("响应信息");
        responseInfoParam.setDescription("响应信息");
        responseInfoParam.setReturnValue(true);
        responseInfoParam.setPrimitiveType(false);
        responseInfoParam.setCollection(false);
        responseInfoParam.setCollectionDepth(0);
        responseInfoParam.setModelId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        responseInfoParam.setModelCode("ResponseInfo");
        responseInfoParam.setModelName("ResponseInfo");
        responseInfoParam.setModelType(ModelType.DTO);
        responseInfoParam.setStructuredTypeRefId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        responseInfoParam.setRefCode("ResponseInfo");
        responseInfoParam.setRefName("ResponseInfo");
        responseInfoParam.setLocation("");
        responseInfoParam.setRequired(true);
        operation.getParameters().add(responseInfoParam);
        return operation;
    }

    private static Operation extendElementHelp(GspMetadata vmMetadata) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        Operation operation = new Operation();

        operation.setId(
                MessageFormat.format("{0}&^^&Extend_ElementHelp", vmMetadata.getHeader().getId()));
        operation.setCode("Extend_GetElementHelp");
        operation.setName("扩展获取字段帮助");
        operation.setDescription("根据labelId获取字段帮助扩展方法");
        operation.setResourceOperationId(
                MessageFormat.format("{0}&^^&Extend_ElementHelp", vmMetadata.getHeader().getId()));
        operation.setHttpMethod("PUT");
        operation.setHttpPath("/extension/elementhelps");
        operation.setDeprecated(false);
        operation.setExtend1("Basic");

        operation.setParameters(new ArrayList<>());

        Parameter parameter1 = new Parameter();
        parameter1.setId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setCode("requestInfo");
        parameter1.setName("请求信息");
        parameter1.setDescription("请求信息");
        parameter1.setReturnValue(false);
        parameter1.setPrimitiveType(false);
        parameter1.setCollection(false);
        parameter1.setCollectionDepth(0);
        parameter1.setModelId(
                MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setModelCode("RequestInfo");
        parameter1.setModelName("RequestInfo");
        parameter1.setModelType(ModelType.DTO);
        parameter1.setStructuredTypeRefId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setRefCode("RequestInfo");
        parameter1.setRefName("RequestInfo");
        parameter1.setLocation("Body");
        parameter1.setRequired(false);
        operation.getParameters().add(parameter1);

        Parameter parameter2 = new Parameter();
        parameter2.setId(MessageFormat.format("{0}&^^&labelIdParam", vmMetadata.getHeader().getId()));
        parameter2.setCode("labelId");
        parameter2.setName("标签Id");
        parameter2.setDescription("标签内码");
        parameter2.setReturnValue(false);
        parameter2.setPrimitiveType(true);
        parameter2.setCollection(false);
        parameter2.setCollectionDepth(0);
        parameter2.setModelId("string");
        parameter2.setModelName("string");
        parameter2.setLocation("Body");
        parameter2.setRequired(true);
        operation.getParameters().add(parameter2);

        Parameter parameter3 = new Parameter();
        parameter3.setId(MessageFormat.format("{0}&^^&nodeCodeParam", vmMetadata.getHeader().getId()));
        parameter3.setCode("nodeCode");
        parameter3.setName("节点编号");
        parameter3.setDescription("节点编号");
        parameter3.setReturnValue(false);
        parameter3.setPrimitiveType(true);
        parameter3.setCollection(false);
        parameter3.setCollectionDepth(0);
        parameter3.setModelId("string");
        parameter3.setModelName("string");
        parameter3.setLocation("Body");
        parameter3.setRequired(true);
        operation.getParameters().add(parameter3);

        Parameter parameter4 = new Parameter();
        parameter4.setId(MessageFormat.format("{0}&^^&queryParam", vmMetadata.getHeader().getId()));
        parameter4.setCode("queryParam");
        parameter4.setName("查询参数");
        parameter4.setDescription("查询参数");
        parameter4.setReturnValue(false);
        parameter4.setPrimitiveType(true);
        parameter4.setCollection(false);
        parameter4.setCollectionDepth(0);
        parameter4.setModelId(
                MessageFormat.format("{0}&^^&LookupQueryParam", vmMetadata.getHeader().getId()));
        parameter4.setModelCode("LookupQueryParam");
        parameter4.setModelName("LookupQueryParam");
        parameter4.setModelType(ModelType.DTO);
        parameter4.setStructuredTypeRefId(
                MessageFormat.format("{0}&^^&LookupQueryParam", vmMetadata.getHeader().getId()));
        parameter4.setRefCode("LookupQueryParam");
        parameter4.setRefName("LookupQueryParam");
        parameter4.setLocation("Body");
        parameter4.setRequired(true);
        operation.getParameters().add(parameter4);

        Parameter parameter5 = new Parameter();
        parameter5.setId(MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter5.setCode("responseInfo");
        parameter5.setName("响应信息");
        parameter5.setDescription("响应信息");
        parameter5.setReturnValue(true);
        parameter5.setPrimitiveType(false);
        parameter5.setCollection(false);
        parameter5.setCollectionDepth(0);
        parameter5.setModelId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter5.setModelCode("ResponseInfo");
        parameter5.setModelName("ResponseInfo");
        parameter5.setModelType(ModelType.DTO);
        parameter5.setStructuredTypeRefId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter5.setRefCode("ResponseInfo");
        parameter5.setRefName("ResponseInfo");
        parameter5.setLocation("");
        parameter5.setRequired(true);
        operation.getParameters().add(parameter5);

        return operation;
    }

    private static Operation extensionFilter(GspMetadata vmMetadata) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        Operation operation = new Operation();

        operation.setId(
                MessageFormat.format("{0}&^^&Extension_Filter", vmMetadata.getHeader().getId()));
        operation.setCode("Extension_Filter");
        operation.setName("查询数据");
        operation.setDescription("查询数据带过滤条件");
        operation.setResourceOperationId(
                MessageFormat.format("{0}&^^&Extension_Filter", vmMetadata.getHeader().getId()));
        operation.setHttpMethod("POST");
        operation.setHttpPath("/extension/filter");
        operation.setDeprecated(false);
        operation.setExtend1("Basic");

        operation.setParameters(new ArrayList<>());

        Parameter parameter1 = new Parameter();
        parameter1.setId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setCode("requestInfo");
        parameter1.setName("请求信息");
        parameter1.setDescription("请求信息");
        parameter1.setReturnValue(false);
        parameter1.setPrimitiveType(false);
        parameter1.setCollection(false);
        parameter1.setCollectionDepth(0);
        parameter1.setModelId(
                MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setModelCode("RequestInfo");
        parameter1.setModelName("RequestInfo");
        parameter1.setModelType(ModelType.DTO);
        parameter1.setStructuredTypeRefId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setRefCode("RequestInfo");
        parameter1.setRefName("RequestInfo");
        parameter1.setLocation("Body");
        parameter1.setRequired(false);
        operation.getParameters().add(parameter1);

        Parameter parameter2 = new Parameter();
        parameter2.setId(MessageFormat.format("{0}&^^&EntityFilter", vmMetadata.getHeader().getId()));
        parameter2.setCode("entityFilter");
        parameter2.setName("过滤条件");
        parameter2.setDescription("过滤条件");
        parameter2.setReturnValue(false);
        parameter2.setPrimitiveType(false);
        parameter2.setCollection(false);
        parameter2.setCollectionDepth(0);
        parameter2.setModelId(
                MessageFormat.format("{0}&^^&EntityFilter", vmMetadata.getHeader().getId()));
        parameter2.setModelCode("EntityFilter");
        parameter2.setModelName("EntityFilter");
        parameter2.setModelType(ModelType.DTO);
        parameter2.setStructuredTypeRefId(
                MessageFormat.format("{0}&^^&EntityFilter", vmMetadata.getHeader().getId()));
        parameter2.setRefCode("EntityFilter");
        parameter2.setRefName("EntityFilter");
        parameter2.setLocation("Body");
        parameter2.setRequired(false);
        operation.getParameters().add(parameter2);

        Parameter parameter3 = new Parameter();
        parameter3.setId(MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setCode("responseInfo");
        parameter3.setName("响应信息");
        parameter3.setDescription("响应信息");
        parameter3.setReturnValue(true);
        parameter3.setPrimitiveType(false);
        parameter3.setCollection(false);
        parameter3.setCollectionDepth(0);
        parameter3.setModelId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setModelCode("ResponseInfo");
        parameter3.setModelName("ResponseInfo");
        parameter3.setModelType(ModelType.DTO);
        parameter3.setStructuredTypeRefId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setRefCode("ResponseInfo");
        parameter3.setRefName("ResponseInfo");
        parameter3.setLocation("");
        parameter3.setRequired(true);
        operation.getParameters().add(parameter3);

        return operation;
    }

    private static Operation extendQuery(GspMetadata vmMetadata) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        Operation operation = new Operation();

        operation.setId(MessageFormat.format("{0}&^^&Extend_Query", vmMetadata.getHeader().getId()));
        operation.setCode("Extend_Query");
        operation.setName("扩展过滤查询");
        operation.setDescription("扩展过滤查询");
        operation.setResourceOperationId(
                MessageFormat.format("{0}&^^&Extend_Query", vmMetadata.getHeader().getId()));
        operation.setHttpMethod("PUT");
        operation.setHttpPath("/extension/query");
        operation.setDeprecated(false);
        operation.setExtend1("Basic");

        operation.setParameters(new ArrayList<>());

        Parameter parameter1 = new Parameter();
        parameter1.setId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setCode("requestInfo");
        parameter1.setName("请求信息");
        parameter1.setDescription("请求信息");
        parameter1.setReturnValue(false);
        parameter1.setPrimitiveType(false);
        parameter1.setCollection(false);
        parameter1.setCollectionDepth(0);
        parameter1.setModelId(
                MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setModelCode("RequestInfo");
        parameter1.setModelName("RequestInfo");
        parameter1.setModelType(ModelType.DTO);
        parameter1.setStructuredTypeRefId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setRefCode("RequestInfo");
        parameter1.setRefName("RequestInfo");
        parameter1.setLocation("Body");
        parameter1.setRequired(false);
        operation.getParameters().add(parameter1);

        Parameter parameter2 = new Parameter();
        parameter2.setId(
                MessageFormat.format("{0}&^^&EntityFilterParam", vmMetadata.getHeader().getId()));
        parameter2.setCode("entityFilter");
        parameter2.setName("过滤条件");
        parameter2.setDescription("过滤条件");
        parameter2.setReturnValue(false);
        parameter2.setPrimitiveType(true);
        parameter2.setCollection(false);
        parameter2.setCollectionDepth(0);
        parameter2.setModelId("string");
        parameter2.setModelName("string");
        parameter2.setLocation("Query");
        parameter2.setRequired(false);
        operation.getParameters().add(parameter2);

        Parameter parameter3 = new Parameter();
        parameter3.setId(MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setCode("responseInfo");
        parameter3.setName("响应信息");
        parameter3.setDescription("响应信息");
        parameter3.setReturnValue(true);
        parameter3.setPrimitiveType(false);
        parameter3.setCollection(false);
        parameter3.setCollectionDepth(0);
        parameter3.setModelId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setModelCode("ResponseInfo");
        parameter3.setModelName("ResponseInfo");
        parameter3.setModelType(ModelType.DTO);
        parameter3.setStructuredTypeRefId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setRefCode("ResponseInfo");
        parameter3.setRefName("ResponseInfo");
        parameter3.setLocation("");
        parameter3.setRequired(true);
        operation.getParameters().add(parameter3);

        return operation;
    }

    private static Operation extendRetrieve(GspMetadata vmMetadata) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        Operation operation = new Operation();

        operation.setId(MessageFormat.format("{0}&^^&Extend_Retrieve", vmMetadata.getHeader().getId()));
        operation.setCode("Extend_Retrieve");
        operation.setName("扩展数据检索");
        operation.setDescription("扩展数据检索");
        operation.setResourceOperationId(
                MessageFormat.format("{0}&^^&Extend_Retrieve", vmMetadata.getHeader().getId()));
        operation.setHttpMethod("PUT");
        operation.setHttpPath("/extension/retrieve/{dataId}");
        operation.setDeprecated(false);
        operation.setExtend1("Basic");

        operation.setParameters(new ArrayList<>());

        Parameter parameter1 = new Parameter();
        parameter1.setId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setCode("requestInfo");
        parameter1.setName("请求信息");
        parameter1.setDescription("请求信息");
        parameter1.setReturnValue(false);
        parameter1.setPrimitiveType(false);
        parameter1.setCollection(false);
        parameter1.setCollectionDepth(0);
        parameter1.setModelId(
                MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setModelCode("RequestInfo");
        parameter1.setModelName("RequestInfo");
        parameter1.setModelType(ModelType.DTO);
        parameter1.setStructuredTypeRefId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setRefCode("RequestInfo");
        parameter1.setRefName("RequestInfo");
        parameter1.setLocation("Body");
        parameter1.setRequired(false);
        operation.getParameters().add(parameter1);

        Parameter parameter2 = new Parameter();
        parameter2.setId(MessageFormat.format("{0}&^^&RetrieveParam", vmMetadata.getHeader().getId()));
        parameter2.setCode("dataId");
        parameter2.setName("单据内码");
        parameter2.setDescription("单据内码");
        parameter2.setReturnValue(false);
        parameter2.setPrimitiveType(true);
        parameter2.setCollection(false);
        parameter2.setCollectionDepth(0);
        parameter2.setModelId("string");
        parameter2.setModelName("string");
        parameter2.setLocation("Path");
        parameter2.setRequired(true);
        operation.getParameters().add(parameter2);

        Parameter parameter3 = new Parameter();
        parameter3.setId(MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setCode("responseInfo");
        parameter3.setName("响应信息");
        parameter3.setDescription("响应信息");
        parameter3.setReturnValue(true);
        parameter3.setPrimitiveType(false);
        parameter3.setCollection(false);
        parameter3.setCollectionDepth(0);
        parameter3.setModelId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setModelCode("ResponseInfo");
        parameter3.setModelName("ResponseInfo");
        parameter3.setModelType(ModelType.DTO);
        parameter3.setStructuredTypeRefId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setRefCode("ResponseInfo");
        parameter3.setRefName("ResponseInfo");
        parameter3.setLocation("");
        parameter3.setRequired(true);
        operation.getParameters().add(parameter3);

        return operation;
    }

    private static Operation extendBatchDelete(GspMetadata vmMetadata) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        Operation operation = new Operation();

        operation.setId(
                MessageFormat.format("{0}&^^&Extend_BatchDelete", vmMetadata.getHeader().getId()));
        operation.setCode("Extend_BatchDelete");
        operation.setName("扩展批量删除");
        operation.setDescription("扩展批量删除");
        operation.setResourceOperationId(
                MessageFormat.format("{0}&^^&Extend_BatchDelete", vmMetadata.getHeader().getId()));
        operation.setHttpMethod("PUT");
        operation.setHttpPath("/extension/batchdelete");
        operation.setDeprecated(false);
        operation.setExtend1("Basic");

        operation.setParameters(new ArrayList<>());

        Parameter parameter1 = new Parameter();
        parameter1.setId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setCode("requestInfo");
        parameter1.setName("请求信息");
        parameter1.setDescription("请求信息");
        parameter1.setReturnValue(false);
        parameter1.setPrimitiveType(false);
        parameter1.setCollection(false);
        parameter1.setCollectionDepth(0);
        parameter1.setModelId(
                MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setModelCode("RequestInfo");
        parameter1.setModelName("RequestInfo");
        parameter1.setModelType(ModelType.DTO);
        parameter1.setStructuredTypeRefId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setRefCode("RequestInfo");
        parameter1.setRefName("RequestInfo");
        parameter1.setLocation("Body");
        parameter1.setRequired(false);
        operation.getParameters().add(parameter1);

        Parameter parameter2 = new Parameter();
        parameter2.setId(
                MessageFormat.format("{0}&^^&BatchDeleteParam", vmMetadata.getHeader().getId()));
        parameter2.setCode("ids");
        parameter2.setName("单据内码列表");
        parameter2.setDescription("单据内码列表");
        parameter2.setReturnValue(false);
        parameter2.setPrimitiveType(true);
        parameter2.setCollection(false);
        parameter2.setCollectionDepth(0);
        parameter2.setModelId("string");
        parameter2.setModelName("string");
        parameter2.setLocation("Query");
        parameter2.setRequired(true);
        operation.getParameters().add(parameter2);

        Parameter parameter3 = new Parameter();
        parameter3.setId(MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setCode("responseInfo");
        parameter3.setName("响应信息");
        parameter3.setDescription("响应信息");
        parameter3.setReturnValue(true);
        parameter3.setPrimitiveType(false);
        parameter3.setCollection(false);
        parameter3.setCollectionDepth(0);
        parameter3.setModelId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setModelCode("ResponseInfo");
        parameter3.setModelName("ResponseInfo");
        parameter3.setModelType(ModelType.DTO);
        parameter3.setStructuredTypeRefId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setRefCode("ResponseInfo");
        parameter3.setRefName("ResponseInfo");
        parameter3.setLocation("");
        parameter3.setRequired(true);
        operation.getParameters().add(parameter3);

        return operation;
    }

    private static Operation extendDelete(GspMetadata vmMetadata) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        Operation operation = new Operation();

        operation.setId(MessageFormat.format("{0}&^^&Extend_Delete", vmMetadata.getHeader().getId()));
        operation.setCode("Extend_Delete");
        operation.setName("扩展删除");
        operation.setDescription("扩展删除");
        operation.setResourceOperationId(
                MessageFormat.format("{0}&^^&Extend_Delete", vmMetadata.getHeader().getId()));
        operation.setHttpMethod("PUT");
        operation.setHttpPath("/extension/delete/{dataId}");
        operation.setDeprecated(false);
        operation.setExtend1("Basic");

        operation.setParameters(new ArrayList<>());

        Parameter parameter1 = new Parameter();
        parameter1.setId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setCode("requestInfo");
        parameter1.setName("请求信息");
        parameter1.setDescription("请求信息");
        parameter1.setReturnValue(false);
        parameter1.setPrimitiveType(false);
        parameter1.setCollection(false);
        parameter1.setCollectionDepth(0);
        parameter1.setModelId(
                MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setModelCode("RequestInfo");
        parameter1.setModelName("RequestInfo");
        parameter1.setModelType(ModelType.DTO);
        parameter1.setStructuredTypeRefId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setRefCode("RequestInfo");
        parameter1.setRefName("RequestInfo");
        parameter1.setLocation("Body");
        parameter1.setRequired(false);
        operation.getParameters().add(parameter1);

        Parameter parameter2 = new Parameter();
        parameter2.setId(MessageFormat.format("{0}&^^&DeleteParam", vmMetadata.getHeader().getId()));
        parameter2.setCode("dataId");
        parameter2.setName("单据内码");
        parameter2.setDescription("单据内码");
        parameter2.setReturnValue(false);
        parameter2.setPrimitiveType(true);
        parameter2.setCollection(false);
        parameter2.setCollectionDepth(0);
        parameter2.setModelId("string");
        parameter2.setModelName("string");
        parameter2.setLocation("Path");
        parameter2.setRequired(false);
        operation.getParameters().add(parameter2);

        Parameter parameter3 = new Parameter();
        parameter3.setId(MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setCode("responseInfo");
        parameter3.setName("响应信息");
        parameter3.setDescription("响应信息");
        parameter3.setReturnValue(true);
        parameter3.setPrimitiveType(false);
        parameter3.setCollection(false);
        parameter3.setCollectionDepth(0);
        parameter3.setModelId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setModelCode("ResponseInfo");
        parameter3.setModelName("ResponseInfo");
        parameter3.setModelType(ModelType.DTO);
        parameter3.setStructuredTypeRefId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setRefCode("ResponseInfo");
        parameter3.setRefName("ResponseInfo");
        parameter3.setLocation("");
        parameter3.setRequired(true);
        operation.getParameters().add(parameter3);

        return operation;
    }

    private static List<Operation> getDeleteChildOperationList(GspMetadata vmMetadata) {
        List<Operation> operations = new ArrayList<>();
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        GspViewObject mainObject = viewModel.getMainObject();
        if (mainObject == null || mainObject.getContainChildObjects().size() <= 0) {
            return operations;
        }
        for (IGspCommonObject obj : mainObject.getContainChildObjects()) {
            String nodesCodesStringBuidler = "";
            getDeleteChildActions((GspViewObject)obj, operations, nodesCodesStringBuidler, vmMetadata);
        }
        return operations;
    }

    private static void getDeleteChildActions(
            GspViewObject viewObject,
            List<Operation> operations,
            String nodesCodesStringBuidler,
            GspMetadata vmMetadata) {
        nodesCodesStringBuidler =
                StringUtils.isBlank(nodesCodesStringBuidler)
                        ? viewObject.getCode()
                        : nodesCodesStringBuidler + "#" + viewObject.getCode();
        Operation operation =
                GetDeleteChildOperation(vmMetadata, viewObject.getCode(), nodesCodesStringBuidler);
        if (operation != null) {
            operations.add(operation);
        }
        if (viewObject.getContainChildObjects().size() > 0) {
            for (IGspCommonObject obj : viewObject.getContainChildObjects()) {
                getDeleteChildActions((GspViewObject)obj, operations, nodesCodesStringBuidler, vmMetadata);
            }
        }
    }

    private static Operation GetDeleteChildOperation(
            GspMetadata vmMetadata, String currentNodeCode, String nodeCodesStringBuilder) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        String[] nodeCodes = nodeCodesStringBuilder.split("#");
        if (nodeCodes.length <= 0) {
            return null;
        }
        Operation operation = new Operation();
        operation.setParameters(new ArrayList<>());
        operation.setHttpPath("/{rootId}");
        getDeleteChildParam(viewModel, operation, "rootId");
        String methodCode = "DeleteChild";
        String methodId = "DeleteChild";
        for (String nodeCode : nodeCodes) {
            methodCode = methodCode + String.format("_%s", nodeCode);
            methodId = methodId + MessageFormat.format("##{0}", nodeCode);
            operation.setHttpPath(
                    operation.getHttpPath()
                            + MessageFormat.format("/{0}", nodeCode.toLowerCase())
                            + "/{"
                            + MessageFormat.format(
                            "{0}Id", StringUtils.toCamelCase(nodeCode))
                            + "}");
            getDeleteChildParam(
                    viewModel,
                    operation,
                    StringUtils.toCamelCase(nodeCode) + "Id");
        }

        operation.setId(MessageFormat.format("{0}&^^&{1}", vmMetadata.getHeader().getId(), methodId));
        operation.setCode(methodCode);
        operation.setName(MessageFormat.format("删除从表{0}数据", currentNodeCode));
        operation.setDescription(MessageFormat.format("删除从表{0}数据", currentNodeCode));
        operation.setResourceOperationId(
                MessageFormat.format("{0}&^^&{1}", vmMetadata.getHeader().getId(), methodId));
        operation.setHttpMethod("DELETE");
        operation.setDeprecated(false);
        operation.setExtend1("Basic"); // 表示该操作是基本操作中的增加从表数据的操作
        operation.setExtend2(nodeCodesStringBuilder); // 用于存储NodeCode拼接信息（A#B#C）

        return operation;
    }

    private static void getDeleteChildParam(
            GspViewModel viewModel, Operation operation, String currentParamCode) {
        Parameter serviceParam = new Parameter();
        serviceParam.setId(viewModel.getId() + currentParamCode);
        serviceParam.setCode(currentParamCode);
        String nodeCode = currentParamCode.substring(0, currentParamCode.length() - 2);
        if (nodeCode.equals("root")) {
            serviceParam.setName(MessageFormat.format("主表{0}数据ID", nodeCode));
            serviceParam.setDescription(MessageFormat.format("主表{0}数据的所属实体数据的唯一标识", nodeCode));
        } else {
            serviceParam.setName(MessageFormat.format("从表{0}数据ID", nodeCode));
            serviceParam.setDescription(MessageFormat.format("从表{0}数据的所属实体数据的唯一标识", nodeCode));
        }
        serviceParam.setReturnValue(false);
        serviceParam.setPrimitiveType(true);
        serviceParam.setCollection(false);
        serviceParam.setCollectionDepth(0);
        serviceParam.setModelId("string");
        serviceParam.setModelName("string");
        serviceParam.setLocation("Path");
        serviceParam.setRequired(true);
        operation.getParameters().add(serviceParam);
    }

    private static List<Operation> getCreateChildOperationList(GspMetadata vmMetadata) {
        List<Operation> operations = new ArrayList<>();
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        GspViewObject mainObject = viewModel.getMainObject();
        if (mainObject == null || mainObject.getContainChildObjects().size() <= 0) {
            return operations;
        }
        for (IGspCommonObject obj : mainObject.getContainChildObjects()) {
            String nodesCodesStringBuidler = "";
            getCreateChildActions((GspViewObject)obj, operations, nodesCodesStringBuidler, vmMetadata);
        }
        return operations;
    }

    private static void getCreateChildActions(
            GspViewObject viewObject,
            List<Operation> operations,
            String nodesCodesStringBuidler,
            GspMetadata vmMetadata) {
        nodesCodesStringBuidler =
                StringUtils.isBlank(nodesCodesStringBuidler)
                        ? viewObject.getCode()
                        : nodesCodesStringBuidler + "#" + viewObject.getCode();
        Operation operation =
                getCreateChildOperation(vmMetadata, viewObject.getCode(), nodesCodesStringBuidler);
        if (operation != null) {
            operations.add(operation);
        }
        if (viewObject.getContainChildObjects().size() > 0) {
            for (IGspCommonObject obj : viewObject.getContainChildObjects()) {
                getCreateChildActions((GspViewObject)obj, operations, nodesCodesStringBuidler, vmMetadata);
            }
        }
    }

    private static Operation getCreateChildOperation(
            GspMetadata vmMetadata, String currentNodeCode, String nodeCodesStringBuilder) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        String[] nodeCodes = nodeCodesStringBuilder.split("#");
        if (nodeCodes.length <= 0) {
            return null;
        }
        Operation operation = new Operation();
        operation.setParameters(new ArrayList<>());
        Parameter parameter1 = new Parameter();
        parameter1.setId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setCode("requestInfo");
        parameter1.setName("请求信息");
        parameter1.setDescription("请求信息");
        parameter1.setReturnValue(false);
        parameter1.setPrimitiveType(false);
        parameter1.setCollection(false);
        parameter1.setCollectionDepth(0);
        parameter1.setModelId(
                MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setModelCode("RequestInfo");
        parameter1.setModelName("RequestInfo");
        parameter1.setModelType(ModelType.DTO);
        parameter1.setStructuredTypeRefId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setRefCode("RequestInfo");
        parameter1.setRefName("RequestInfo");
        parameter1.setLocation("Body");
        parameter1.setRequired(false);
        operation.getParameters().add(parameter1);

        operation.setHttpPath("/{rootId}");
        getCreateChildParam(viewModel, operation, "rootId");
        String methodCode = "CreateChild";
        String methodId = "CreateChild";
        int count = 0;
        for (String nodeCode : nodeCodes) {
            methodCode = methodCode + String.format("_%s", nodeCode);
            methodId = methodId + MessageFormat.format("##{0}", nodeCode);
            if (count < nodeCodes.length - 1) {
                operation.setHttpPath(
                        operation.getHttpPath()
                                + MessageFormat.format("/{0}", nodeCode.toLowerCase())
                                + "/{"
                                + MessageFormat.format(
                                "{0}Id", StringUtils.toCamelCase(nodeCode))
                                + "}");
                getCreateChildParam(
                        viewModel,
                        operation,
                        StringUtils.toCamelCase(nodeCode) + "Id");
            } else {
                operation.setHttpPath(
                        operation.getHttpPath() + MessageFormat.format("/{0}", nodeCode.toLowerCase()));
            }
            count++;
        }

        operation.setId(MessageFormat.format("{0}&^^&{1}", vmMetadata.getHeader().getId(), methodId));
        operation.setCode(methodCode);
        operation.setName(MessageFormat.format("新增从表{0}数据", currentNodeCode));
        operation.setDescription(MessageFormat.format("新增从表{0}数据", currentNodeCode));
        operation.setResourceOperationId(
                MessageFormat.format("{0}&^^&{1}", vmMetadata.getHeader().getId(), methodId));
        operation.setHttpMethod("POST");
        operation.setDeprecated(false);
        operation.setExtend1("Basic"); // 表示该操作是基本操作中的增加从表数据的操作
        operation.setExtend2(nodeCodesStringBuilder); // 用于存储NodeCode拼接信息（A#B#C）

        Parameter parameter2 = new Parameter();
        parameter2.setId(MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter2.setCode("responseInfo");
        parameter2.setName("响应信息");
        parameter2.setDescription("响应信息");
        parameter2.setReturnValue(true);
        parameter2.setPrimitiveType(false);
        parameter2.setCollection(false);
        parameter2.setCollectionDepth(0);
        parameter2.setModelId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter2.setModelCode("ResponseInfo");
        parameter2.setModelName("ResponseInfo");
        parameter2.setModelType(ModelType.DTO);
        parameter2.setStructuredTypeRefId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter2.setRefCode("ResponseInfo");
        parameter2.setRefName("ResponseInfo");
        parameter2.setLocation("");
        parameter2.setRequired(true);
        operation.getParameters().add(parameter2);

        return operation;
    }

    private static void getCreateChildParam(
            GspViewModel viewModel, Operation operation, String currentParamCode) {
        Parameter serviceParam = new Parameter();
        serviceParam.setId(viewModel.getId() + currentParamCode);
        serviceParam.setCode(currentParamCode);
        String nodeCode = currentParamCode.substring(0, currentParamCode.length() - 2);
        if (nodeCode.equals("root")) {
            serviceParam.setName(MessageFormat.format("主表{0}数据ID", nodeCode));
            serviceParam.setDescription(MessageFormat.format("主表{0}数据的所属实体数据的唯一标识", nodeCode));
        } else {
            serviceParam.setName(MessageFormat.format("从表{0}数据ID", nodeCode));
            serviceParam.setDescription(MessageFormat.format("从表{0}数据的所属实体数据的唯一标识", nodeCode));
        }
        serviceParam.setReturnValue(false);
        serviceParam.setPrimitiveType(true);
        serviceParam.setCollection(false);
        serviceParam.setCollectionDepth(0);
        serviceParam.setModelId("string");
        serviceParam.setModelName("string");
        serviceParam.setLocation("Path");
        serviceParam.setRequired(true);
        operation.getParameters().add(serviceParam);
    }

    private static Operation getElementHelp(GspMetadata vmMetadata) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        Operation operation = new Operation();

        operation.setId(MessageFormat.format("{0}&^^&ElementHelp", vmMetadata.getHeader().getId()));
        operation.setCode("GetElementHelp");
        operation.setName("获取字段帮助");
        operation.setDescription("根据labelId获取字段帮助");
        operation.setResourceOperationId(
                MessageFormat.format("{0}&^^&ElementHelp", vmMetadata.getHeader().getId()));
        operation.setHttpMethod("GET");
        operation.setHttpPath("elementhelps/{labelId}");
        operation.setDeprecated(false);
        operation.setExtend1("Basic");

        operation.setParameters(new ArrayList<>());

        Parameter parameter1 = new Parameter();
        parameter1.setId(MessageFormat.format("{0}&^^&labelIdParam", vmMetadata.getHeader().getId()));
        parameter1.setCode("labelId");
        parameter1.setName("标签Id");
        parameter1.setDescription("标签内码");
        parameter1.setReturnValue(false);
        parameter1.setPrimitiveType(true);
        parameter1.setCollection(false);
        parameter1.setCollectionDepth(0);
        parameter1.setModelId("string");
        parameter1.setModelName("string");
        parameter1.setLocation("Path");
        parameter1.setRequired(true);
        operation.getParameters().add(parameter1);

        Parameter parameter2 = new Parameter();
        parameter2.setId(MessageFormat.format("{0}&^^&nodeCodeParam", vmMetadata.getHeader().getId()));
        parameter2.setCode("nodeCode");
        parameter2.setName("节点编号");
        parameter2.setDescription("节点编号");
        parameter2.setReturnValue(false);
        parameter2.setPrimitiveType(true);
        parameter2.setCollection(false);
        parameter2.setCollectionDepth(0);
        parameter2.setModelId("string");
        parameter2.setModelName("string");
        parameter2.setLocation("Query");
        parameter2.setRequired(true);
        operation.getParameters().add(parameter2);

        Parameter parameter3 = new Parameter();
        parameter3.setId(MessageFormat.format("{0}&^^&queryParam", vmMetadata.getHeader().getId()));
        parameter3.setCode("queryParam");
        parameter3.setName("查询参数");
        parameter3.setDescription("查询参数");
        parameter3.setReturnValue(false);
        parameter3.setPrimitiveType(true);
        parameter3.setCollection(false);
        parameter3.setCollectionDepth(0);
        parameter3.setModelId("string");
        parameter3.setModelName("string");
        parameter3.setLocation("Query");
        parameter3.setRequired(true);
        operation.getParameters().add(parameter3);

        Parameter parameter4 = new Parameter();
        parameter4.setId(
                MessageFormat.format("{0}&^^&ElementHelpRetParam", vmMetadata.getHeader().getId()));
        parameter4.setCode("return");
        parameter4.setName("返回值");
        parameter4.setDescription("返回值");
        parameter4.setReturnValue(true);
        parameter4.setPrimitiveType(false);
        parameter4.setCollection(false);
        parameter4.setCollectionDepth(0);
        parameter4.setModelId(
                MessageFormat.format("{0}&^^&LookupResult", vmMetadata.getHeader().getId()));
        parameter4.setModelCode("LookupResult");
        parameter4.setModelName("LookupResult");
        parameter4.setModelType(ModelType.DTO);
        parameter4.setStructuredTypeRefId(
                MessageFormat.format("{0}&^^&LookupResult", vmMetadata.getHeader().getId()));
        parameter4.setRefCode("LookupResult");
        parameter4.setRefName("LookupResult");
        parameter4.setLocation("");
        parameter4.setRequired(true);
        operation.getParameters().add(parameter4);

        return operation;
    }

    private static Operation cancel(GspMetadata vmMetadata) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        Operation operation = new Operation();
        operation.setId(MessageFormat.format("{0}&^^&Cancel", vmMetadata.getHeader().getId()));
        operation.setCode("Cancel");
        operation.setName("取消");
        operation.setDescription("取消");
        operation.setResourceOperationId(
                MessageFormat.format("{0}&^^&Cancel", vmMetadata.getHeader().getId()));
        operation.setHttpMethod("POST");
        operation.setHttpPath("service/cancel");
        operation.setDeprecated(false);
        operation.setExtend1("Basic");

        return operation;
    }

    private static Operation save(GspMetadata vmMetadata) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        Operation operation = new Operation();

        operation.setId(MessageFormat.format("{0}&^^&Save", vmMetadata.getHeader().getId()));
        operation.setCode("Save");
        operation.setName("保存");
        operation.setDescription("保存");
        operation.setResourceOperationId(MessageFormat.format("{0}&^^&Save", vmMetadata.getHeader().getId()));
        operation.setHttpMethod("PUT");
        operation.setHttpPath("");
        operation.setDeprecated(false);
        operation.setExtend1("Basic");

        operation.setParameters(new ArrayList<>());

        Parameter parameter1 = new Parameter();
        parameter1.setId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setCode("requestInfo");
        parameter1.setName("请求信息");
        parameter1.setDescription("请求信息");
        parameter1.setReturnValue(false);
        parameter1.setPrimitiveType(false);
        parameter1.setCollection(false);
        parameter1.setCollectionDepth(0);
        parameter1.setModelId(
                MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setModelCode("RequestInfo");
        parameter1.setModelName("RequestInfo");
        parameter1.setModelType(ModelType.DTO);
        parameter1.setStructuredTypeRefId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setRefCode("RequestInfo");
        parameter1.setRefName("RequestInfo");
        parameter1.setLocation("Body");
        parameter1.setRequired(false);
        operation.getParameters().add(parameter1);

        Parameter parameter2 = new Parameter();
        parameter2.setId(MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter2.setCode("responseInfo");
        parameter2.setName("响应信息");
        parameter2.setDescription("响应信息");
        parameter2.setReturnValue(true);
        parameter2.setPrimitiveType(false);
        parameter2.setCollection(false);
        parameter2.setCollectionDepth(0);
        parameter2.setModelId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter2.setModelCode("ResponseInfo");
        parameter2.setModelName("ResponseInfo");
        parameter2.setModelType(ModelType.DTO);
        parameter2.setStructuredTypeRefId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter2.setRefCode("ResponseInfo");
        parameter2.setRefName("ResponseInfo");
        parameter2.setLocation("");
        parameter2.setRequired(true);
        operation.getParameters().add(parameter2);

        return operation;
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
        parameter1.setId(
                MessageFormat.format("{0}&^^&EntityFilterParam", vmMetadata.getHeader().getId()));
        parameter1.setCode("entityFilter");
        parameter1.setName("过滤条件");
        parameter1.setDescription("过滤条件");
        parameter1.setReturnValue(false);
        parameter1.setPrimitiveType(true);
        parameter1.setCollection(false);
        parameter1.setCollectionDepth(0);
        parameter1.setModelId("string");
        parameter1.setModelName("string");
        parameter1.setLocation("Query");
        parameter1.setRequired(true);
        operation.getParameters().add(parameter1);

        Parameter parameter2 = new Parameter();
        parameter2.setId(MessageFormat.format("{0}&^^&QueryParam", vmMetadata.getHeader().getId()));
        // TODO code要做ToCamel处理
        parameter2.setCode(viewModel.getMainObject().getCode());
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

    private static Operation retrieveChildByIndex(GspMetadata vmMetadata) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        Operation operation = new Operation();

        operation.setId(MessageFormat.format("{0}&^^&QueryChild", vmMetadata.getHeader().getId()));
        operation.setCode("QueryChild");
        operation.setName("子表查询");
        operation.setDescription("子表数据查询");
        operation.setResourceOperationId(
                MessageFormat.format("{0}&^^&QueryChild", vmMetadata.getHeader().getId()));
        operation.setHttpMethod("PUT");
        operation.setHttpPath("/service/querychild");
        operation.setDeprecated(false);
        operation.setExtend1("Basic");

        operation.setParameters(new ArrayList<>());

        Parameter parameter1 = new Parameter();
        parameter1.setId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setCode("requestInfo");
        parameter1.setName("请求信息");
        parameter1.setDescription("请求信息");
        parameter1.setReturnValue(false);
        parameter1.setPrimitiveType(false);
        parameter1.setCollection(false);
        parameter1.setCollectionDepth(0);
        parameter1.setModelId(
                MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setModelCode("RequestInfo");
        parameter1.setModelName("RequestInfo");
        parameter1.setModelType(ModelType.DTO);
        parameter1.setStructuredTypeRefId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setRefCode("RequestInfo");
        parameter1.setRefName("RequestInfo");
        parameter1.setLocation("Body");
        parameter1.setRequired(false);
        operation.getParameters().add(parameter1);

        Parameter parameter2 = new Parameter();
        parameter2.setId(MessageFormat.format("{0}&^^&nodeCodes", vmMetadata.getHeader().getId()));
        parameter2.setCode("nodeCodes");
        parameter2.setName("节点编号列表");
        parameter2.setDescription("节点编号列表");
        parameter2.setReturnValue(false);
        parameter2.setPrimitiveType(true);
        parameter2.setCollection(true);
        parameter2.setCollectionDepth(1);
        parameter2.setModelId("string");
        parameter2.setModelName("string");
        parameter2.setLocation("Body");
        parameter2.setRequired(true);
        operation.getParameters().add(parameter2);

        Parameter parameter3 = new Parameter();
        parameter3.setId(MessageFormat.format("{0}&^^&ids", vmMetadata.getHeader().getId()));
        parameter3.setCode("ids");
        parameter3.setName("内码列表");
        parameter3.setDescription("内码列表");
        parameter3.setReturnValue(false);
        parameter3.setPrimitiveType(true);
        parameter3.setCollection(true);
        parameter3.setCollectionDepth(1);
        parameter3.setModelId("string");
        parameter3.setModelName("string");
        parameter3.setLocation("Body");
        parameter3.setRequired(true);
        operation.getParameters().add(parameter3);

        Parameter parameter4 = new Parameter();
        parameter4.setId(MessageFormat.format("{0}&^^&pagination", vmMetadata.getHeader().getId()));
        parameter4.setCode("pagination");
        parameter4.setName("分页信息");
        parameter4.setDescription("分页信息");
        parameter4.setReturnValue(false);
        parameter4.setPrimitiveType(false);
        parameter4.setCollection(false);
        parameter4.setCollectionDepth(0);
        parameter4.setModelId(
                MessageFormat.format("{0}&^^&Pagination", vmMetadata.getHeader().getId()));
        parameter4.setModelCode("Pagination");
        parameter4.setModelName("Pagination");
        parameter4.setModelType(ModelType.DTO);
        parameter4.setStructuredTypeRefId(MessageFormat.format("{0}&^^&Pagination", vmMetadata.getHeader().getId()));
        parameter4.setRefCode("Pagination");
        parameter4.setRefName("Pagination");
        parameter4.setLocation("Body");
        parameter4.setRequired(true);
        operation.getParameters().add(parameter4);

        Parameter parameter5 = new Parameter();
        parameter5.setId(MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter5.setCode("responseInfo");
        parameter5.setName("响应信息");
        parameter5.setDescription("响应信息");
        parameter5.setReturnValue(true);
        parameter5.setPrimitiveType(false);
        parameter5.setCollection(false);
        parameter5.setCollectionDepth(0);
        parameter5.setModelId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter5.setModelCode("ResponseInfo");
        parameter5.setModelName("ResponseInfo");
        parameter5.setModelType(ModelType.DTO);
        parameter5.setStructuredTypeRefId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter5.setRefCode("ResponseInfo");
        parameter5.setRefName("ResponseInfo");
        parameter5.setLocation("");
        parameter5.setRequired(true);
        operation.getParameters().add(parameter5);

        return operation;
    }

    private static Operation retrieveWithChildPagination(GspMetadata vmMetadata) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        Operation operation = new Operation();

        operation.setId(
                MessageFormat.format("{0}&^^&RetrieveWithChildPagination", vmMetadata.getHeader().getId()));
        operation.setCode("RetrieveWithChildPagination");
        operation.setName("数据检索");
        operation.setDescription("数据检索(包含子表数据)");
        operation.setResourceOperationId(
                MessageFormat.format("{0}&^^&RetrieveWithChildPagination", vmMetadata.getHeader().getId()));
        operation.setHttpMethod("PUT");
        operation.setHttpPath("/service/retrieve/{dataId}");
        operation.setDeprecated(false);
        operation.setExtend1("Basic");

        operation.setParameters(new ArrayList<>());

        Parameter parameter1 = new Parameter();
        parameter1.setId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setCode("requestInfo");
        parameter1.setName("请求信息");
        parameter1.setDescription("请求信息");
        parameter1.setReturnValue(false);
        parameter1.setPrimitiveType(false);
        parameter1.setCollection(false);
        parameter1.setCollectionDepth(0);
        parameter1.setModelId(
                MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setModelCode("RequestInfo");
        parameter1.setModelName("RequestInfo");
        parameter1.setModelType(ModelType.DTO);
        parameter1.setStructuredTypeRefId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setRefCode("RequestInfo");
        parameter1.setRefName("RequestInfo");
        parameter1.setLocation("Body");
        parameter1.setRequired(false);
        operation.getParameters().add(parameter1);

        Parameter parameter2 = new Parameter();
        parameter2.setId(MessageFormat.format("{0}&^^&dataId", vmMetadata.getHeader().getId()));
        parameter2.setCode("dataId");
        parameter2.setName("单据内码");
        parameter2.setDescription("单据内码");
        parameter2.setReturnValue(false);
        parameter2.setPrimitiveType(true);
        parameter2.setCollection(false);
        parameter2.setCollectionDepth(0);
        parameter2.setModelId("string");
        parameter2.setModelName("string");
        parameter2.setLocation("Path");
        parameter2.setRequired(true);
        operation.getParameters().add(parameter2);

        Parameter parameter3 = new Parameter();
        parameter3.setId(MessageFormat.format("{0}&^^&retrieveParam", vmMetadata.getHeader().getId()));
        parameter3.setCode("retrieveParam");
        parameter3.setName("查询参数");
        parameter3.setDescription("查询参数");
        parameter3.setReturnValue(false);
        parameter3.setPrimitiveType(false);
        parameter3.setCollection(false);
        parameter3.setCollectionDepth(0);
        parameter3.setModelId(
                MessageFormat.format("{0}&^^&RetrieveParam", vmMetadata.getHeader().getId()));
        parameter3.setModelCode("RetrieveParam");
        parameter3.setModelName("RetrieveParam");
        parameter3.setModelType(ModelType.DTO);
        parameter3.setStructuredTypeRefId(
                MessageFormat.format("{0}&^^&RetrieveParam", vmMetadata.getHeader().getId()));
        parameter3.setRefCode("RetrieveParam");
        parameter3.setRefName("RetrieveParam");
        parameter3.setLocation("Body");
        parameter3.setRequired(true);
        operation.getParameters().add(parameter3);

        Parameter parameter4 = new Parameter();
        parameter4.setId(MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter4.setCode("responseInfo");
        parameter4.setName("响应信息");
        parameter4.setDescription("响应信息");
        parameter4.setReturnValue(true);
        parameter4.setPrimitiveType(false);
        parameter4.setCollection(false);
        parameter4.setCollectionDepth(0);
        parameter4.setModelId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter4.setModelCode("ResponseInfo");
        parameter4.setModelName("ResponseInfo");
        parameter4.setModelType(ModelType.DTO);
        parameter4.setStructuredTypeRefId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter4.setRefCode("ResponseInfo");
        parameter4.setRefName("ResponseInfo");
        parameter4.setLocation("");
        parameter4.setRequired(true);
        operation.getParameters().add(parameter4);

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

        Parameter parameter1 = new Parameter();
        parameter1.setId(MessageFormat.format("{0}&^^&RetrieveParam", vmMetadata.getHeader().getId()));
        parameter1.setCode("dataId");
        parameter1.setName("单据内码");
        parameter1.setDescription("单据内码");
        parameter1.setReturnValue(false);
        parameter1.setPrimitiveType(true);
        parameter1.setCollection(false);
        parameter1.setCollectionDepth(0);
        parameter1.setModelId("string");
        parameter1.setModelName("string");
        parameter1.setLocation("Path");
        parameter1.setRequired(true);
        operation.getParameters().add(parameter1);

        Parameter parameter2 = new Parameter();
        parameter2.setId(MessageFormat.format("{0}&^^&RetrieveParam1", vmMetadata.getHeader().getId()));
        // TODO code要做ToCamel处理
        parameter2.setCode(viewModel.getMainObject().getCode());
        parameter2.setName(viewModel.getMainObject().getName());
        parameter2.setDescription("实体模型");
        parameter2.setReturnValue(true);
        parameter2.setPrimitiveType(false);
        parameter2.setCollection(false);
        parameter2.setCollectionDepth(0);
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

    private static Operation update(GspMetadata vmMetadata) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        Operation operation = new Operation();

        operation.setId(MessageFormat.format("{0}&^^&Update", vmMetadata.getHeader().getId()));
        operation.setCode("Update");
        operation.setName("修改");
        operation.setDescription("修改");
        operation.setResourceOperationId(
                MessageFormat.format("{0}&^^&Update", vmMetadata.getHeader().getId()));
        operation.setHttpMethod("PATCH");
        operation.setHttpPath("");
        operation.setDeprecated(false);
        operation.setExtend1("Basic");

        operation.setParameters(new ArrayList<>());

        Parameter parameter1 = new Parameter();
        parameter1.setId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setCode("requestInfo");
        parameter1.setName("请求信息");
        parameter1.setDescription("请求信息");
        parameter1.setReturnValue(false);
        parameter1.setPrimitiveType(false);
        parameter1.setCollection(false);
        parameter1.setCollectionDepth(0);
        parameter1.setModelId(
                MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setModelCode("RequestInfo");
        parameter1.setModelName("RequestInfo");
        parameter1.setModelType(ModelType.DTO);
        parameter1.setStructuredTypeRefId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setRefCode("RequestInfo");
        parameter1.setRefName("RequestInfo");
        parameter1.setLocation("Body");
        parameter1.setRequired(false);
        operation.getParameters().add(parameter1);

        Parameter parameter2 = new Parameter();
        parameter2.setId(MessageFormat.format("{0}&^^&UpdateParam", vmMetadata.getHeader().getId()));
        parameter2.setCode("changeDetail");
        parameter2.setName("数据变更集");
        parameter2.setDescription("数据变更集");
        parameter2.setReturnValue(false);
        parameter2.setPrimitiveType(false);
        parameter2.setCollection(false);
        parameter2.setCollectionDepth(0);
        parameter2.setModelId(
                MessageFormat.format("{0}&^^&ChangeDetail", vmMetadata.getHeader().getId()));
        parameter2.setModelCode("ChangeDetail");
        parameter2.setModelName("ChangeDetail");
        parameter2.setModelType(ModelType.DTO);
        parameter2.setStructuredTypeRefId(
                MessageFormat.format("{0}&^^&ChangeDetail", vmMetadata.getHeader().getId()));
        parameter2.setRefCode("ChangeDetail");
        parameter2.setRefName("ChangeDetail");
        parameter2.setLocation("Body");
        parameter2.setRequired(true);
        operation.getParameters().add(parameter2);

        Parameter parameter3 = new Parameter();
        parameter3.setId(MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setCode("responseInfo");
        parameter3.setName("响应信息");
        parameter3.setDescription("响应信息");
        parameter3.setReturnValue(true);
        parameter3.setPrimitiveType(false);
        parameter3.setCollection(false);
        parameter3.setCollectionDepth(0);
        parameter3.setModelId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setModelCode("ResponseInfo");
        parameter3.setModelName("ResponseInfo");
        parameter3.setModelType(ModelType.DTO);
        parameter3.setStructuredTypeRefId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setRefCode("ResponseInfo");
        parameter3.setRefName("ResponseInfo");
        parameter3.setLocation("");
        parameter3.setRequired(true);
        operation.getParameters().add(parameter3);

        return operation;
    }

    private static Operation deleteAndSave(GspMetadata vmMetadata) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        Operation operation = new Operation();

        operation.setId(MessageFormat.format("{0}&^^&DeleteAndSave", vmMetadata.getHeader().getId()));
        operation.setCode("DeleteAndSave");
        operation.setName("删除并保存");
        operation.setDescription("删除并保存");
        operation.setResourceOperationId(
                MessageFormat.format("{0}&^^&DeleteAndSave", vmMetadata.getHeader().getId()));
        operation.setHttpMethod("PUT");
        operation.setHttpPath("/service/delete/{dataId}");
        operation.setDeprecated(false);
        operation.setExtend1("Basic");

        operation.setParameters(new ArrayList<>());

        Parameter parameter1 = new Parameter();
        parameter1.setId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setCode("requestInfo");
        parameter1.setName("请求信息");
        parameter1.setDescription("请求信息");
        parameter1.setReturnValue(false);
        parameter1.setPrimitiveType(false);
        parameter1.setCollection(false);
        parameter1.setCollectionDepth(0);
        parameter1.setModelId(
                MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setModelCode("RequestInfo");
        parameter1.setModelName("RequestInfo");
        parameter1.setModelType(ModelType.DTO);
        parameter1.setStructuredTypeRefId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setRefCode("RequestInfo");
        parameter1.setRefName("RequestInfo");
        parameter1.setLocation("Body");
        parameter1.setRequired(false);
        operation.getParameters().add(parameter1);

        Parameter parameter2 = new Parameter();
        parameter2.setId(MessageFormat.format("{0}&^^&DeleteParam", vmMetadata.getHeader().getId()));
        parameter2.setCode("dataId");
        parameter2.setName("单据内码");
        parameter2.setDescription("单据内码");
        parameter2.setReturnValue(false);
        parameter2.setPrimitiveType(true);
        parameter2.setCollection(false);
        parameter2.setCollectionDepth(0);
        parameter2.setModelId("string");
        parameter2.setModelName("string");
        parameter2.setLocation("Path");
        parameter2.setRequired(true);
        operation.getParameters().add(parameter2);

        Parameter parameter3 = new Parameter();
        parameter3.setId(MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setCode("responseInfo");
        parameter3.setName("响应信息");
        parameter3.setDescription("响应信息");
        parameter3.setReturnValue(true);
        parameter3.setPrimitiveType(false);
        parameter3.setCollection(false);
        parameter3.setCollectionDepth(0);
        parameter3.setModelId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setModelCode("ResponseInfo");
        parameter3.setModelName("ResponseInfo");
        parameter3.setModelType(ModelType.DTO);
        parameter3.setStructuredTypeRefId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setRefCode("ResponseInfo");
        parameter3.setRefName("ResponseInfo");
        parameter3.setLocation("");
        parameter3.setRequired(true);
        operation.getParameters().add(parameter3);

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

        Parameter parameter1 = new Parameter();
        parameter1.setId(
                MessageFormat.format("{0}&^^&BatchDeleteParam", vmMetadata.getHeader().getId()));
        parameter1.setCode("ids");
        parameter1.setName("单据内码列表");
        parameter1.setDescription("单据内码列表");
        parameter1.setReturnValue(false);
        parameter1.setPrimitiveType(true);
        parameter1.setCollection(false);
        parameter1.setCollectionDepth(0);
        parameter1.setModelId("string");
        parameter1.setModelName("string");
        parameter1.setLocation("Query");
        parameter1.setRequired(true);
        operation.getParameters().add(parameter1);

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

        Parameter parameter1 = new Parameter();
        parameter1.setId(MessageFormat.format("{0}&^^&DeleteParam", vmMetadata.getHeader().getId()));
        parameter1.setCode("dataId");
        parameter1.setName("单据内码");
        parameter1.setDescription("单据内码");
        parameter1.setReturnValue(false);
        parameter1.setPrimitiveType(true);
        parameter1.setCollection(false);
        parameter1.setCollectionDepth(0);
        parameter1.setModelId("string");
        parameter1.setModelName("string");
        parameter1.setLocation("Path");
        parameter1.setRequired(true);
        operation.getParameters().add(parameter1);

        return operation;
    }

    private static Operation edit(GspMetadata vmMetadata) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        Operation operation = new Operation();

        operation.setId(MessageFormat.format("{0}&^^&Edit", vmMetadata.getHeader().getId()));
        operation.setCode("Edit");
        operation.setName("编辑");
        operation.setDescription("编辑");
        operation.setResourceOperationId(MessageFormat.format("{0}&^^&Edit", vmMetadata.getHeader().getId()));
        operation.setHttpMethod("PUT");
        operation.setHttpPath("/service/edit/{dataId}");
        operation.setDeprecated(false);
        operation.setExtend1("Basic");

        operation.setParameters(new ArrayList<>());

        Parameter parameter1 = new Parameter();
        parameter1.setId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setCode("requestInfo");
        parameter1.setName("请求信息");
        parameter1.setDescription("请求信息");
        parameter1.setReturnValue(false);
        parameter1.setPrimitiveType(false);
        parameter1.setCollection(false);
        parameter1.setCollectionDepth(0);
        parameter1.setModelId(
                MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setModelCode("RequestInfo");
        parameter1.setModelName("RequestInfo");
        parameter1.setModelType(ModelType.DTO);
        parameter1.setStructuredTypeRefId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setRefCode("RequestInfo");
        parameter1.setRefName("RequestInfo");
        parameter1.setLocation("Body");
        parameter1.setRequired(false);
        operation.getParameters().add(parameter1);

        Parameter parameter2 = new Parameter();
        parameter2.setId(MessageFormat.format("{0}&^^&RetrieveParam", vmMetadata.getHeader().getId()));
        parameter2.setCode("dataId");
        parameter2.setName("单据内码");
        parameter2.setDescription("单据内码");
        parameter2.setReturnValue(false);
        parameter2.setPrimitiveType(true);
        parameter2.setCollection(false);
        parameter2.setCollectionDepth(0);
        parameter2.setModelId("string");
        parameter2.setModelName("string");
        parameter2.setLocation("Path");
        parameter2.setRequired(true);
        operation.getParameters().add(parameter2);

        Parameter parameter3 = new Parameter();
        parameter3.setId(MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setCode("responseInfo");
        parameter3.setName("响应信息");
        parameter3.setDescription("响应信息");
        parameter3.setReturnValue(true);
        parameter3.setPrimitiveType(false);
        parameter3.setCollection(false);
        parameter3.setCollectionDepth(0);
        parameter3.setModelId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setModelCode("ResponseInfo");
        parameter3.setModelName("ResponseInfo");
        parameter3.setModelType(ModelType.DTO);
        parameter3.setStructuredTypeRefId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setRefCode("ResponseInfo");
        parameter3.setRefName("ResponseInfo");
        parameter3.setLocation("");
        parameter3.setRequired(true);
        operation.getParameters().add(parameter3);

        return operation;
    }

    private static Operation create(GspMetadata vmMetadata) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        Operation operation = new Operation();

        operation.setId(MessageFormat.format("{0}&^^&Create", vmMetadata.getHeader().getId()));
        operation.setCode("Create");
        operation.setName("新增");
        operation.setResourceOperationId(
                MessageFormat.format("{0}&^^&Create", vmMetadata.getHeader().getId()));
        operation.setHttpMethod("POST");
        operation.setHttpPath("");
        operation.setDeprecated(false);
        operation.setExtend1("Basic");

        operation.setParameters(new ArrayList<>());

        Parameter parameter1 = new Parameter();
        parameter1.setId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setCode("requestInfo");
        parameter1.setName("请求信息");
        parameter1.setDescription("请求信息");
        parameter1.setReturnValue(false);
        parameter1.setPrimitiveType(false);
        parameter1.setCollection(false);
        parameter1.setCollectionDepth(0);
        parameter1.setModelId(
                MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setModelCode("RequestInfo");
        parameter1.setModelName("RequestInfo");
        parameter1.setModelType(ModelType.DTO);
        parameter1.setStructuredTypeRefId(MessageFormat.format("{0}&^^&RequestInfo", vmMetadata.getHeader().getId()));
        parameter1.setRefCode("RequestInfo");
        parameter1.setRefName("RequestInfo");
        parameter1.setLocation("Body");
        parameter1.setRequired(false);
        operation.getParameters().add(parameter1);

        Parameter parameter2 = new Parameter();
        parameter2.setId(MessageFormat.format("{0}&^^&defaultValue", vmMetadata.getHeader().getId()));
        parameter2.setCode("defaultValue");
        parameter2.setName("默认值");
        parameter2.setDescription("默认值");
        parameter2.setReturnValue(false);
        parameter2.setPrimitiveType(false);
        parameter2.setCollection(false);
        parameter2.setCollectionDepth(0);
        parameter2.setModelId(
                MessageFormat.format("{0}&^^&DefaultValue", vmMetadata.getHeader().getId()));
        parameter2.setModelCode("DefaultValue");
        parameter2.setModelName("DefaultValue");
        parameter2.setModelType(ModelType.DTO);
        parameter2.setStructuredTypeRefId(
                MessageFormat.format("{0}&^^&DefaultValue", vmMetadata.getHeader().getId()));
        parameter2.setRefCode("DefaultValue");
        parameter2.setRefName("DefaultValue");
        parameter2.setLocation("Body");
        parameter2.setRequired(false);
        operation.getParameters().add(parameter2);

        Parameter parameter3 = new Parameter();
        parameter3.setId(MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setCode("responseInfo");
        parameter3.setName("响应信息");
        parameter3.setDescription("响应信息");
        parameter3.setReturnValue(true);
        parameter3.setPrimitiveType(false);
        parameter3.setCollection(false);
        parameter3.setCollectionDepth(0);
        parameter3.setModelId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setModelCode("ResponseInfo");
        parameter3.setModelName("ResponseInfo");
        parameter3.setModelType(ModelType.DTO);
        parameter3.setStructuredTypeRefId(
                MessageFormat.format("{0}&^^&ResponseInfo", vmMetadata.getHeader().getId()));
        parameter3.setRefCode("ResponseInfo");
        parameter3.setRefName("ResponseInfo");
        parameter3.setLocation("");
        parameter3.setRequired(true);
        operation.getParameters().add(parameter3);

        return operation;
    }

    private static Operation closeSession(GspMetadata vmMetadata) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        Operation operation = new Operation();
        operation.setId(MessageFormat.format("{0}&^^&CloseSession", vmMetadata.getHeader().getId()));
        operation.setCode("CloseSession");
        operation.setName("关闭Session");
        operation.setResourceOperationId(
                MessageFormat.format("{0}&^^&CloseSession", vmMetadata.getHeader().getId()));
        operation.setHttpMethod("POST");
        operation.setHttpPath("service/closesession");
        operation.setDeprecated(false);
        operation.setExtend1("Basic");

        return operation;
    }

    private static Operation createSession(GspMetadata vmMetadata) {
        GspViewModel viewModel = (GspViewModel)vmMetadata.getContent();
        Operation operation = new Operation();
        operation.setId(MessageFormat.format("{0}&^^&CreateSession", vmMetadata.getHeader().getId()));
        operation.setCode("CreateSession");
        operation.setName("创建Session");
        operation.setResourceOperationId(
                MessageFormat.format("{0}&^^&CreateSession", vmMetadata.getHeader().getId()));
        operation.setHttpMethod("POST");
        operation.setHttpPath("service/createsession");
        operation.setDeprecated(false);
        operation.setExtend1("Basic");
        operation.setParameters(new ArrayList<>());

        Parameter parameter = new Parameter();
        parameter.setId(MessageFormat.format("{0}&^^&CreateSessionParam", vmMetadata.getHeader().getId()));
        parameter.setCode("sessionId");
        parameter.setName("sessionId");
        parameter.setDescription("功能sessionId");
        parameter.setReturnValue(true);
        parameter.setPrimitiveType(true);
        parameter.setCollection(false);
        parameter.setCollectionDepth(0);
        parameter.setModelId("string");
        parameter.setModelName("string");
        parameter.setLocation("");
        parameter.setRequired(true);
        operation.getParameters().add(parameter);
        return operation;
    }
}
