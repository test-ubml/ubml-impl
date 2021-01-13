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

package org.openatom.ubml.model.externalapi.provider.viewmodel.utils;

import java.util.ArrayList;
import java.util.List;
import org.openatom.ubml.model.externalapi.provider.viewmodel.BasicActionProvider;
import org.openatom.ubml.model.externalapi.provider.viewmodel.BasicActionSummary;
import org.openatom.ubml.model.externalapi.provider.viewmodel.CustomActionProvider;
import org.openatom.ubml.model.externalapi.provider.viewmodel.CustomActionSummary;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.externalapi.definition.dto.Summary;
import org.openatom.ubml.model.externalapi.definition.entity.Operation;

/**
 * 功能描述:
 *
 * @ClassName: ViewModelUtils
 * @Author: Fynn Qi
 * @Date: 2020/9/1 9:59
 * @Version: V1.0
 */
public class ViewModelUtils {

    public static List<Operation> getAllOperations(GspMetadata metadata) {
        List<Operation> operations = new ArrayList<>();
        operations.addAll(BasicActionProvider.getBasicActions(metadata));
        operations.addAll(CustomActionProvider.getCustomActions(metadata));
        return operations;
    }

    public static List<Operation> getAllBasicOperations(GspMetadata metadata) {
        return BasicActionProvider.getBasicActions(metadata);
    }

    public static List<Operation> getAllCustomOperations(GspMetadata metadata) {
        return CustomActionProvider.getCustomActions(metadata);
    }

    public static List<Summary> getAllSummaryOperations(GspMetadata metadata) {
        List<Summary> operations = new ArrayList<>();
        operations.addAll(BasicActionSummary.getBasicActions(metadata));
        operations.addAll(CustomActionSummary.getCustomActions(metadata));
        return operations;
    }

    public static List<Summary> getAllBasicSummaryOperations(GspMetadata metadata) {
        return BasicActionSummary.getBasicActions(metadata);
    }

    public static List<Summary> getAllCustomSummaryOperations(GspMetadata metadata) {
        return CustomActionSummary.getCustomActions(metadata);
    }
}
