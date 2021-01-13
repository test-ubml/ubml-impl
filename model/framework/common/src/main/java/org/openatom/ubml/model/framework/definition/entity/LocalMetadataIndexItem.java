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
 * Classname LocalMetadataIndexItem Description 本地元数据索引 Date 2019/11/26 15:33
 *
 * @author zhongchq
 * @version 1.0
 */
public class LocalMetadataIndexItem {

    private String id;
    private String code;
    private String name;
    private String fileName;
    private String nameSpace;
    private String mdPkgName;
    private String mdPkgLocation;
    private String relativePath;
    private String type;
    private String processMode;
    private LocalRepoPkg repoPkg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getMdPkgName() {
        return mdPkgName;
    }

    public void setMdPkgName(String mdPkgName) {
        this.mdPkgName = mdPkgName;
    }

    public String getMdPkgLocation() {
        return mdPkgLocation;
    }

    public void setMdPkgLocation(String mdPkgLocation) {
        this.mdPkgLocation = mdPkgLocation;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProcessMode() {
        return processMode;
    }

    public void setProcessMode(String processMode) {
        this.processMode = processMode;
    }

    public LocalRepoPkg getRepoPkg() {
        return repoPkg;
    }

    public void setRepoPkg(LocalRepoPkg repoPkg) {
        this.repoPkg = repoPkg;
    }

}
