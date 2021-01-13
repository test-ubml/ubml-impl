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

import java.util.List;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.common.definition.entity.IMdExtRuleContent;
import org.openatom.ubml.model.common.definition.entity.MetadataHeader;
import org.openatom.ubml.model.framework.definition.entity.GspProject;
import org.openatom.ubml.model.framework.definition.entity.Metadata4Ref;
import org.openatom.ubml.model.framework.definition.entity.Metadata4RefDto;
import org.openatom.ubml.model.framework.definition.entity.MetadataDto;
import org.openatom.ubml.model.framework.definition.entity.MetadataFilter;
import org.openatom.ubml.model.framework.definition.entity.MetadataIndexItemDto;
import org.openatom.ubml.model.framework.definition.entity.MetadataPackage;
import org.openatom.ubml.model.framework.definition.entity.MetadataProject;
import org.openatom.ubml.model.framework.definition.entity.MetadataType;

/**
 * @author zhaoleitr
 */
public interface MetadataService {

    /**
     * 用于新建元数据之前初始化元数据实体
     *
     * @param metadata 元数据基础信息，不包含各元数据content
     * @return
     */
    GspMetadata initializeMetadataEntity(GspMetadata metadata);

    /**
     * 新建元数据
     *
     * @param path     元数据存放路径，相对路径信息
     * @param metadata 元数据基本信息，包含命名空间、元数据名称、元数据类型、业务对象，元数据实体等信息
     */
    void createMetadata(String path, GspMetadata metadata);

    /**
     * 保存元数据
     *
     * @param metadata 元数据基本信息
     * @param fullPath 元数据文件相对于开发根目录的路径，是一个相对路径,该路径需要包含元数据文件名及后缀
     */
    void saveMetadata(GspMetadata metadata, String fullPath);

    /**
     * 根据元数据文件名称与路径信息，获取元数据
     *
     * @param metadataFileName 元数据文件名称，需要包含后缀
     * @param path             元数据文件相对路径
     * @return 元数据内容实体
     */
    GspMetadata loadMetadata(String metadataFileName, String path);

    /**
     * 根据元数据id和路径获取元数据内容，仅支持获取工程下的元数据
     *
     * @param metadataId 元数据文件名
     * @param path       元数据工程路径
     * @return 元数据内容实体
     */
    GspMetadata loadMetadataByMetadataId(String metadataId, String path);

    GspMetadata loadMetadataInBo(String metadataFullPath, String currentProjPath);

    /**
     * 根据路径和元数据id获取元数据内容，可获取工程依赖的所有元数据
     *
     * @param spacePath  元数据工程路径
     * @param metadataID 元数据id
     * @return 元数据内容实体
     */
    GspMetadata getRefMetadata(String spacePath, String metadataID);

    /**
     * 获取元数据基础信息，不包含元数据Content
     *
     * @param path 元数据文件相对于开发根目录的路径，全路径，包含文件名
     * @return 元数据实体，不含content
     */
    GspMetadata getMetadataWithoutContent(String path);

    /**
     * 删除元数据
     *
     * @param path             元数据文件相对于开发根目录的路径
     * @param metadataFileName 元数据名称，包含后缀
     */
    void deleteMetadata(String path, String metadataFileName);

    /**
     * 删除文件夹
     *
     * @param path 文件夹路径
     */
    void deleteFolder(String path);

    /**
     * 重命名元数据
     *
     * @param oldFileName      旧的文件名
     * @param newFileName      新的文件名
     * @param metadataFilePath 元数据文件相对于开发根目录的路径
     */
    void renameMetadata(String oldFileName, String newFileName, String metadataFilePath);

    /**
     * 获取某一目录及其子目录下某些类型的元数据基础信息
     *
     * @param spacePath     工程路径或下级路径
     * @param metadataTypes 元数据类型，格式为.*。例如，be元数据.be，传null则为全类型
     * @return 元数据列表，不含content
     */
    List<GspMetadata> getMetadataList(String spacePath, List<String> metadataTypes);

    /**
     * 获取某一目录及其子目录下某些类型的元数据基础信息
     *
     * @param spacePath 工程路径或下级路径
     * @return 元数据列表，不含content
     * @author zhongchq
     */
    List<GspMetadata> getMetadataList(String spacePath);

    /**
     * 根据被引用的元数据id获取依赖它的元数据列表
     *
     * @param path       元数据路径
     * @param metadataId 元数据id
     * @return 元数据信息列表
     */
    List<GspMetadata> getMetadataListByRefedMetadataId(String path, String metadataId);

    /**
     * 获取BO下元数据列表，生成型工程仅能获取生成型元数据（供元数据选择器使用）
     *
     * @param path          工程路径或下级路径
     * @param metadataTypes 元数据类型，格式为.*。例如，be元数据.be，传null则为全类型
     * @return 元数据传输实体列表
     */
    List<MetadataDto> getMetadataListInBo(String path, List<String> metadataTypes);

    /**
     * 根据过滤条件获取元数据列表，如构件可细分为多种子构件，可根据filter精确过滤具体类型的元数据
     *
     * @param path           工程路径
     * @param metadataFilter 过滤条件，包含需要精确获取元数据类型的元数据编号、要获取的元数据后缀，不传则默认获取工程下所有的元数据和字符串
     * @return
     */
    List<GspMetadata> getMetadataByFilter(String path, MetadataFilter metadataFilter);

    /**
     * 判断元数据新建的路径下是否有相同名称的文件存在
     *
     * @param path     元数据新建的路径
     * @param fileName 元数据文件名
     * @return
     */
    boolean validateRepeatName(String path, String fileName);

    /**
     * 根据元数据包路径和元数据名称获取元数据包信息
     *
     * @param packageFileName 元数据包文件名
     * @param packagePath     元数据包路径
     * @return 元数据包信息
     */
    MetadataPackage getMetadataPackageInfo(String packageFileName, String packagePath);

    /**
     * 根据元数据包信息和元数据id获取元数据信息
     *
     * @param packageName 元数据包名
     * @param packagePath 元数据路径
     * @param metadataID  元数据id
     * @return 元数据信息实体
     */
    Metadata4Ref getMetadataFromPackage(String packageName, String packagePath, String metadataID);

    /**
     * 获取元数据类型列表，需要各元数据在配置文件中配置各自的类型
     *
     * @return 元数据类型列表
     */
    List<MetadataType> getMetadataTypeList();

    /**
     * 判断元数据是否存在
     *
     * @param path             元数据路径信息
     * @param metadataFileName 元数据文件名，带后缀信息
     * @return
     */
    boolean isMetadataExist(String path, String metadataFileName);

    /**
     * 判断工程下是否存在某个ID的元数据
     *
     * @param path       元数据路径信息
     * @param metadataID metadataID
     * @return
     */
    boolean isMetadataExistInProject(String path, String metadataID);

    /**
     * 获取引用路径
     *
     * @param path 元数据所在的路径信息
     * @return
     */
    String getRefPath(String path);

    /**
     * 获取元数据头结点
     *
     * @param metadataID 元数据ID
     * @param path       元数据所在工程路径
     * @return
     */
    MetadataHeader getRefMetadataHeader(String metadataID, String path);

    /**
     * 获取元数据依赖的资源元数据信息列表
     *
     * @param metadataId 元数据id
     * @param path       元数据路径
     * @return 资源元数据header信息
     */
    List<MetadataHeader> getResourceMetadata(String metadataId, String path);

    /**
     * 设置路径上下文
     *
     * @param path
     */
    void setMetadataUri(String path);

    /**
     * 获取多余元数据
     *
     * @param fileName 文件名
     * @param path     元数据所在的路径信息
     * @param language 语言
     * @return
     */
    GspMetadata getI18nMetadata(String fileName, String path, String language);

    /**
     * 根据资源元数据获取语言包元数据
     *
     * @param resourceMdID     资源元数据id
     * @param resourceFileName 资源元数据文件名
     * @param path             工程路径
     * @return
     */
    List<GspMetadata> GetRefI18nMetadata(String resourceMdID, String resourceFileName, String path);

    /**
     * 获取元数据工程信息，元数据包、依赖的元数据包信息等打包元数据时需要用到的信息
     *
     * @param projectPath 元数据打包时选中的文件目录
     * @return 元数据工程信息
     */
    MetadataProject getMetadataProjInfo(String projectPath);

    /**
     * 获取远程元数据索引列表
     *
     * @param text                  过滤值，Code or Name
     * @param path                  工程路径
     * @param selectedPackageSource 选择的元数据包源
     * @param metadataTypeList      元数据类型列表
     * @param page                  是否分页
     * @param pageSize              每页显示多少数据
     * @param pageIndex             第多少页
     * @param isFilterByRefs        是否引用的元素回家
     * @param conflictAvoidFlag
     * @return 元数据索引信息传输列表
     */
    List<MetadataIndexItemDto> getRemoteMetadataIndexList(String text, String path, String selectedPackageSource,
        List<String> metadataTypeList, boolean page, int pageSize, int pageIndex,
        boolean isFilterByRefs, boolean conflictAvoidFlag);

    /**
     * 获取本地元数据索引信息列表
     *
     * @param text             过滤值，Code or Name
     * @param path             工程路径
     * @param metadataTypeList 元数据类型列表
     * @param page             是否分页
     * @param pageSize         每页显示多少数据
     * @param pageIndex        第多少页
     * @return
     */
    List<MetadataIndexItemDto> getLocalMetadataList(String text, String path, List<String> metadataTypeList,
        boolean page, int pageSize, int pageIndex);

    /**
     * 根据元数据索引信息获取元数据信息
     *
     * @param scopeType        元数据范围类型，本地or远程
     * @param currentPath      当前路径
     * @param metadataIndexDto 元数据索引信息
     * @param isNeedMdDto      是否需要元数据内容
     * @return 元数据信息传输实体
     */
    Metadata4RefDto pickMetadataIndex(int scopeType, String currentPath, MetadataIndexItemDto metadataIndexDto,
        boolean isNeedMdDto);

    /**
     * 获取工程信息
     *
     * @param path gsp工程路径
     * @return com.inspur.edp.lcm.metadata.api.entity.GspProject
     * @author zhongchq
     */
    GspProject getGspProjectInfo(String path);

    /**
     * 获取索引服务器状态
     *
     * @param ip   索引服务器ip
     * @param port 索引服务器端口号
     * @return 索引服务器状态
     */
    String getIndexServerStatus(String ip, String port);

    /**
     * 获取元数据扩展规则
     *
     * @param metadataId 元数据id
     * @param path       元数据路径
     * @return 元数据扩展规则
     */
    IMdExtRuleContent getMdExtRule(String metadataId, String path);

    /**
     * 保存元数据扩展规则
     *
     * @param metadataId 元数据id
     * @param path       元数据路径
     * @param extendRule 元数据扩展规则
     */
    void saveMdExtRule(String metadataId, String path, IMdExtRuleContent extendRule);

    boolean isMetadataRefed(String path);
}
