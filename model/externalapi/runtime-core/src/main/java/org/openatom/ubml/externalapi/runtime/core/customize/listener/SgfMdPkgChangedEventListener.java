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

package org.openatom.ubml.externalapi.runtime.core.customize.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.opeantom.ubml.externalapi.runtime.api.ExternalApiCustomizedPublishService;
import org.opeantom.ubml.externalapi.runtime.api.PublishedExternalApiInfo;
import org.openatom.ubml.common.spring.SpringUtils;
import org.openatom.ubml.externalapi.runtime.core.customize.deploy.CustomizeDeploy;
import org.openatom.ubml.externalapi.runtime.core.customize.entity.CustomizeUnit;
import org.openatom.ubml.externalapi.runtime.core.customize.entity.CustomizeUnitGetter;
import org.openatom.ubml.externalapi.runtime.core.customize.entity.CustomizeUnitUtil;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;
import org.openatom.ubml.model.externalapi.definition.temp.lcm.MdPkgChangedArgs;
import org.openatom.ubml.model.externalapi.definition.temp.lcm.MdPkgChangedEventListener;
import org.openatom.ubml.model.externalapi.definition.util.MetadataUtil;
import org.openatom.ubml.model.framework.definition.entity.MetadataPackage;
import org.openatom.ubml.model.framework.definition.entity.ProcessMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 本类被配置在application.ymal文件中，请勿随意修改本类的包和类名
 *
 * @author haozhibei
 */
public class SgfMdPkgChangedEventListener implements MdPkgChangedEventListener {

    private final Logger logger = LoggerFactory.getLogger(SgfMdPkgChangedEventListener.class);

    @Override
    public void fireMdPkgAddedEvent(MdPkgChangedArgs mdPkgChangedArgs) {
        MetadataPackage metadataPackage = mdPkgChangedArgs.getMetadataPackage();
        handleMdPkg(metadataPackage);
    }

    @Override
    public void fireMdPkgChangedEvent(MdPkgChangedArgs mdPkgChangedArgs) {
        MetadataPackage metadataPackage = mdPkgChangedArgs.getMetadataPackage();
        handleMdPkg(metadataPackage);
    }

    private void handleMdPkg(MetadataPackage metadataPackage) {
        try {
            if (!ProcessMode.interpretation.equals(metadataPackage.getHeader().getProcessMode())) {
                //如果不是无需生成代码的元数据包，直接忽略
                return;
            }

            List<GspMetadata> packageMetadataList = metadataPackage.getMetadataList();
            if (packageMetadataList == null || packageMetadataList.isEmpty()) {
                //如果元数据包下不存在元数据
                return;
            }
            //过滤所有EApi元数据
            List<GspMetadata> eapiMetadatas = packageMetadataList.stream()
                    .filter(MetadataUtil::isEApiMetadata)
                    .collect(Collectors.toList());
            if (eapiMetadatas.isEmpty()) {
                //如果当前元数据包中未包含Eapi元数据，直接忽略
                return;
            }

            Map<String, CustomizeUnit> unitMap = new HashMap<>();
            //TODO:Add tenant ctxt;
            //获取当前已部署的eapi版本，并移除未变动的EApi元数据
            ExternalApiCustomizedPublishService deployment = SpringUtils.getBean(ExternalApiCustomizedPublishService.class);
            assert deployment != null;
            List<PublishedExternalApiInfo> allDeploymentInfos = deployment.getAllPublishedServices();
            Map<String, PublishedExternalApiInfo> deployInfoMap = allDeploymentInfos.stream().collect(Collectors.toMap(PublishedExternalApiInfo::getId, info -> info));
            List<GspMetadata> changedMetadatas = eapiMetadatas.stream().filter(metadata -> {
                String id = metadata.getHeader().getId();
                String version = MetadataUtil.getEApiMetadataVersion(metadata);
                return !deployInfoMap.containsKey(id) || !Objects.equals(version, deployInfoMap.get(id).getVersion());
            }).collect(Collectors.toList());

            if (changedMetadatas.isEmpty()) {
                logger.info("no changed eapi metadata need deploy");
            }

            //加载为CustomizeUnit
            unitMap.putAll(new CustomizeUnitGetter().fromMetadatas(changedMetadatas));

            //重要：后台线程无Session（租户信息），下方运行的所有代码禁止访问数据库，如需访问数据库，请合并到上方代码块中执行

            if (unitMap.isEmpty()) {
                return;
            }

            CustomizeUnitUtil.checkAndFilterRepeatedUnit(unitMap);

            //部署CustomizeUnit
            List<CustomizeUnit> units = new ArrayList<>(unitMap.values());
            CustomizeDeploy.getInstance().deploy(units);
        } catch (Exception e) {
            logger.error("部署Eapi元数据出错：" + e.getMessage(), e);
        }
    }
}
