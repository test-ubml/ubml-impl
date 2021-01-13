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

package org.openatom.ubml.model.externalapi.devtime.core.provider;

import java.io.IOException;
import org.openatom.ubml.common.spring.SpringUtils;
import org.openatom.ubml.common.util.StringUtils;
import org.openatom.ubml.metamodel.framework.designtime.api.GspProjectService;
import org.openatom.ubml.metamodel.framework.designtime.api.MetadataProjectService;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.externalapi.definition.temp.lcm.RefCommonService;
import org.openatom.ubml.model.framework.definition.entity.GspProject;
import org.openatom.ubml.model.framework.definition.entity.MetadataProject;

/**
 * MetadataUtils
 *
 * @Author: Fynn Qi
 * @Date: 2020/9/1 14:15
 * @Version: V1.0
 */
public class MetadataUtils {

    public static GspMetadata getDtVoMetadata(String resourceId) {
        if (StringUtils.isBlank(resourceId)) {
            throw new RuntimeException("外部服务获取解析的资源ID为空");
        }
        RefCommonService service = SpringUtils.getBean(RefCommonService.class);
        return service.getRefMetadata(resourceId);
    }

    public static MetadataProject getMetadataProject(String path) {
        MetadataProjectService service = SpringUtils.getBean(MetadataProjectService.class);
        if (service == null) {
            throw new RuntimeException("获取MetadataProjectService Bean为空");
        }
        MetadataProject projectInfo = service.getMetadataProjInfo(path);
        if (projectInfo == null) {
            throw new RuntimeException(String.format("根据相对路径【%s】获取元数据工程信息为空！", path));
        }
        return projectInfo;
    }

    public static GspProject getProjectInfo(GspMetadata metadata) {
        if (StringUtils.isBlank(metadata.getRelativePath())) {
            throw new RuntimeException(
                    String.format(
                            "元数据【%s(ID:%s)】的相对路径为空，无法获取工程信息！",
                            metadata.getHeader().getCode(), metadata.getHeader().getId()));
        }
        GspProjectService service = SpringUtils.getBean(GspProjectService.class);
        if (service == null) {
            throw new RuntimeException("获取MetadataProjectService Bean为空");
        }
        GspProject projectInfo = null;
        try {
            projectInfo = service.getGspProjectInfo(metadata.getRelativePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (projectInfo == null) {
            throw new RuntimeException(
                    String.format(
                            "元数据【%s(ID:%s)】根据相对路径【%s】获取工程信息为空！",
                            metadata.getHeader().getCode(),
                            metadata.getHeader().getId(),
                            metadata.getRelativePath()));
        }
        return projectInfo;
    }


}
