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

import java.util.List;
import java.util.Map;

/**
 * GSP工程实体
 */
public class GspProject {

    /**
     * 关键应用编号
     */
    private String appCode;

    /**
     * 服务单元编号
     */
    private String serviceUnitCode;

    /**
     * 业务对象编号
     */
    private String bizobjectCode;

    /**
     * 业务对象ID
     */
    private String bizobjectID;

    /**
     * 工程命名空间
     */
    private String projectNameSpace;

    /**
     * 服务单元的部署路径
     */
    private String suDeploymentPath;

    /**
     * 元数据工程名称
     */
    private String metadataProjectName;

    /**
     * 元数据包名
     */
    private String metadataPackageName;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getServiceUnitCode() {
        return serviceUnitCode;
    }

    public void setServiceUnitCode(String serviceUnitCode) {
        this.serviceUnitCode = serviceUnitCode;
    }

    public String getBizobjectCode() {
        return bizobjectCode;
    }

    public void setBizobjectCode(String bizobjectCode) {
        this.bizobjectCode = bizobjectCode;
    }

    public String getBizobjectID() {
        return bizobjectID;
    }

    public void setBizobjectID(String bizobjectID) {
        this.bizobjectID = bizobjectID;
    }

    public String getProjectNameSpace() {
        return projectNameSpace;
    }

    public void setProjectNameSpace(String projectNameSpace) {
        this.projectNameSpace = projectNameSpace;
    }

    public String getSuDeploymentPath() {
        return suDeploymentPath;
    }

    public void setSuDeploymentPath(String suDeploymentPath) {
        this.suDeploymentPath = suDeploymentPath;
    }

    public String getMetadataProjectName() {
        return metadataProjectName;
    }

    public void setMetadataProjectName(String metadataProjectName) {
        this.metadataProjectName = metadataProjectName;
    }

    public String getMetadataPackageName() {
        return metadataPackageName;
    }

    public void setMetadataPackageName(String metadataPackageName) {
        this.metadataPackageName = metadataPackageName;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public List<Map<String, String>> getProjectExtendItem() {
        return projectExtendItem;
    }

    public void setProjectExtendItem(List<Map<String, String>> projectExtendItem) {
        this.projectExtendItem = projectExtendItem;
    }

    /**
     * 配置文件部署路径
     */
    private String suConfigDeploymentPath;

    /**
     * 工程类型
     */
    private String projectType;

    /**
     * 工程扩展项
     */
    private List<Map<String, String>> projectExtendItem;

    public String getSuConfigDeploymentPath() {
        return suConfigDeploymentPath;
    }

    public void setSuConfigDeploymentPath(String suConfigDeploymentPath) {
        this.suConfigDeploymentPath = suConfigDeploymentPath;
    }
}
