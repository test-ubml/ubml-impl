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

public class ExtractContext {

    private String projectPath;
    private String deployPath;
    private String deployConfigPath;
    private String webModulePath;

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getDeployPath() {
        return deployPath;
    }

    public void setDeployPath(String deployPath) {
        this.deployPath = deployPath;
    }

    public String getDeployConfigPath() {
        return deployConfigPath;
    }

    public void setDeployConfigPath(String deployConfigPath) {
        this.deployConfigPath = deployConfigPath;
    }

    public String getWebModulePath() {
        return webModulePath;
    }

    public void setWebModulePath(String webModulePath) {
        this.webModulePath = webModulePath;
    }
}
