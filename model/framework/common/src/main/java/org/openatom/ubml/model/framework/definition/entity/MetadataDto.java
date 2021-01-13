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

import org.openatom.ubml.model.common.definition.entity.MetadataProperties;

/**
 * Classname MetadataDto Description 通过WebApi调用时前端往后端传递元数据信息所用实体 Date 2019/11/14 19:54
 *
 * @author zhongchq
 * @version 1.0
 */
public class MetadataDto {

    public String id;
    public String nameSpace;
    public String code;
    public String name;
    public String fileName;
    public String type;
    public String bizobjectID;
    public String language;
    public boolean isTranslating;
    public String relativePath;
    public String extendProperty;
    public String content;
    public boolean extendable;
    public String refs;
    public boolean extented;
    public String previousVersion;
    public String version;
    public MetadataProperties properties;
    public String extendRule;
    public String projectName;
    public String processMode;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBizobjectID() {
        return bizobjectID;
    }

    public void setBizobjectID(String bizobjectID) {
        this.bizobjectID = bizobjectID;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isTranslating() {
        return isTranslating;
    }

    public void setTranslating(boolean translating) {
        isTranslating = translating;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getExtendProperty() {
        return extendProperty;
    }

    public void setExtendProperty(String extendProperty) {
        this.extendProperty = extendProperty;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isExtendable() {
        return extendable;
    }

    public void setExtendable(boolean extendable) {
        this.extendable = extendable;
    }

    public String getRefs() {
        return refs;
    }

    public void setRefs(String refs) {
        this.refs = refs;
    }

    public boolean isExtented() {
        return extented;
    }

    public void setExtented(boolean extented) {
        this.extented = extented;
    }

    public String getPreviousVersion() {
        return previousVersion;
    }

    public void setPreviousVersion(String previousVersion) {
        this.previousVersion = previousVersion;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public MetadataProperties getProperties() {
        return properties;
    }

    public void setProperties(MetadataProperties properties) {
        this.properties = properties;
    }

    public String getExtendRule() {
        return extendRule;
    }

    public void setExtendRule(String extendRule) {
        this.extendRule = extendRule;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProcessMode() {
        return processMode;
    }

    public void setProcessMode(String processMode) {
        this.processMode = processMode;
    }

}
