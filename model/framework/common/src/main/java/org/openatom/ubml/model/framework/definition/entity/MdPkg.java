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

import java.util.HashSet;
import java.util.List;

/**
 * Classname MdPkg Description TODO Date 2019/11/26 15:40
 *
 * @author zhongchq
 * @version 1.0
 */
public class MdPkg {

    private String mdPkgName;
    private List<MdPkg> depMdPkgs;
    private HashSet<String> metadataIds;

    public String getMdPkgName() {
        return mdPkgName;
    }

    public void setMdPkgName(String mdPkgName) {
        this.mdPkgName = mdPkgName;
    }

    public List<MdPkg> getDepMdPkgs() {
        return depMdPkgs;
    }

    public void setDepMdPkgs(List<MdPkg> depMdPkgs) {
        this.depMdPkgs = depMdPkgs;
    }

    public HashSet<String> getMetadataIds() {
        return metadataIds;
    }

    public void setMetadataIds(HashSet<String> metadataIds) {
        this.metadataIds = metadataIds;
    }

}
