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

package org.openatom.ubml.metamodel.framework.designtime.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.openatom.ubml.model.framework.definition.entity.GspProject;
import org.openatom.ubml.model.framework.definition.entity.MetadataPackageHeader;
import org.openatom.ubml.model.framework.definition.entity.MetadataProject;
import org.openatom.ubml.model.framework.definition.entity.ProcessMode;
import org.openatom.ubml.model.framework.definition.entity.mvnEntity.MavenPackageRefs;

/**
 * @author zhaoleitr
 * 元数据工程服务
 */
public interface MetadataProjectService {

    /**
     * 元数据工程新建，元数据工程名必填，如果命名空间及包名不填，则默认赋值为工程名
     *
     * @param path             元数据工程所在的路径，即选中的文件目录
     * @param projectName      元数据工程名称
     * @param projectNameSpace 元数据工程命名空间
     * @param packageName      元数据包名
     * @param processMode      工程处理方式
     */
    void create(String path, String projectName, String projectNameSpace, String packageName, String processMode);

    /**
     * 创建GSP Project
     *
     * @param path        工程路径
     * @param projectData 外部资源
     */
    void createGspProject(String path, GspProject projectData);

    /**
     * 更新mdproj引用
     *
     * @param projPath     工程路径
     * @param metadataProj 工程实体
     * @param packageRefs  引用信息
     */
    void updateMavenRefs(String projPath, MetadataProject metadataProj, MavenPackageRefs packageRefs);

    /**
     * 更新工程引用
     *
     * @param projPath      工程路径
     * @param metadataProj  工程实体
     * @param packageHeader 元数据包头信息
     */
    void updateRefs(String projPath, MetadataProject metadataProj, MetadataPackageHeader packageHeader);

    /**
     * 更新工程引用
     *
     * @param projPath      工程路径
     * @param packageHeader 元数据包头信息
     */
    void updateRefs(String projPath, MetadataPackageHeader packageHeader);

    /**
     * 元数据工程路径
     *
     * @param path 工程路径
     * @return 如果存在文件，返回true，否则，返回false
     */
    boolean isExistProjFile(String path);

    /**
     * 重命名元数据工程
     *
     * @param path    元数据工程所在路径
     * @param oldName 旧文件名
     * @param newName 新文件名
     */
    void rename(String path, String oldName, String newName);

    /**
     * 获取元数据工程信息
     *
     * @param path 工程路径
     * @return 元数据工程信息
     */
    MetadataProject getMetadataProjInfo(String path);

    /**
     * 获取Java构件工程路径
     *
     * @param path 工程路径
     * @return
     */
    String getJavaCompProjectPath(String path);

    /**
     * 获取依赖的工程的路径
     *
     * @param projPath     当前工程路径
     * @param refProjPaths 依赖的工程路径列表
     */
    void getRefProjPaths(String projPath, List<String> refProjPaths);

    /**
     * 获取工程路径
     *
     * @param path 当前路径
     * @return 当前路径下或者父级（递归）目录下工程文件路径
     */
    String getProjPath(String path);

    /**
     * @param path         文件夹路径路径
     * @param projPathList 工程路径列表
     * @return 元数据工程信息
     */
    MetadataProject getMetadataProjRecursively(String path, List<String> projPathList);

    /**
     * 更新mdproj引用(版本)
     *
     * @param projFilePath     工程路径
     * @param proj             工程实体
     * @param mavenPackageRefs 引用信息
     */
    void updateMavenRefVersion(String projFilePath, MetadataProject proj, MavenPackageRefs mavenPackageRefs);

    /**
     * 获取工程编译顺序（BO内引用场景）
     *
     * @param path 当前工程路径
     * @return 排序后的工程目录列表
     */
    List<String> getBuildOrder(String path);

    /**
     * 获取工程最后修改时间
     *
     * @param projPath 工程路径
     * @return 工程最后修改时间
     */
    Map<String, Map<String, Long>> getSourceDataModifiedTime(String projPath);

    /**
     * 检查工程下元数据是否发生变化
     *
     * @param projPath 工程路径
     * @return 工程下元数据发生变化返回true，否则返回false
     */
    boolean metadataChangesDetected(String projPath);

    /**
     * 检查工程生成的代码是否发生变化
     *
     * @param projPath 工程路径
     * @param type
     * @return 工程下生成的代码如果发生了变化返回true，否则返回false
     */
    boolean codeChangesDetected(String projPath, String type);

    /**
     * 设置工程下文件最后修改时间
     *
     * @param projPath 工程路径
     * @param types
     * @throws IOException
     */
    void setSourceDataModifiedTime(String projPath, List<String> types) throws IOException;

    /**
     * 设置强制更新依赖jar包标识
     *
     * @param projPath 工程路径
     * @param flag     maven更新标识
     */
    @Deprecated
    void setMavenUpdateFlag(String projPath, Boolean flag);

    /**
     * @param projPath 工程路径
     * @return maven更新标识
     */
    @Deprecated
    String getMavenUpdateFlag(String projPath);


    /**
     * 获取工程类型（生成型 or 解析型）
     *
     * @param path 工程路径
     * @return 工程类型
     */
    ProcessMode getProcessMode(String path);

    /**
     * 获取路径下工程了路径列表
     *
     * @param path 当前路径
     * @return 工程路径列表
     */
    List<String> getProjPathsInPath(String path);

    /**
     * 元数据包名是否在bo内存在
     *
     * @param path      工程路径或下级路径
     * @param mdpkgName 元数据包名
     * @return 同名的工程名称
     */
    String getMdpkgNameExistInBo(String path, String mdpkgName);

    boolean isExistProjFileRecursively(String path);

    String getBoPath(String path);

    boolean isInterpretation(String path);
}
