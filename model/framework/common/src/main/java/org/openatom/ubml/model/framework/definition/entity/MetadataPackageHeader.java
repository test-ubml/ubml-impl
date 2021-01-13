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

/**
 * 元数据包的头节点，主要包含元数据包的基本描述信息
 */
public class MetadataPackageHeader {
    /**
     * 元数据包名称
     */
    private String name;

    /**
     * 元数据包版本
     */
    private MetadataPackageVersion version;

    /**
     * 元数据包路径
     */
    private String location;

    private ProcessMode processMode;

    public MetadataPackageHeader() {
        setProcessMode(ProcessMode.generation);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MetadataPackageVersion getVersion() {
        return version;
    }

    public void setVersion(MetadataPackageVersion version) {
        this.version = version;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ProcessMode getProcessMode() {
        return processMode;
    }

    public void setProcessMode(ProcessMode processMode) {
        this.processMode = processMode;
    }

}