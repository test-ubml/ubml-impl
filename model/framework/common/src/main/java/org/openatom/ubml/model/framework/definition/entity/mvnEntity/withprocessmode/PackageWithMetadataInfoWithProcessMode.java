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

package org.openatom.ubml.model.framework.definition.entity.mvnEntity.withprocessmode;

import java.util.List;

public class PackageWithMetadataInfoWithProcessMode {

    protected String processMode;
    protected String mavenPackageGroupId;
    protected String mavenPackageArtifactId;
    protected String mavenPackageVersion;
    protected String mavenPackageUrl;
    protected List<MetadataInfoInMavenWithProcessMode> metadatas;

    public String getProcessMode() {
        return processMode;
    }

    public void setProcessMode(String processMode) {
        this.processMode = processMode;
    }

    public String getMavenPackageGroupId() {
        return mavenPackageGroupId;
    }

    public void setMavenPackageGroupId(String mavenPackageGroupId) {
        this.mavenPackageGroupId = mavenPackageGroupId;
    }

    public String getMavenPackageArtifactId() {
        return mavenPackageArtifactId;
    }

    public void setMavenPackageArtifactId(String mavenPackageArtifactId) {
        this.mavenPackageArtifactId = mavenPackageArtifactId;
    }

    public String getMavenPackageVersion() {
        return mavenPackageVersion;
    }

    public void setMavenPackageVersion(String mavenPackageVersion) {
        this.mavenPackageVersion = mavenPackageVersion;
    }

    public String getMavenPackageUrl() {
        return mavenPackageUrl;
    }

    public void setMavenPackageUrl(String mavenPackageUrl) {
        this.mavenPackageUrl = mavenPackageUrl;
    }

    public List<MetadataInfoInMavenWithProcessMode> getMetadatas() {
        return metadatas;
    }

    public void setMetadatas(
        List<MetadataInfoInMavenWithProcessMode> metadatas) {
        this.metadatas = metadatas;
    }
}
