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

import java.util.Map;

public class ProjectMetadataCache {

    private Map<String, String> metadataPackageLocations;

    private Map<String, MetadataPackageForIndex> directReferences;

    private Map<String, MetadataPackageForIndex> allReferences;

    public Map<String, String> getMetadataPackageLocations() {
        return metadataPackageLocations;
    }

    public void setMetadataPackageLocations(Map<String, String> metadataPackageLocations) {
        this.metadataPackageLocations = metadataPackageLocations;
    }

    public Map<String, MetadataPackageForIndex> getDirectReferences() {
        return directReferences;
    }

    public void setDirectReferences(
        Map<String, MetadataPackageForIndex> directReferences) {
        this.directReferences = directReferences;
    }

    public Map<String, MetadataPackageForIndex> getAllReferences() {
        return allReferences;
    }

    public void setAllReferences(
        Map<String, MetadataPackageForIndex> allReferences) {
        this.allReferences = allReferences;
    }
}
