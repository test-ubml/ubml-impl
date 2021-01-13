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

package org.openatom.ubml.externalapi.runtime.core.base.simple;

/**
 * SimpleVoBaseServiceImpl
 *
 * @Author: Fynn Qi
 * @Date: 2020/7/31 11:05
 * @Version: V1.0
 */
public class SimpleVoBaseServiceImpl implements SimpleVoBaseService {

    /**
     * VO元数据的ID
     */
    private String voId;

    /**
     * VO的ConfigId
     */
    private String voCode;

    public String getVoId() {
        return voId;
    }

    public void setVoId(String voId) {
        this.voId = voId;
    }

    public String getVoCode() {
        return voCode;
    }

    public void setVoCode(String voCode) {
        this.voCode = voCode;
    }
}
