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

package org.openatom.ubml.model.framework.definition.entity.mvnEntity;

import java.util.List;

public class MetadataInfoFilter {

    protected String codeOrName;
    protected List<String> metadataTypes;
    protected boolean pageable;
    protected int pageSize;
    protected int pageIndex;
    protected boolean fromDb;
    protected List<String> packageSources;
    protected List<MetadataMavenInfo> refPackages;
    protected String packageCodeExcluded;

    public String getCodeOrName() {
        return codeOrName;
    }

    public void setCodeOrName(String codeOrName) {
        this.codeOrName = codeOrName;
    }

    public List<String> getMetadataTypes() {
        return metadataTypes;
    }

    public void setMetadataTypes(List<String> metadataTypes) {
        this.metadataTypes = metadataTypes;
    }

    public boolean isPageable() {
        return pageable;
    }

    public void setPageable(boolean pageable) {
        this.pageable = pageable;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public boolean isFromDb() {
        return fromDb;
    }

    public void setFromDb(boolean fromDb) {
        this.fromDb = fromDb;
    }

    public List<String> getPackageSources() {
        return packageSources;
    }

    public void setPackageSources(List<String> packageSources) {
        this.packageSources = packageSources;
    }

    public List<MetadataMavenInfo> getRefPackages() {
        return refPackages;
    }

    public void setRefPackages(
        List<MetadataMavenInfo> refPackages) {
        this.refPackages = refPackages;
    }

    public String getPackageCodeExcluded() {
        return packageCodeExcluded;
    }

    public void setPackageCodeExcluded(String packageCodeExcluded) {
        this.packageCodeExcluded = packageCodeExcluded;
    }

}
