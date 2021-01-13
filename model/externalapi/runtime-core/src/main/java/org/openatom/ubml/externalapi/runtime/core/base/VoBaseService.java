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

package org.openatom.ubml.externalapi.runtime.core.base;

import com.fasterxml.jackson.databind.JsonNode;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * 功能描述:VO内置服务操作
 *
 * @ClassName: VoBaseService
 * @Author: Fynn Qi
 * @Date: 2019/7/29 10:09
 * @Version: V1.0
 */
public interface VoBaseService {

    /**
     * 关闭Session
     */
    @POST
    @Path("/service/closesession")
    void closeSession();

    /**
     * Delete and sava
     *
     * @param dataId
     * @param node
     * @return
     */
    @PUT
    @Path("/service/delete/{dataId}")
    Object deleteAndSave(@PathParam("dataId") String dataId, JsonNode node);

    /**
     * retrieveWithChildPagination
     *
     * @param dataId
     * @param node
     * @return
     */
    @PUT
    @Path("service/retrieve/{dataId}")
    Object retrieveWithChildPagination(@PathParam("dataId") String dataId, JsonNode node);

    /**
     * retrieveChildByIndex
     *
     * @param node
     * @return
     */
    @PUT
    @Path("service/querychild")
    Object retrieveChildByIndex(JsonNode node);

    /**
     * extensionFilter
     *
     * @param node
     * @return
     */
    @POST
    @Path("/extension/filter")
    Object extensionFilter(JsonNode node);

}
