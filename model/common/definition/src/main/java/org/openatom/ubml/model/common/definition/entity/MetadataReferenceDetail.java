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

package org.openatom.ubml.model.common.definition.entity;

/**
 * The type MetadataReferenceDetail
 *
 * @author: Jack Lee
 */
public class MetadataReferenceDetail {

    /**
     * metadata element id
     */
    private String elementId;

    /**
     * the dependened element id
     */
    private String dependentElementId;

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getDependentElementId() {
        return dependentElementId;
    }

    public void setDependentElementId(String dependentElementId) {
        this.dependentElementId = dependentElementId;
    }

    @Override
    public final Object clone() {
        MetadataReferenceDetail metadataReferenceDetail = new MetadataReferenceDetail();
        metadataReferenceDetail.setElementId(this.getElementId());
        metadataReferenceDetail.setDependentElementId(this.getDependentElementId());

        return metadataReferenceDetail;
    }

}
