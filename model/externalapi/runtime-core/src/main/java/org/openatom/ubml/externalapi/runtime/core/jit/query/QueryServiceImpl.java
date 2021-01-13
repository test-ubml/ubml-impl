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

package org.openatom.ubml.externalapi.runtime.core.jit.query;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.openatom.ubml.externalapi.runtime.core.jit.common.DirectoryManager;
import org.openatom.ubml.externalapi.runtime.core.jit.compile.Manifest;
import org.openatom.ubml.externalapi.runtime.core.jit.compile.ManifestUtil;
import org.springframework.stereotype.Component;

@Component("org.openatom.ubml.externalapi.runtime.core.jit.query.QueryServiceImpl")
public class QueryServiceImpl implements QueryService {

    @Override
    public List<FunctionSummaryInfo> query(String functionType) {
        String functionTypePath = DirectoryManager.getJitDirectory(functionType);
        List<String> ids = DirectoryManager.listChildrenDirName(functionTypePath);
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }

        List<FunctionSummaryInfo> infos = new ArrayList<>(ids.size());
        for (String id : ids) {
            FunctionSummaryInfo info = query(functionType, id);
            infos.add(info);
        }
        return infos;
    }

    @Override
    public FunctionSummaryInfo query(String functionType, String functionId) {
        String functionDirPath = DirectoryManager.getJitDirectory(functionType, functionId);
        File functionDir = new File(functionDirPath);
        if (!functionDir.exists()) {
            //当不存在功能包时，返回null
            return null;
        }

        Manifest manifest = ManifestUtil.getManifest(functionType, functionId);
        FunctionSummaryInfo info = new FunctionSummaryInfo();
        info.setFunctionType(functionType);
        info.setFunctionId(functionId);
        if (manifest == null) {
            info.setVersion(null);
        } else {
            info.setVersion(manifest.getVersion());
        }
        return info;
    }

    @Override
    public void delete(String functionType, String functionId) {
        String dirPath = DirectoryManager.getJitDirectory(functionType, functionId);
        File dir = new File(dirPath);
        try {
            if (dir.exists()) {
                FileUtils.forceDelete(dir);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(String functionType) {
        String dirPath = DirectoryManager.getJitDirectory(functionType);
        File dir = new File(dirPath);
        try {
            if (dir.exists()) {
                FileUtils.forceDelete(dir);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void batchDelete(String functionType, List<String> functionIds) {
        for (String id : functionIds) {
            delete(functionType, id);
        }
    }
}
