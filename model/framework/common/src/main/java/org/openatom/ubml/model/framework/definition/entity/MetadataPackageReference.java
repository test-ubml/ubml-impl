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
 * @Classname MetadataPackageReference
 * @Description 元数据包依赖
 * @Date 2019/7/19 16:17
 * @Created by liu_bintr
 * @Version 1.0
 */
public class MetadataPackageReference {
    /**
     * 所依赖的元数据包 {@link MetadataPackageHeader}
     */
    private MetadataPackageHeader depententPackage;

    public MetadataPackageHeader getDepententPackage() {
        return depententPackage;
    }

    public void setDepententPackage(MetadataPackageHeader depententPackage) {
        this.depententPackage = depententPackage;
    }
}
