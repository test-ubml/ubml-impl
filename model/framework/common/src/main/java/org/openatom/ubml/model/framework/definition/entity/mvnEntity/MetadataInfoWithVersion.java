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

package org.openatom.ubml.model.framework.definition.entity.mvnEntity;

import java.util.List;

/**
 * 用于返回元数据索引信息
 */
public class MetadataInfoWithVersion {

    /**
     * key值由 namespace，code，type组成
     */
    private String key;
    private String metadataCode;
    private String metadataName;
    private String metadataNamespace;
    private String metadataType;
    private String mdBizobjectId;
    private String metadataLanguage;
    private String metadataIsTranslating;

    /**
     * 认为metadataId与 namespace，code，type的key值等价，
     */
    private String metadataId;
    private String packageCode;
    private String packageVersion;
    List<MetadataMavenInfo> mavenInfos;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMetadataCode() {
        return metadataCode;
    }

    public void setMetadataCode(String metadataCode) {
        this.metadataCode = metadataCode;
    }

    public String getMetadataName() {
        return metadataName;
    }

    public void setMetadataName(String metadataName) {
        this.metadataName = metadataName;
    }

    public String getMetadataNamespace() {
        return metadataNamespace;
    }

    public void setMetadataNamespace(String metadataNamespace) {
        this.metadataNamespace = metadataNamespace;
    }

    public String getMetadataType() {
        return metadataType;
    }

    public void setMetadataType(String metadataType) {
        this.metadataType = metadataType;
    }

    public String getMdBizobjectId() {
        return mdBizobjectId;
    }

    public void setMdBizobjectId(String mdBizobjectId) {
        this.mdBizobjectId = mdBizobjectId;
    }

    public String getMetadataLanguage() {
        return metadataLanguage;
    }

    public void setMetadataLanguage(String metadataLanguage) {
        this.metadataLanguage = metadataLanguage;
    }

    public String getMetadataIsTranslating() {
        return metadataIsTranslating;
    }

    public void setMetadataIsTranslating(String metadataIsTranslating) {
        this.metadataIsTranslating = metadataIsTranslating;
    }

    public String getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(String metadataId) {
        this.metadataId = metadataId;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public String getPackageVersion() {
        return packageVersion;
    }

    public void setPackageVersion(String packageVersion) {
        this.packageVersion = packageVersion;
    }

    public List<MetadataMavenInfo> getMavenInfos() {
        return mavenInfos;
    }

    public void setMavenInfos(
        List<MetadataMavenInfo> mavenInfos) {
        this.mavenInfos = mavenInfos;
    }

}
