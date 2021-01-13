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

import java.util.List;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;

/**
 * @Classname MetadataPackage
 * @Description 元数据包
 * @Date 2019/7/19 16:18
 * @Created by liu_bintr
 * @Version 1.0
 */
public class MetadataPackage {

    /**
     * 元数据包的头节点，包含包名及版本等基础信息 {@link MetadataPackageHeader}
     */
    private MetadataPackageHeader header;

    /**
     * 元数据包所在的服务单元信息 {@link ServiceUnitInfo}
     */
    private ServiceUnitInfo serviceUnitInfo;

    /**
     * 元数据包的依赖关系 {@link List<MetadataPackageReference>}
     */
    private List<MetadataPackageReference> reference;

    /**
     * 元数据包中所包含的元数据的基本信息 {@link List< GspMetadata >}
     */
    private List<GspMetadata> metadataList;

    public MetadataPackageHeader getHeader() {
        return header;
    }

    public void setHeader(MetadataPackageHeader header) {
        this.header = header;
    }

    public ServiceUnitInfo getServiceUnitInfo() {
        return serviceUnitInfo;
    }

    public void setServiceUnitInfo(ServiceUnitInfo serviceUnitInfo) {
        this.serviceUnitInfo = serviceUnitInfo;
    }

    public List<MetadataPackageReference> getReference() {
        return reference;
    }

    public void setReference(List<MetadataPackageReference> reference) {
        this.reference = reference;
    }

    public List<GspMetadata> getMetadataList() {
        return metadataList;
    }

    public void setMetadataList(List<GspMetadata> metadataList) {
        this.metadataList = metadataList;
    }

}
