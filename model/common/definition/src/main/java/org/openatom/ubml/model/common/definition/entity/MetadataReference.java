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

import java.util.ArrayList;
import java.util.List;

/**
 * The type MetadataReference
 *
 * @author: Jack Lee
 */
public class MetadataReference {
    /**
     * metadata info
     */
    private MetadataHeader metadata;

    /**
     * the dependended metadata info
     */
    private MetadataHeader dependentMetadata;

    /**
     * details of reference
     */
    private List<MetadataReferenceDetail> referenceDetail;

    public void setMetadata(MetadataHeader metadata) {
        this.metadata = metadata;
    }

    public MetadataHeader getMetadata() {
        return metadata;
    }

    public void setDependentMetadata(MetadataHeader dependentMetadata) {
        this.dependentMetadata = dependentMetadata;
    }

    public MetadataHeader getDependentMetadata() {
        return dependentMetadata;
    }

    public void setReferenceDetail(
        List<MetadataReferenceDetail> referenceDetail) {
        this.referenceDetail = referenceDetail;
    }

    public List<MetadataReferenceDetail> getReferenceDetail() {
        return referenceDetail;
    }


    @Override
    public Object clone() {
        MetadataReference metadataReference = new MetadataReference();
        metadataReference.setMetadata((MetadataHeader) this.metadata.clone());
        metadataReference.setDependentMetadata((MetadataHeader) this.dependentMetadata.clone());
        metadataReference.setReferenceDetail(new ArrayList<>());
        if (this.referenceDetail == null || this.referenceDetail.size() <= 0) {
            return metadataReference;
        } else {
            this.referenceDetail.forEach(item -> {
                metadataReference.getReferenceDetail().add((MetadataReferenceDetail) item.clone());
            });

            return metadataReference;
        }
    }

}
