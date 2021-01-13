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
 * @Classname CommonMetaData
 * @Description TODO
 * @Date 2019/7/22 13:43
 * @Created by zhongchq
 * @Version 1.0
 */
public class CommonConfigData {

    private String type;
    private String assembly;
    private String typeCode;
    private String typeName;
    private boolean isCreatingResourceMetadata;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAssembly() {
        return assembly;
    }

    public void setAssembly(String assembly) {
        this.assembly = assembly;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean isCreatingResourceMetadata() {
        return isCreatingResourceMetadata;
    }

    public void setCreatingResourceMetadata(boolean creatingResourceMetadata) {
        isCreatingResourceMetadata = creatingResourceMetadata;
    }

}
