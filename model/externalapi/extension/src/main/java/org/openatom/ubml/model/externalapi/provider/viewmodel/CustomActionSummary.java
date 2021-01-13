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
import org.openatom.ubml.model.externalapi.definition.temp.vo.GspViewModel;
import org.openatom.ubml.model.externalapi.definition.temp.vo.ViewModelAction;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.externalapi.definition.dto.Summary;

/**
 * CustomActionSummary
 *
 * @Author: Fynn Qi
 * @Date: 2020/8/26 14:03
 * @Version: V1.0
 */
public class CustomActionSummary {

    public static List<Summary> getCustomActions(GspMetadata gspMetadata) {
        List<Summary> operations = new ArrayList<>();
        GspViewModel viewModel = (GspViewModel)gspMetadata.getContent();
        List<ViewModelAction> actions = viewModel.getActions();
        for (ViewModelAction action : actions) {
            long count =
                    BasicActionSummary.getBasicActions(gspMetadata).stream()
                            .filter(x -> x.getCode().equals(action.getCode()))
                            .count();
            if (count > 0) {
                throw new RuntimeException(String.format("操作编号%s为预置的基本操作编号，请修改%s[ID:%s]的自定义动作编号", action.getCode(), viewModel.getCode(), viewModel.getID()));
            }
            Summary operation = new Summary();
            operation.setId(MessageFormat.format("{0}&^^&{1}", action.getID(), action.getCode()));
            operation.setCode(action.getCode());
            operation.setName(action.getName());
            operations.add(operation);
        }
        return operations;
    }
}
