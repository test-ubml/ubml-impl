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
 * Classname Metadata4RefDto Description TODO Date 2019/11/15 10:18
 *
 * @author zhongchq
 * @version 1.0
 */
public class Metadata4RefDto {

    private MetadataPackageHeader packageHeader;
    private MetadataDto metadata;

    /**
     * 元数据包服务单元信息类 {@link ServiceUnitInfo}
     */
    private ServiceUnitInfo serviceUnitInfo;

    public MetadataPackageHeader getPackageHeader() {
        return packageHeader;
    }

    public void setPackageHeader(MetadataPackageHeader packageHeader) {
        this.packageHeader = packageHeader;
    }

    public MetadataDto getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataDto metadata) {
        this.metadata = metadata;
    }

    public ServiceUnitInfo getServiceUnitInfo() {
        return serviceUnitInfo;
    }

    public void setServiceUnitInfo(ServiceUnitInfo serviceUnitInfo) {
        this.serviceUnitInfo = serviceUnitInfo;
    }

}
