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

package org.openatom.ubml.model.externalapi.devtime.core.rest;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.openatom.ubml.model.externalapi.definition.entity.Operation;

/**
 * 功能描述:
 *
 * @ClassName: EapiDefinitionService
 * @Author: Fynn Qi
 * @Date: 2020/12/23 10:55
 * @Version: V1.0
 */
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ExternalApiDefinitionService {

    /**
     * 获取资源定义的服务操作集合
     *
     * @param resourceType 资源类型
     * @param resourceId   资源ID
     * @param opIdListstr  指定的操作id集合，是一个数组的拼接，例如id1,id2，以逗号隔开
     * @return 返回值
     */
    @GET
    @Path("/resources/operations")
    List<Operation> getOperations(
            @QueryParam("resourceType") String resourceType,
            @QueryParam("resourceId") String resourceId,
            @QueryParam("opIdListstr") String opIdListstr);


    /**
     * 发布Eapi
     *
     * @param metadataId
     */
    @Path("/service/publish/{metadataId}")
    @POST
    void publish(@PathParam("metadataId") String metadataId);
}
