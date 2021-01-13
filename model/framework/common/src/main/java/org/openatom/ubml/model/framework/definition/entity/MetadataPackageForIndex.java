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
import java.util.Map;

public class MetadataPackageForIndex {

    private String metadataPackageName;

    private String location;

    private String depFileLocation;

    private List<String> metadataIds;

    private String sourceName;

    private String sourceVersion;

    private Long lastModified;

    private Map<String, MetadataPackageForIndex> depMetadataPackages;

    public String getMetadataPackageName() {
        return metadataPackageName;
    }

    public void setMetadataPackageName(String metadataPackageName) {
        this.metadataPackageName = metadataPackageName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDepFileLocation() {
        return depFileLocation;
    }

    public void setDepFileLocation(String depFileLocation) {
        this.depFileLocation = depFileLocation;
    }

    public List<String> getMetadataIds() {
        return metadataIds;
    }

    public void setMetadataIds(List<String> metadataIds) {
        this.metadataIds = metadataIds;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceVersion() {
        return sourceVersion;
    }

    public void setSourceVersion(String sourceVersion) {
        this.sourceVersion = sourceVersion;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

    public Map<String, MetadataPackageForIndex> getDepMetadataPackages() {
        return depMetadataPackages;
    }

    public void setDepMetadataPackages(
        Map<String, MetadataPackageForIndex> depMetadataPackages) {
        this.depMetadataPackages = depMetadataPackages;
    }

}
