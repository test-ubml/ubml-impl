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

package org.openatom.ubml.model.external.generator;

import java.text.MessageFormat;
import java.util.Objects;
import org.openatom.ubml.common.util.StringUtils;
import org.openatom.ubml.model.externalapi.definition.entity.ExternalApi;
import org.openatom.ubml.model.externalapi.definition.entity.Operation;

/**
 * EapiChecker
 *
 * @Author: Fynn Qi
 * @Date: 2020/9/17 18:18
 * @Version: V1.0
 */
public class ExternalApiChecker {

    public static void check(ExternalApi metadata) {
        if (metadata == null) {
            throw new RuntimeException("外部服务元数据代码生成校验：外部服务元数据为空，请检查入参");
        }
        if (metadata.getService() == null) {
            throw new RuntimeException(MessageFormat.format("元数据{0}的服务定义不能为空", metadata.getCode()));
        }
        if (StringUtils.isBlank(metadata.getApplication())) {
            throw new RuntimeException(MessageFormat.format("元数据{0}的关键应用不能为空", metadata.getCode()));
        }
        if (StringUtils.isBlank(metadata.getMicroServiceUnit())) {
            throw new RuntimeException(MessageFormat.format("元数据{0}的服务单元不能为空", metadata.getCode()));
        }
        if (StringUtils.isBlank(metadata.getVersion())) {
            throw new RuntimeException(MessageFormat.format("元数据{0}的版本信息不能为空", metadata.getCode()));
        }

        if (StringUtils.isBlank(metadata.getService().getResourceType())) {
            throw new RuntimeException(MessageFormat.format("元数据{0}的资源类型不能为空", metadata.getCode()));
        }

        if (StringUtils.isBlank(metadata.getService().getResourceId())) {
            throw new RuntimeException(MessageFormat.format("元数据{0}的资源ID不能为空", metadata.getCode()));
        }

        if (Objects.nonNull(metadata.getService().getOperations())) {
            for (Operation operation : metadata.getService().getOperations()) {
                if (StringUtils.isBlank(operation.getHttpMethod())) {
                    throw new RuntimeException(
                            MessageFormat.format(
                                    "元数据{0}中服务操作{1}的HTTP方法不能为空", metadata.getCode(), operation.getCode()));
                }
            }
        }
    }
}
