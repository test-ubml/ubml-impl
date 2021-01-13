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

package org.openatom.ubml.externalapi.runtime.core.jit.load;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.openatom.ubml.externalapi.runtime.core.jit.common.DirectoryManager;

/**
 * The ClassLoadManager
 *
 * @author haozhibei
 */
public class ClassLoaderManager {

    private static Map<String, Map<String, JitClassLoader>> classLoaderMap = new ConcurrentHashMap<>();

    public static Class load(String functionType, String functionId, String fullName) {
        if (!classLoaderMap.containsKey(functionType)) {
            classLoaderMap.put(functionType, new HashMap<>());
        }
        JitClassLoader classLoader = null;
        if (!classLoaderMap.get(functionType).containsKey(functionId)) {
            String jitDirectory = DirectoryManager.getJitDirectory(functionType, functionId);
            classLoader = new JitClassLoader(jitDirectory);
        } else {
            classLoader = classLoaderMap.get(functionType).get(functionId);
        }
        try {
            return classLoader.loadClass(fullName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("load class error ", e);
        }
    }


    public static void unLoad(String functionType, String functionId) {
        if (classLoaderMap.containsKey(functionType) && classLoaderMap.get(functionType).containsKey(functionId)) {
            JitClassLoader loader = classLoaderMap.get(functionType).get(functionId);
            classLoaderMap.get(functionType).remove(functionId);
            try {
                loader.close();
            } catch (IOException e) {
                throw new RuntimeException("close class loader failure:", e);
            }
        }
    }

}
