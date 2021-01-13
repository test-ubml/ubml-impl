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

package org.openatom.ubml.model.externalapi.definition.config;

import java.util.ArrayList;
import java.util.List;

/**
 * The Config of EApi Operation
 *
 * @ClassName: EapiOpConfig
 * @Author: Fynn Qi
 * @Date: 2020/12/11 17:13
 * @Version: V1.0
 */
public class ExternalApiOperationConfig {

    public ExternalApiOperationConfig() {
        this.bizOpIdList = new ArrayList();
    }

    /**
     * 内码，唯一标识
     */
    private String id;

    /**
     * 服务ID
     */
    private String serviceId;

    /**
     * 操作编号
     */
    private String operationCode;

    /**
     * 统一资源定位符
     */
    private String uri;

    /**
     * Http方法
     */
    private String httpMethod;

    /**
     * 操作备注
     */
    private String operationNote;

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 业务操作ID集合
     */
    private List<String> bizOpIdList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getOperationNote() {
        return operationNote;
    }

    public void setOperationNote(String operationNote) {
        this.operationNote = operationNote;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public List<String> getBizOpIdList() {
        return bizOpIdList;
    }

    public void setBizOpIdList(List<String> bizOpIdList) {
        this.bizOpIdList = bizOpIdList;
    }
}
