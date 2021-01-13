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

package org.openatom.ubml.externalapi.runtime.core.config;

import org.opeantom.ubml.externalapi.runtime.api.ExternalApiCustomizedMetadataService;
import org.opeantom.ubml.externalapi.runtime.api.ExternalApiEngine;
import org.opeantom.ubml.externalapi.runtime.api.ExternalApiMetadataService;
import org.openatom.ubml.externalapi.runtime.core.service.DefaultExternalApiCustomizedMetadataService;
import org.openatom.ubml.externalapi.runtime.core.service.DefaultExternalApiEngine;
import org.openatom.ubml.externalapi.runtime.core.service.DefaultExternalApiMetadataService;
import org.openatom.ubml.externalapi.runtime.core.service.ExternalApiJavaCodeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * BeanConfig
 *
 * @Author: Fynn Qi
 * @Date: 2019/7/22 19:00
 * @Version: V1.0
 */
@Configuration
public class ExternalApiSpringBeanConfig {

    @Bean("org.openatom.ubml.externalapi.runtime.core.config.ExternalApiSpringBeanConfig.engine")
    public ExternalApiEngine engine() {
        return new DefaultExternalApiEngine();
    }


    @Bean("org.openatom.ubml.externalapi.runtime.core.config.ExternalApiSpringBeanConfig.manager")
    public ExternalApiMetadataService manager() {
        return new DefaultExternalApiMetadataService();
    }

    @Bean("org.openatom.ubml.externalapi.runtime.core.config.ExternalApiSpringBeanConfig.customizedManager")
    public ExternalApiCustomizedMetadataService customizedManager() {
        return new DefaultExternalApiCustomizedMetadataService();
    }

    @Bean("org.openatom.ubml.externalapi.runtime.core.config.ExternalApiSpringBeanConfig.javaCodeHandler")
    public ExternalApiJavaCodeHandler javaCodeHandler() {
        return new ExternalApiJavaCodeHandler();
    }

}
