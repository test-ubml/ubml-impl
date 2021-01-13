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

package org.openatom.ubml.model.framework.definition.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.openatom.ubml.model.framework.definition.entity.mvnEntity.MavenPackageRefs;

/**
 * 元数据工程实体，对应.mdproj文件
 */
public class MetadataProject {
    /**
     * 工程ID，生成工程时的唯一标识，元数据包如果有ID获取该ID
     */
    @JsonProperty("ID")
    private String id;

    /**
     * 元数据工程命名空间
     */
    private String nameSpace;

    /**
     * 元数据工程名称
     */
    private String name;

    /**
     * 元数据工程文件名称
     */
    private String projectFileName;

    /**
     * 元数据工程路径，元数据设计器上选择元数据需要知道工程路径信息
     */
    private String projectPath;

    /**
     * 元数据工程生成的元数据包信息
     */
    private MetadataPackageHeader metadataPackageInfo;

    /**
     * 当前工程下的元数据所依赖的其他元数据包的信息（设计时依赖）
     */
    private List<MetadataPackageHeader> metadataPackageRefs;

    private String csprojAssemblyName;

    /**
     * Project依赖
     */
    private List<ProjectHeader> projectRefs;

    /**
     * Maven依赖
     */
    private List<MavenPackageRefs> mavenPackageRefs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectFileName() {
        return projectFileName;
    }

    public void setProjectFileName(String projectFileName) {
        this.projectFileName = projectFileName;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public MetadataPackageHeader getMetadataPackageInfo() {
        return metadataPackageInfo;
    }

    public void setMetadataPackageInfo(MetadataPackageHeader metadataPackageInfo) {
        this.metadataPackageInfo = metadataPackageInfo;
    }

    public List<MetadataPackageHeader> getMetadataPackageRefs() {
        return metadataPackageRefs;
    }

    public void setMetadataPackageRefs(List<MetadataPackageHeader> metadataPackageRefs) {
        this.metadataPackageRefs = metadataPackageRefs;
    }

    public String getCsprojAssemblyName() {
        return csprojAssemblyName;
    }

    public void setCsprojAssemblyName(String csprojAssemblyName) {
        this.csprojAssemblyName = csprojAssemblyName;
    }

    public List<ProjectHeader> getProjectRefs() {
        return projectRefs;
    }

    public void setProjectRefs(List<ProjectHeader> projectRefs) {
        this.projectRefs = projectRefs;
    }

    public List<MavenPackageRefs> getMavenPackageRefs() {
        return mavenPackageRefs;
    }

    public void setMavenPackageRefs(List<MavenPackageRefs> mavenPackageRefs) {
        this.mavenPackageRefs = mavenPackageRefs;
    }

}