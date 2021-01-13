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

import java.util.ArrayList;
import java.util.List;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.externalapi.definition.dto.Summary;

/**
 * 功能描述:
 *
 * @ClassName: SimpleBasicActionProvider
 * @Author: Fynn Qi
 * @Date: 2020/7/31 10:42
 * @Version: V1.0
 */
public class SimpleBasicActionSummary {

    public static List<Summary> getSimpleBasicActions(GspMetadata gspMetadata) {
        List<Summary> operations = new ArrayList<>();
        String voId = gspMetadata.getHeader().getId();
        //增加
        operations.add(create(voId));
        //删除
        operations.add(delete(voId));
        //批量删除
        operations.add(batchDelete(voId));
        //保存
        operations.add(update(voId));
        //查询
        operations.add(retrieve(voId));
        //过滤查询
        operations.add(query(voId));
        //新增默认值
        operations.add(createDefaultValue(voId));
        return operations;
    }

    private static Summary query(String voId) {
        Summary operation = new Summary();
        operation.setId(getOperationId(voId, "Query"));
        operation.setCode("Query");
        operation.setName("过滤查询");
        return operation;
    }

    private static Summary retrieve(String voId) {
        Summary operation = new Summary();
        operation.setId(getOperationId(voId, "Retrieve"));
        operation.setCode("Retrieve");
        operation.setName("数据检索");
        return operation;
    }

    private static Summary update(String voId) {
        Summary operation = new Summary();
        operation.setId(getOperationId(voId, "Update"));
        operation.setCode("Update");
        operation.setName("更新");
        return operation;
    }

    private static Summary batchDelete(String voId) {
        Summary operation = new Summary();
        operation.setId(getOperationId(voId, "BatchDelete"));
        operation.setCode("BatchDelete");
        operation.setName("批量删除");
        return operation;
    }

    private static Summary delete(String voId) {
        Summary operation = new Summary();
        operation.setId(getOperationId(voId, "Delete"));
        operation.setCode("Delete");
        operation.setName("删除");
        return operation;
    }

    private static Summary create(String voId) {
        Summary operation = new Summary();
        operation.setId(getOperationId(voId, "Create"));
        operation.setCode("Create");
        operation.setName("新增");
        return operation;
    }

    private static Summary createDefaultValue(String voId) {
        Summary operation = new Summary();
        operation.setId(getOperationId(voId, "CreateDefaultValue"));
        operation.setCode("CreateDefaultValue");
        operation.setName("新增");
        return operation;
    }

    private static String getOperationId(String voId, String code) {
        return String.format("%s&^^&%s", voId, code);
    }

}
