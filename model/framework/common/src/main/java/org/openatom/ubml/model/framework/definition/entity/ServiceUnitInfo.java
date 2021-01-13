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
 * @Classname ServiceUnitInfo
 * @Description SU信息
 * @Date 2019/7/19 16:19
 * @Created by liu_bintr
 * @Version 1.0
 */
public class ServiceUnitInfo {

    /**
     * 关键应用编号
     */
    private String appCode;

    /**
     * 服务单元编号
     */
    private String serviceUnitCode;

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

}
