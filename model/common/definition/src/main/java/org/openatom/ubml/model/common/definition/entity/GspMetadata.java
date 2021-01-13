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
import org.openatom.ubml.common.util.StringUtils;

/**
 * The type GspMetadata
 *
 * @author: Jack Lee
 */
public class GspMetadata {

    /**
     * Metadata header {@link MetadataHeader}
     */
    private MetadataHeader header;

    /**
     * the metadata references {@link MetadataReference}
     */
    private List<MetadataReference> refs;

    /**
     * the metadata content
     */
    private IMetadataContent content;

    /**
     * the metadata extendrule
     */
    private IMdExtRuleContent extendRule;

    /**
     * the relative path of metadata file in ide
     */
    private String relativePath;

    /**
     * extend property, must be resolved by concrete metadata
     */
    private String extendProperty;

    /**
     * the metadata is extended or not
     */
    private boolean extended;

    /**
     * the previous version of metadata, used by extend solution
     */
    private String previousVersion;

    /**
     * the current version of metadata, used by extend solution
     */
    private String version;

    /**
     * the property of metadata
     */
    private MetadataProperties properties;

    public void setHeader(MetadataHeader header) {
        this.header = header;
    }

    public MetadataHeader getHeader() {
        return header;
    }

    public void setRefs(List<MetadataReference> refs) {
        this.refs = refs;
    }

    public List<MetadataReference> getRefs() {
        return refs;
    }

    public IMetadataContent getContent() {
        return content;
    }

    public void setContent(IMetadataContent content) {
        this.content = content;
    }

    public IMdExtRuleContent getExtendRule() {
        return extendRule;
    }

    public void setExtendRule(IMdExtRuleContent extendRule) {
        this.extendRule = extendRule;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getExtendProperty() {
        return extendProperty;
    }

    public void setExtendProperty(String extendProperty) {
        this.extendProperty = extendProperty;
    }

    public boolean isExtended() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public void setPreviousVersion(String previousVersion) {
        this.previousVersion = previousVersion;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public MetadataProperties getProperties() {
        return properties;
    }

    public void setProperties(MetadataProperties properties) {
        this.properties = properties;
    }

    @Override
    public Object clone() {
        GspMetadata metadata = new GspMetadata();
        metadata.setHeader((MetadataHeader)this.header.clone());
        metadata.setRelativePath(this.getRelativePath());
        metadata.setExtendProperty(this.getExtendProperty());
        metadata.setExtended(this.isExtended());
        metadata.setPreviousVersion(this.previousVersion);
        metadata.setVersion(this.version);
        metadata.setProperties(this.getProperties() == null ? null : (MetadataProperties)this.getProperties().clone());
        metadata.setRefs(new ArrayList<>());
        if (this.refs != null && this.refs.size() > 0) {
            this.refs.forEach(item -> {
                metadata.getRefs().add((MetadataReference)item.clone());
            });

        }
        return metadata;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof GspMetadata) {
            GspMetadata otherMetadata = (GspMetadata)obj;
            return (this.getHeader().getId().equals(otherMetadata.getHeader().getId())) && compareStringValue(this.getHeader().getCertId(), otherMetadata.getHeader().getCertId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (StringUtils.isEmpty(this.getHeader().getId()) ? 0 : this.getHeader().getId().hashCode());
        result = 31 * result + (StringUtils.isEmpty(this.getHeader().getCertId()) ? 0 : this.getHeader().getCertId().hashCode());
        return result;
    }

    private boolean compareStringValue(String value1, String value2) {
        if (StringUtils.isEmpty(value1) && StringUtils.isEmpty(value2)) {
            return true;
        } else {
            if (!StringUtils.isEmpty(value1)) {
                return value1.equals(value2);
            } else {
                return false;
            }
        }
    }

}
