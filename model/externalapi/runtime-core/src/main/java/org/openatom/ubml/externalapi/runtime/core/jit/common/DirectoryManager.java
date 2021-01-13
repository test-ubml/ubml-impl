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

package org.openatom.ubml.externalapi.runtime.core.jit.common;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.openatom.ubml.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Directory manager
 *
 * @author haozhibei
 */
public class DirectoryManager {

    private static Logger logger = LoggerFactory.getLogger(DirectoryManager.class);

    private static final String BOOT_ENV_NAME = "CAF_BOOT_HOME";
    private static final String BOOT_LIB_DIRECTORY_NAME = "bootlibs";
    private static String JIT_BASE_DIR = "var" + File.separator + "jit";
    private static String HOME_DIR = null;
    private static Object lockObj = new Object();

    public static String getUnZipBootJarDirectory() {
        return getHomeDir() + File.separator + JIT_BASE_DIR + File.separator + BOOT_LIB_DIRECTORY_NAME;
    }

    public static String getHomeDir() {
        if (HOME_DIR == null) {
            String cafHome = System.getenv(BOOT_ENV_NAME);
            if (cafHome == null || cafHome == "") {
                throw new RuntimeException("获取环境参数CAF_BOOT_HOME异常，获取的值为null");
            }
            HOME_DIR = cafHome;
        }
        return HOME_DIR;
    }

    public static String getJitBaseDirectory() {
        return getHomeDir() + File.separator + JIT_BASE_DIR;
    }

    public static String getJitDirectory(String functionType) {
        return getJitBaseDirectory() + File.separator + functionType;
    }

    public static String getJitDirectory(String functionType, String functionId) {
        return getJitDirectory(functionType) + File.separator + functionId;
    }

    public static void removeFunctionDir(String functionType, String functionId) {
        String dir = getJitDirectory(functionType, functionId);
        File file = new File(dir);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 获取指定目录的所有直接子文件夹的名称列表。
     *
     * @param parentDirPath 父文件夹路径
     * @return 所有直接子文件夹的名称列表，只包含文件名，不包含路径信息。
     */
    public static List<String> listChildrenDirName(String parentDirPath) {
        File parentDir = new File(parentDirPath);
        if (!parentDir.exists()) {
            return Collections.emptyList();
        }

        if (parentDir.isFile()) {
            return Collections.emptyList();
        }

        File[] children = null;
        try {
            children = parentDir.listFiles();
        } catch (SecurityException e) {
            logger.error("获取文件夹信息异常：" + e.getMessage(), e);
        }

        if (children == null || children.length == 0) {
            return Collections.emptyList();
        }

        List<String> result = new ArrayList<>();
        for (File child : children) {
            if (child.isDirectory()) {
                result.add(child.getName());
            }
        }
        return result;
    }

    public static void clean() {
        String dir = getJitBaseDirectory();
        File file = new File(dir);
        if (file.exists()) {
            file.delete();
        }
    }


    public static boolean createDir(String creatingDir) {
        File file = new File(creatingDir);
        if (!file.exists()) {
            synchronized (lockObj) {
                if (!file.exists()) {
                    file.mkdirs();
                    return true;
                }
            }
        }
        return false;
    }

    public static String joinFilePath(String... paths) {
        return StringUtils.join(paths, File.separator);
    }
}
