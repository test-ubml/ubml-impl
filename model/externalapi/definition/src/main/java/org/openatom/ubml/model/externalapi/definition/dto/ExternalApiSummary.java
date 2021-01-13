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

package org.openatom.ubml.model.externalapi.definition.dto;


/**
 * The summary of EApi
 */
public class ExternalApiSummary {

    private String id;

    private String serviceUnitCode;

    private String serviceUnitName;

    private String bizObjectId;

    private String serviceCode;

    private String serviceName;

    private String basePath;

    private String version;

    private String bizObjectCode;

    private String bizObjectName;

    private String sourceType;

    private String sourceId;

    private String sourceVersion;

    private String serviceUsage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceUnitCode() {
        return serviceUnitCode;
    }

    public void setServiceUnitCode(String serviceUnitCode) {
        this.serviceUnitCode = serviceUnitCode;
    }

    public String getServiceUnitName() {
        return serviceUnitName;
    }

    public void setServiceUnitName(String serviceUnitName) {
        this.serviceUnitName = serviceUnitName;
    }

    public String getBizObjectId() {
        return bizObjectId;
    }

    public void setBizObjectId(String bizObjectId) {
        this.bizObjectId = bizObjectId;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBizObjectCode() {
        return bizObjectCode;
    }

    public void setBizObjectCode(String bizObjectCode) {
        this.bizObjectCode = bizObjectCode;
    }

    public String getBizObjectName() {
        return bizObjectName;
    }

    public void setBizObjectName(String bizObjectName) {
        this.bizObjectName = bizObjectName;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceVersion() {
        return sourceVersion;
    }

    public void setSourceVersion(String sourceVersion) {
        this.sourceVersion = sourceVersion;
    }

    public String getServiceUsage() {
        return serviceUsage;
    }

    public void setServiceUsage(String serviceUsage) {
        this.serviceUsage = serviceUsage;
    }
}
