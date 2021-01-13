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

package org.openatom.ubml.model.common.definition.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type MetadataHeader
 *
 * @author: Jack Lee
 */
public class MetadataHeader {
    /**
     * metadata id
     */
    @JsonProperty("ID")
    private String id;

    /**
     * certificate id
     */
    private String certId;

    /**
     * namespace
     */
    private String nameSpace;

    /**
     * code
     */
    private String code;

    /**
     * name
     */
    private String name;

    /**
     * file name in ide
     */
    private String fileName;

    /**
     * metadata type
     */
    private String type;

    /**
     * business object id
     */
    private String bizObjectId;

    /**
     * language
     */
    private String language;

    /**
     * Translated
     */
    private boolean isTranslating;

    /**
     * can be extend or not
     */
    private boolean extendable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
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

    public String getBizObjectId() {
        return bizObjectId;
    }

    public void setBizObjectId(String bizObjectId) {
        this.bizObjectId = bizObjectId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean getTranslating() {
        return isTranslating;
    }

    public void setTranslating(boolean translating) {
        isTranslating = translating;
    }

    public boolean isExtendable() {
        return extendable;
    }

    public void setExtendable(boolean extendable) {
        this.extendable = extendable;
    }

    /**
     * clone
     */
    @Override
    public final Object clone() {
        MetadataHeader header = new MetadataHeader();
        header.setId(this.getId());
        header.setBizObjectId(this.getBizObjectId());
        header.setType(this.getType());
        header.setCode(this.getCode());
        header.setName(this.getName());
        header.setNameSpace(this.getNameSpace());
        header.setFileName(this.getFileName());
        header.setLanguage(this.getLanguage());
        header.setTranslating(this.isTranslating);
        header.setExtendable(this.isExtendable());
        header.setCertId(this.getCertId());
        return header;
    }

}
