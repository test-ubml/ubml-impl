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

package org.openatom.ubml.model.externalapi.definition.temp.lcm;

/**
 * The type FileService
 *
 * @author: haozhibei
 */
public interface FileService {
    /**
     * getcom
     *
     * @param path
     * @param replace
     * @return
     */
    String getCombinePath(String path, String replace);

    /**
     * getbo
     *
     * @param dir
     * @return
     */
    boolean isDirectoryExist(String dir);

    /**
     * cre
     *
     * @param dir
     */
    void createDirectory(String dir);

    /**
     * is
     *
     * @param path
     * @return
     */
    boolean isFileExist(String path);

    /**
     * cre
     *
     * @param dir
     * @param name
     */
    void createFile(String dir, String name);

    /**
     * read
     *
     * @param path
     * @return
     */
    String fileRead(String path);

    /**
     * up
     *
     * @param path
     * @param code
     * @param b
     */
    void fileUpdate(String path, String code, boolean b);

    /**
     * delete
     *
     * @param path
     */
    void fileDelete(String path);

    /**
     * update
     *
     * @param path
     * @param code
     */
    void fileUpdate(String path, String code);
}
