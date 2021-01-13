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

package org.openatom.ubml.model.framework.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipException;

/**
 * @author zhaoleitr
 * @Classname FileService
 * @Description 文件基础操作接口
 * @Date 2019/7/18 16:31
 * @Created by zhongchq
 * @Version 1.0
 */
public interface FileService {

    //region 文件操作

    /**
     * 文件删除
     *
     * @param path 文件路径
     * @throws IOException
     */
    void fileDelete(String path) throws IOException;

    /**
     * 文件更新
     *
     * @param path    文件路径
     * @param content 文件内容
     */
    void fileUpdate(String path, String content);

    /**
     * 文件更新
     *
     * @param path    文件路径
     * @param content 文件内容
     * @param append  是否追加
     */
    void fileUpdate(String path, String content, boolean append);

    /**
     * 判断文件是否存在
     *
     * @param path 文件路径
     * @return 文件是否存在，存在返回true，否则返回false
     */
    boolean isFileExist(String path);

    /**
     * 文件重命名
     *
     * @param oldFileName 原有文件名
     * @param newFileName 新文件名
     * @throws Exception
     */
    void fileRename(String oldFileName, String newFileName) throws Exception;

    /**
     * 文件读取
     *
     * @param path 文件路径
     * @return 文件内容
     * @throws IOException
     */
    String fileRead(String path) throws IOException;

    /**
     * 文件拷贝
     *
     * @param sourcePath      文件源路径
     * @param destinationPath 目标路径
     * @throws IOException
     */
    void fileCopy(String sourcePath, String destinationPath) throws IOException;

    //endregion

    //region 文件夹操作

    /**
     * 文件夹是否存在
     *
     * @param path 文件夹路径
     * @return 文件夹存在，返回true，否则返回false
     */
    boolean isDirectoryExist(String path);

    /**
     * 获取某一路径下的文件夹信息
     *
     * @param path 文件夹路径
     * @return 文件夹爱下文件夹信息列表
     */
    List<File> getDirectorys(String path);

    /**
     * 获取某一文件夹下的所有文件
     *
     * @param path 文件夹路径
     * @return 文件夹下文件信息列表
     */
    List<File> getAllFiles(String path);

    /**
     * 创建文件夹
     *
     * @param dirPath 文件夹路径
     */
    void createDirectory(String dirPath);

    /**
     * 删除某文件夹下的所有文件以及文件夹
     *
     * @param dirPath 文件夹路径
     * @throws IOException
     */
    void deleteAllFilesUnderDirectory(String dirPath) throws IOException;

    //endregion

    //region 路径操作

    /**
     * 整合路径
     *
     * @param path1 路径1
     * @param path2 路径2
     * @return 路径1和路径2整合后的结果，路径1在前
     */
    String getCombinePath(String path1, String path2);

    /**
     * 创建文件
     *
     * @param path     文件路径
     * @param filename 文件名
     * @throws IOException
     */
    void createFile(String path, String filename) throws IOException;

    /**
     * 创建文件
     *
     * @param filePath 文件详细路径
     * @throws IOException
     */
    void createFile(String filePath) throws IOException;

    /**
     * 获取文件后缀信息
     *
     * @param path 文件路径
     * @return 文件后缀
     */
    String getExtension(String path);

    /**
     * 获取文件后缀信息
     *
     * @param path 文件路径
     * @return 文件后缀
     */
    String getExtension(File path);

    /**
     * 获取无后缀的文件名
     *
     * @param path 文件路径
     * @return 文件名
     */
    String getFileNameWithoutExtension(String path);

    /**
     * 获取无后缀的路径信息
     *
     * @param path 文件路径
     * @return 文件名
     */
    String getFileNameWithoutExtension(File path);

    /**
     * 获取文件名
     *
     * @param path 文件路径
     * @return 文件名
     */
    String getFileName(String path);

    /**
     * 获取文件夹名
     *
     * @param path 文件夹路径
     * @return
     */
    String getDirectoryName(String path);

    /**
     * 获取目录分隔符
     *
     * @return 目录分隔符
     */
    char getDirectorySeparatorChar();

    /**
     * 获取开发根路径
     *
     * @return 开发根路径
     **/

    String getDevRootPath();

    /**
     * @param path 需要删除的目录
     * @return boolean 删除目录是否成功
     **/
    boolean deleteDirectory(String path) throws IOException;

    /**
     * 重命名文件夹
     *
     * @param fromDir 要重命名的目录
     * @param toDir   重命名后的目录
     * @return 操作是否成功
     * @throws IOException
     */
    boolean renameDirectory(String fromDir, String toDir) throws IOException;

    /**
     * 强制移动文件夹
     *
     * @param fromDir 要移动的文件夹路径
     * @param toDir   移动文件夹路径
     * @return 移动后文件路径信息
     * @throws IOException
     */
    Path forceMoveDir(String fromDir, String toDir) throws IOException;
    //endregion

    /**
     * 获取工程路径
     *
     * @param propath 绝对路径
     * @return java.lang.String
     */
    String getProjectPath(String propath);

    /**
     * 获取jar包相对于java目录的相对路径
     *
     * @param propath 工程绝对路径
     * @return jar包相对于java目录的相对路径列表
     */
    List<String> getJarPath(String propath);

    /**
     * 获取Api模块相对于java目录的相对路径
     *
     * @param proPath 工程绝对路径
     * @return Api模块相对于java目录的相对路径
     */
    String getApiModulePath(String proPath);

    /**
     * 解压的zip包
     *
     * @param zipName       被解压的zip包名
     * @param targetDirName 解压目标目录
     */
    void upzipFile(String zipName, String targetDirName) throws ZipException;

    /**
     * 获取路径下工程文件夹列表
     *
     * @param path         工程所在路径，绝对路径。
     * @param projectsList 返回值
     * @param proFlag      工程标志
     * @return 工程文件夹列表
     */
    List<String> getAllProjectsDirectories(String path, List<String> projectsList, String proFlag);

    /**
     * 目录拷贝
     *
     * @param sourcePath      源目录
     * @param destinationPath 目的目录
     */
    void folderCopy(String sourcePath, String destinationPath);
}
