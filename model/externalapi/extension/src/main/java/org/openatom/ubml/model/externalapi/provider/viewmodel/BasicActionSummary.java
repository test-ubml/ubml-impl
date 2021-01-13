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
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.externalapi.definition.dto.Summary;
import org.openatom.ubml.model.externalapi.definition.temp.vo.GspViewModel;
import org.openatom.ubml.model.externalapi.definition.temp.vo.GspViewObject;
import org.openatom.ubml.model.externalapi.definition.temp.vo.IGspCommonObject;

/**
 * BasicActionSummary
 *
 * @Author: Fynn Qi
 * @Date: 2020/8/26 11:33
 * @Version: V1.0
 */
public class BasicActionSummary {
    public static List<Summary> getBasicActions(GspMetadata gspMetadata) {
        List<Summary> operations = new ArrayList<>();
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

    private static List<Summary> getExtendDeleteChildOperationList(GspMetadata vmMetadata) {
        List<Summary> operations = new ArrayList<>();
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
            List<Summary> operations,
            String nodesCodesStringBuidler,
            GspMetadata vmMetadata) {
        nodesCodesStringBuidler =
                StringUtils.isBlank(nodesCodesStringBuidler)
                        ? viewObject.getCode()
                        : nodesCodesStringBuidler + "#" + viewObject.getCode();
        Summary operation =
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

    private static Summary getExtendDeleteChildOperation(
            GspMetadata vmMetadata, String currentNodeCode, String nodeCodesStringBuilder) {
        String[] nodeCodes = nodeCodesStringBuilder.split("#");
        if (nodeCodes.length <= 0) {
            return null;
        }
        Summary operation = new Summary();
        String methodCode = "Extend_DeleteChild";
        String methodId = "Extend_DeleteChild";
        for (String nodeCode : nodeCodes) {
            methodCode = methodCode + String.format("_%s", nodeCode);
            methodId = methodId + MessageFormat.format("##{0}", nodeCode);
        }
        operation.setId(MessageFormat.format("{0}&^^&{0}", vmMetadata.getHeader().getId(), methodId));
        operation.setCode(methodCode);
        operation.setName(MessageFormat.format("扩展删除从表{0}数据", currentNodeCode));
        return operation;
    }

    private static Summary extendElementHelp(GspMetadata vmMetadata) {
        Summary operation = new Summary();
        operation.setId(
                MessageFormat.format("{0}&^^&Extend_ElementHelp", vmMetadata.getHeader().getId()));
        operation.setCode("Extend_GetElementHelp");
        operation.setName("扩展获取字段帮助");
        return operation;
    }

    private static Summary extensionFilter(GspMetadata vmMetadata) {
        Summary operation = new Summary();
        operation.setId(
                MessageFormat.format("{0}&^^&Extension_Filter", vmMetadata.getHeader().getId()));
        operation.setCode("Extension_Filter");
        operation.setName("查询数据");
        return operation;
    }

    private static Summary extendQuery(GspMetadata vmMetadata) {
        Summary operation = new Summary();
        operation.setId(MessageFormat.format("{0}&^^&Extend_Query", vmMetadata.getHeader().getId()));
        operation.setCode("Extend_Query");
        operation.setName("扩展过滤查询");
        return operation;
    }

    private static Summary extendRetrieve(GspMetadata vmMetadata) {
        Summary operation = new Summary();
        operation.setId(MessageFormat.format("{0}&^^&Extend_Retrieve", vmMetadata.getHeader().getId()));
        operation.setCode("Extend_Retrieve");
        operation.setName("扩展数据检索");
        return operation;
    }

    private static Summary extendBatchDelete(GspMetadata vmMetadata) {
        Summary operation = new Summary();
        operation.setId(
                MessageFormat.format("{0}&^^&Extend_BatchDelete", vmMetadata.getHeader().getId()));
        operation.setCode("Extend_BatchDelete");
        operation.setName("扩展批量删除");
        return operation;
    }

    private static Summary extendDelete(GspMetadata vmMetadata) {
        Summary operation = new Summary();
        operation.setId(MessageFormat.format("{0}&^^&Extend_Delete", vmMetadata.getHeader().getId()));
        operation.setCode("Extend_Delete");
        operation.setName("扩展删除");
        return operation;
    }

    private static List<Summary> getDeleteChildOperationList(GspMetadata vmMetadata) {
        List<Summary> operations = new ArrayList<>();
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
            List<Summary> operations,
            String nodesCodesStringBuidler,
            GspMetadata vmMetadata) {
        nodesCodesStringBuidler =
                StringUtils.isBlank(nodesCodesStringBuidler)
                        ? viewObject.getCode()
                        : nodesCodesStringBuidler + "#" + viewObject.getCode();
        Summary operation =
                getDeleteChildOperation(vmMetadata, viewObject.getCode(), nodesCodesStringBuidler);
        if (operation != null) {
            operations.add(operation);
        }
        if (viewObject.getContainChildObjects().size() > 0) {
            for (IGspCommonObject obj : viewObject.getContainChildObjects()) {
                getDeleteChildActions((GspViewObject)obj, operations, nodesCodesStringBuidler, vmMetadata);
            }
        }
    }

    private static Summary getDeleteChildOperation(
            GspMetadata vmMetadata, String currentNodeCode, String nodeCodesStringBuilder) {
        String[] nodeCodes = nodeCodesStringBuilder.split("#");
        if (nodeCodes.length <= 0) {
            return null;
        }
        Summary operation = new Summary();
        String methodCode = "DeleteChild";
        String methodId = "DeleteChild";
        for (String nodeCode : nodeCodes) {
            methodCode = methodCode + String.format("_%s", nodeCode);
            methodId = methodId + MessageFormat.format("##{0}", nodeCode);
        }
        operation.setId(MessageFormat.format("{0}&^^&{1}", vmMetadata.getHeader().getId(), methodId));
        operation.setCode(methodCode);
        operation.setName(MessageFormat.format("删除从表{0}数据", currentNodeCode));
        return operation;
    }

    private static List<Summary> getCreateChildOperationList(GspMetadata vmMetadata) {
        List<Summary> operations = new ArrayList<>();
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
            List<Summary> operations,
            String nodesCodesStringBuidler,
            GspMetadata vmMetadata) {
        nodesCodesStringBuidler =
                StringUtils.isBlank(nodesCodesStringBuidler)
                        ? viewObject.getCode()
                        : nodesCodesStringBuidler + "#" + viewObject.getCode();
        Summary operation =
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

    private static Summary getCreateChildOperation(
            GspMetadata vmMetadata, String currentNodeCode, String nodeCodesStringBuilder) {
        String[] nodeCodes = nodeCodesStringBuilder.split("#");
        if (nodeCodes.length <= 0) {
            return null;
        }
        Summary operation = new Summary();
        String methodCode = "CreateChild";
        String methodId = "CreateChild";
        for (String nodeCode : nodeCodes) {
            methodCode = methodCode + String.format("_%s", nodeCode);
            methodId = methodId + MessageFormat.format("##{0}", nodeCode);
        }
        operation.setId(MessageFormat.format("{0}&^^&{1}", vmMetadata.getHeader().getId(), methodId));
        operation.setCode(methodCode);
        operation.setName(MessageFormat.format("新增从表{0}数据", currentNodeCode));
        return operation;
    }

    private static Summary getElementHelp(GspMetadata vmMetadata) {
        Summary operation = new Summary();
        operation.setId(MessageFormat.format("{0}&^^&ElementHelp", vmMetadata.getHeader().getId()));
        operation.setCode("GetElementHelp");
        operation.setName("获取字段帮助");
        return operation;
    }

    private static Summary cancel(GspMetadata vmMetadata) {
        Summary operation = new Summary();
        operation.setId(MessageFormat.format("{0}&^^&Cancel", vmMetadata.getHeader().getId()));
        operation.setCode("Cancel");
        operation.setName("取消");
        return operation;
    }

    private static Summary save(GspMetadata vmMetadata) {
        Summary operation = new Summary();
        operation.setId(MessageFormat.format("{0}&^^&Save", vmMetadata.getHeader().getId()));
        operation.setCode("Save");
        operation.setName("保存");
        return operation;
    }

    private static Summary query(GspMetadata vmMetadata) {
        Summary operation = new Summary();
        operation.setId(MessageFormat.format("{0}&^^&Query", vmMetadata.getHeader().getId()));
        operation.setCode("Query");
        operation.setName("过滤查询");
        return operation;
    }

    private static Summary retrieveChildByIndex(GspMetadata vmMetadata) {
        Summary operation = new Summary();
        operation.setId(MessageFormat.format("{0}&^^&QueryChild", vmMetadata.getHeader().getId()));
        operation.setCode("QueryChild");
        operation.setName("子表查询");
        return operation;
    }

    private static Summary retrieveWithChildPagination(GspMetadata vmMetadata) {
        Summary operation = new Summary();
        operation.setId(
                MessageFormat.format("{0}&^^&RetrieveWithChildPagination", vmMetadata.getHeader().getId()));
        operation.setCode("RetrieveWithChildPagination");
        operation.setName("数据检索");
        return operation;
    }

    private static Summary retrieve(GspMetadata vmMetadata) {
        Summary operation = new Summary();
        operation.setId(MessageFormat.format("{0}&^^&Retrieve", vmMetadata.getHeader().getId()));
        operation.setCode("Retrieve");
        operation.setName("数据检索");
        return operation;
    }

    private static Summary update(GspMetadata vmMetadata) {
        Summary operation = new Summary();
        operation.setId(MessageFormat.format("{0}&^^&Update", vmMetadata.getHeader().getId()));
        operation.setCode("Update");
        operation.setName("修改");
        return operation;
    }

    private static Summary deleteAndSave(GspMetadata vmMetadata) {
        Summary operation = new Summary();
        operation.setId(MessageFormat.format("{0}&^^&DeleteAndSave", vmMetadata.getHeader().getId()));
        operation.setCode("DeleteAndSave");
        operation.setName("删除并保存");
        return operation;
    }

    private static Summary batchDelete(GspMetadata vmMetadata) {
        Summary operation = new Summary();
        operation.setId(MessageFormat.format("{0}&^^&BatchDelete", vmMetadata.getHeader().getId()));
        operation.setCode("BatchDelete");
        operation.setName("批量删除");
        return operation;
    }

    private static Summary delete(GspMetadata vmMetadata) {
        Summary operation = new Summary();
        operation.setId(MessageFormat.format("{0}&^^&Delete", vmMetadata.getHeader().getId()));
        operation.setCode("Delete");
        operation.setName("删除");
        return operation;
    }

    private static Summary edit(GspMetadata vmMetadata) {
        Summary operation = new Summary();
        operation.setId(MessageFormat.format("{0}&^^&Edit", vmMetadata.getHeader().getId()));
        operation.setCode("Edit");
        operation.setName("编辑");
        return operation;
    }

    private static Summary create(GspMetadata vmMetadata) {
        Summary operation = new Summary();
        operation.setId(MessageFormat.format("{0}&^^&Create", vmMetadata.getHeader().getId()));
        operation.setCode("Create");
        operation.setName("新增");
        return operation;
    }

    private static Summary closeSession(GspMetadata vmMetadata) {
        Summary operation = new Summary();
        operation.setId(MessageFormat.format("{0}&^^&CloseSession", vmMetadata.getHeader().getId()));
        operation.setCode("CloseSession");
        operation.setName("关闭Session");
        return operation;
    }

    private static Summary createSession(GspMetadata vmMetadata) {
        Summary operation = new Summary();
        operation.setId(MessageFormat.format("{0}&^^&CreateSession", vmMetadata.getHeader().getId()));
        operation.setCode("CreateSession");
        operation.setName("创建Session");
        return operation;
    }
}
