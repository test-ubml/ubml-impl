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

import java.io.Serializable;

/**
 * @Classname ExtractConfigration
 * @Description 抽取配置
 * @Date 2019/7/23 18:58
 * @Created by zhongchq
 * @Version 1.0
 */

public class ExtractConfigration implements Serializable {

    private String typeCode;
    private ExtractConfigData extract;
    private boolean enable;

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public ExtractConfigData getExtract() {
        return extract;
    }

    public void setExtract(ExtractConfigData extract) {
        this.extract = extract;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

}
