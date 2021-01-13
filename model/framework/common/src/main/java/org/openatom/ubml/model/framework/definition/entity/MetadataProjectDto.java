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

public class MetadataProjectDto {
    private String projectPath;
    private String projectName;
    private String projectNameSpace;
    private String packageName;
    private String depPackageName;
    private String depPackageVersion;
    private String depPackageLocation;

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectNameSpace() {
        return projectNameSpace;
    }

    public void setProjectNameSpace(String projectNameSpace) {
        this.projectNameSpace = projectNameSpace;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDepPackageName() {
        return depPackageName;
    }

    public void setDepPackageName(String depPackageName) {
        this.depPackageName = depPackageName;
    }

    public String getDepPackageVersion() {
        return depPackageVersion;
    }

    public void setDepPackageVersion(String depPackageVersion) {
        this.depPackageVersion = depPackageVersion;
    }

    public String getDepPackageLocation() {
        return depPackageLocation;
    }

    public void setDepPackageLocation(String depPackageLocation) {
        this.depPackageLocation = depPackageLocation;
    }
}
