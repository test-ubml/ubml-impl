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
import org.openatom.ubml.model.externalapi.provider.viewmodel.SimpleBasicActionProvider;
import org.openatom.ubml.model.externalapi.provider.viewmodel.SimpleBasicActionSummary;
import org.openatom.ubml.model.externalapi.provider.viewmodel.SimpleCustomActionProvider;
import org.openatom.ubml.model.externalapi.provider.viewmodel.SimpleCustomActionSummary;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.externalapi.definition.dto.Summary;
import org.openatom.ubml.model.externalapi.definition.entity.Operation;

/**
 * SimpleViewModelUtils
 *
 * @Author: Fynn Qi
 * @Date: 2020/9/1 10:59
 * @Version: V1.0
 */
public class SimpleViewModelUtils {

    public static List<Operation> getAllOperations(GspMetadata metadata) {
        List<Operation> operations = new ArrayList<>();
        operations.addAll(SimpleBasicActionProvider.getSimpleBasicActions(metadata));
        operations.addAll(SimpleCustomActionProvider.getSimpleCustomActions(metadata));
        return operations;
    }

    public static List<Operation> getAllBasicOperations(GspMetadata metadata) {
        return SimpleBasicActionProvider.getSimpleBasicActions(metadata);
    }

    public static List<Operation> getAllCustomOperations(GspMetadata metadata) {
        return SimpleCustomActionProvider.getSimpleCustomActions(metadata);
    }

    public static List<Summary> getAllSummaryOperations(GspMetadata metadata) {
        List<Summary> operations = new ArrayList<>();
        operations.addAll(SimpleBasicActionSummary.getSimpleBasicActions(metadata));
        operations.addAll(SimpleCustomActionSummary.getSimpleCustomActions(metadata));
        return operations;
    }

    public static List<Summary> getAllBasicSummaryOperations(GspMetadata metadata) {
        return SimpleBasicActionSummary.getSimpleBasicActions(metadata);
    }

    public static List<Summary> getAllCustomSummaryOperations(GspMetadata metadata) {
        return SimpleCustomActionSummary.getSimpleCustomActions(metadata);
    }

}
