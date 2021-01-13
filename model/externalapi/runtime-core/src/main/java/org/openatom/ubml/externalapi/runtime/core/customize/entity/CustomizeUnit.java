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

package org.openatom.ubml.externalapi.runtime.core.customize.entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.openatom.ubml.externalapi.runtime.core.jit.compile.JavaSourceCode;

/**
 * 运行时定制单元。该类的一个实例为一个完整的运行时定制单元。包含一个jaxrs服务发布地址和其对应的实例类信息。
 *
 * @author haozhibei
 */
public class CustomizeUnit {

    private String id;

    private String version;

    private JavaSourceCode serviceInfo;

    private String endpointAddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public JavaSourceCode getServiceInfo() {
        return serviceInfo;
    }

    public void setServiceInfo(JavaSourceCode serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    public String getEndpointAddress() {
        return endpointAddress;
    }

    public void setEndpointAddress(String endpointAddress) {
        this.endpointAddress = endpointAddress;
    }

    public CustomizeUnit() {
    }

    public CustomizeUnit(String unitId, String version, JavaSourceCode serviceInfo, String endpointAddress) {
        this.id = unitId;
        this.version = version;
        this.serviceInfo = serviceInfo;
        this.endpointAddress = endpointAddress;
    }

    /**
     * 得到所有的JavaClassSource对象
     *
     * @return 该CustomizeUnit包含的所有JavaClassSource对象。
     */
    public List<JavaSourceCode> getAllJavaClassSources() {
        return Arrays.asList(serviceInfo);
    }

    /**
     * 得到Jaxrs服务对应的所有实例类类名
     *
     * @return Jaxrs服务对应的所有实例类类名
     */
    public List<String> getEndpointClassNames() {
        //目前只有服务实现类会发布jaxrs服务
        return Collections.singletonList(serviceInfo.getFullClassName());
    }
}
