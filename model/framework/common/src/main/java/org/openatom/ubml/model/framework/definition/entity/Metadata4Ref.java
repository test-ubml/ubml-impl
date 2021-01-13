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

import org.openatom.ubml.model.common.definition.entity.GspMetadata;

/**
 * @Classname Metadata4Ref
 * @Description 元数据及包的实体
 * @Date 2019/7/19 16:23
 * @Created by liu_bintr
 * @Version 1.0
 */
public class Metadata4Ref {

    /**
     * 元数据包的基本信息 {@link MetadataPackageHeader}
     */
    private MetadataPackageHeader packageHeader;

    /**
     * 元数据包服务单元信息类 {@link ServiceUnitInfo}
     */
    private ServiceUnitInfo serviceUnitInfo;

    /**
     * 元数据信息， {@link GspMetadata}
     */
    private GspMetadata metadata;

    public MetadataPackageHeader getPackageHeader() {
        return packageHeader;
    }

    public void setPackageHeader(MetadataPackageHeader packageHeader) {
        this.packageHeader = packageHeader;
    }

    public ServiceUnitInfo getServiceUnitInfo() {
        return serviceUnitInfo;
    }

    public void setServiceUnitInfo(ServiceUnitInfo serviceUnitInfo) {
        this.serviceUnitInfo = serviceUnitInfo;
    }

    public GspMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(GspMetadata metadata) {
        this.metadata = metadata;
    }

}
