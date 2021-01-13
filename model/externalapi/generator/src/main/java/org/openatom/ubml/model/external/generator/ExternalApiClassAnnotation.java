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

package org.openatom.ubml.model.external.generator;

import java.text.MessageFormat;
import org.openatom.ubml.model.externalapi.definition.entity.ExternalApi;

/**
 * EapiClassAnnotation
 *
 * @Author: Fynn Qi
 * @Date: 2020/9/17 14:21
 * @Version: V1.0
 */
public class ExternalApiClassAnnotation {

    /**
     * 生成Eapi实现类的服务注解
     *
     * @param eapi
     * @return
     */
    public static String getEapiClassAnnotation(ExternalApi eapi) {

        // 代码样例：
        // @EapiService(id="63d7c61a-ae33-4100-aea4-976469c7f692",code="salesOrder")
        // @Path("/")
        // @Consumes(MediaType.APPLICATION_JSON)
        // @Produces(MediaType.APPLICATION_JSON)

        StringBuilder sb = new StringBuilder();
        sb.append(
                MessageFormat.format(
                        "@EapiService(id=\"{0}\",code=\"{1}\")", eapi.getId(), eapi.getCode()))
                .append(System.lineSeparator());
        String path = "/";
        sb.append(MessageFormat.format("@Path(\"{0}\")", path)).append(System.lineSeparator());
        sb.append("@Consumes(MediaType.APPLICATION_JSON)").append(System.lineSeparator());
        sb.append("@Produces(MediaType.APPLICATION_JSON)").append(System.lineSeparator());
        return sb.toString();
    }
}
