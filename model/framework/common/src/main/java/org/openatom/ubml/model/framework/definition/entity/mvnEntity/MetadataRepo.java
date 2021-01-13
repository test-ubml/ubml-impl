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

/**
 * Classname MetadataRepo Description 获取仓库列表实体 Date 2019/11/30 13:30
 *
 * @author zhongchq
 * @version 1.0
 */
public class MetadataRepo {
    private List<PackageSourceDto> repoList;

    public List<PackageSourceDto> getRepoList() {
        return repoList;
    }

    public void setRepoList(
        List<PackageSourceDto> repoList) {
        this.repoList = repoList;
    }
}
