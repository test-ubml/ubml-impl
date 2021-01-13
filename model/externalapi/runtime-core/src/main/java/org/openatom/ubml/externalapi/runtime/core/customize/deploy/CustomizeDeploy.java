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

package org.openatom.ubml.externalapi.runtime.core.customize.deploy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.opeantom.ubml.externalapi.runtime.api.EndpointInfo;
import org.opeantom.ubml.externalapi.runtime.api.PublishedExternalApiInfo;
import org.openatom.ubml.common.spring.SpringUtils;
import org.openatom.ubml.common.util.StringUtils;
import org.openatom.ubml.externalapi.runtime.core.customize.compile.CustomizeUnitCompiler;
import org.openatom.ubml.externalapi.runtime.core.customize.entity.CustomizeUnit;
import org.openatom.ubml.externalapi.runtime.core.jit.load.ClassloaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomizeDeploy {

    private static Logger logger = LoggerFactory.getLogger(CustomizeDeploy.class);

    private static final CustomizeDeploy INSTANCE = new CustomizeDeploy();

    private Map<String, String> unitEndpointAddressMap = new HashMap<>();
    private ClassloaderService classloaderService;

    private Map<String, PublishedExternalApiInfo> publishedInfo = new ConcurrentHashMap<>();

    private CustomizeDeploy() {
    }

    public static CustomizeDeploy getInstance() {
        return INSTANCE;
    }

    /**
     * 部署CustomizeUnit。编译所有代码，发布服务。
     */
    public void deploy(CustomizeUnit unit) {
        //编译代码
        logger.info("Deploy start");
        CustomizeUnitCompiler.getInstance().compile(unit);
        logger.info("Deploy compileEnd");

        //注册Jaxrs服务
        EndpointInfo endpointInfo = registerJaxrsService(unit);

        updateDeploymentInfo(unit, endpointInfo);
        logger.info("Deploy registJaxrsEnd");
    }

    /**
     * 部署CustomizeUnit。编译所有代码，发布服务。
     */
    public void deploy(List<CustomizeUnit> units) {
        logger.info("Deploy start");
        CustomizeUnitCompiler.getInstance().batCompile(units);
        logger.info("Deploy compileEnd");

        for (CustomizeUnit unit : units) {
            EndpointInfo endpointInfo = registerJaxrsService(unit);
            updateDeploymentInfo(unit, endpointInfo);
        }
        logger.info("Deploy registJaxrsEnd");
    }

    private EndpointInfo registerJaxrsService(CustomizeUnit unit) {
        //加载实现类实例
        List<Object> beans = new ArrayList<>();
        unit.getEndpointClassNames().forEach(className -> {
            try {
                Class<?> beanClass = getClassloaderService().load("eapi", unit.getId(), className);
                Object obj = beanClass.getDeclaredConstructor().newInstance();
                beans.add(obj);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        });

        //TODO：调用Jaxrs发布服务（如果新旧服务的url相同，则自动覆盖）

        //结束原有服务
        boolean hasOldEndpoint = unitEndpointAddressMap.containsKey(unit.getId());
        String oldEndpointAddress = unitEndpointAddressMap.put(unit.getId(), unit.getEndpointAddress());
        if (hasOldEndpoint && oldEndpointAddress != null && !StringUtils.equals(oldEndpointAddress, unit.getEndpointAddress())) {
            //当新旧服务的url不一致时，停止旧服务。
            //当新旧服务的url相同，新服务会自动覆盖旧服务。
            //TODO: 调用Jaxrs取消发布服务（如果新旧服务的url相同，则自动覆盖）

        }

        EndpointInfo endpointInfo = new EndpointInfo();
        endpointInfo.setPath(unit.getEndpointAddress());
        endpointInfo.setServices(Collections.unmodifiableList(beans));
        return endpointInfo;
    }

    public void removeDeploy(String unitId) {
        //移除发布的jaxrs服务
        //TODO:移除JaxrsRegist
        publishedInfo.remove(unitId);

        //停止原有ClassLoader
        classloaderService.unload("eapi", unitId);
    }

    private ClassloaderService getClassloaderService() {
        if (classloaderService == null) {
            classloaderService = SpringUtils.getBean(ClassloaderService.class);
        }
        return classloaderService;
    }

    private void updateDeploymentInfo(CustomizeUnit unit, EndpointInfo endpointInfo) {
        PublishedExternalApiInfo deploymentInfo = new PublishedExternalApiInfo();
        deploymentInfo.setVersion(unit.getVersion());
        deploymentInfo.setId(unit.getId());
        deploymentInfo.setEndpoint(endpointInfo);
        publishedInfo.put(unit.getId(), deploymentInfo);
    }

    public List<PublishedExternalApiInfo> getAllDeploymentInfos() {
        return new ArrayList<>(publishedInfo.values());
    }
}
