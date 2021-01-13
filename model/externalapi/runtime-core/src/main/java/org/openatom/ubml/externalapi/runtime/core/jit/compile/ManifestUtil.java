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

package org.openatom.ubml.externalapi.runtime.core.jit.compile;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openatom.ubml.common.util.JacksonJsonUtil;
import org.openatom.ubml.externalapi.runtime.core.jit.common.DirectoryManager;

/**
 * The utils for manifest
 *
 * @author haozhibei
 */
public class ManifestUtil {
    /**
     * 更新Manifest.json信息
     *
     * @param context 编译上下文
     */
    public static void updateManifest(CompileContext context) {
        Manifest manifest = new Manifest();
        manifest.setVersion(context.getVersion());
        String content = JacksonJsonUtil.toJson(manifest);
        File file = getManifestJsonFile(context.getFunctionType(), context.getFunctionId());
        try {
            FileUtils.write(file, content, "utf-8", false);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 得到功能包的Manifest信息，如果功能包不存在或者manifest.json文件不存在，则返回null。
     *
     * @param functionType 功能包类型
     * @param functionId   功能包Id
     * @return 功能包的Manifest信息，如果功能包不存在或者manifest.json文件不存在，则返回null
     */
    public static Manifest getManifest(String functionType, String functionId) {
        File file = getManifestJsonFile(functionType, functionId);
        if (!file.exists()) {
            return null;
        }

        try {
            String content = FileUtils.readFileToString(file, "utf-8");
            if (content.isEmpty()) {
                return new Manifest();
            }

            return JacksonJsonUtil.toObject(content, Manifest.class);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static File getManifestJsonFile(String functionType, String functionId) {
        String functionDir = DirectoryManager.getJitDirectory(functionType, functionId);
        String path = DirectoryManager.joinFilePath(functionDir, "manifest.json");
        return new File(path);
    }
}
