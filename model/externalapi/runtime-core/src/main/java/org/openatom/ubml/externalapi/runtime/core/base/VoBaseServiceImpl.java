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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.opeantom.ubml.externalapi.runtime.api.ExternalApiInvokeAgent;
import org.openatom.ubml.common.util.JacksonJsonUtil;
import org.openatom.ubml.model.externalapi.definition.Constants;

/**
 * VoBaseServiceImpl
 *
 * @Author: Fynn Qi
 * @Date: 2019/7/22 10:16
 * @Version: V1.0
 */
public class VoBaseServiceImpl implements VoBaseService {

    /**
     * 框架级服务操作名称，关闭Session
     */
    private static final String BASE_SERVICE_CLOSESESSION_OPID = "CloseSession";

    /**
     * 删除并保存
     */
    private static final String BASE_SERVICE_DELETEANDSAVE_OPID = "DeleteAndSave";

    private static final String BASE_SERVICE_RETRIEVE_WITH_CHILD_PAGINATION_OPID = "RetrieveWithChildPagination";

    private static final String BASE_SERVICE_RETRIEVE_CHILD_BY_INDEX_OPID = "QueryChild";

    private static final String BASE_SERVICE_EXTENSION_FILTER_OPID = "Extension_Filter";

    /**
     * VO元数据的ID
     */
    private String voId;

    /**
     * VO的ConfigId
     */
    private String voCode;

    public String getVoId() {
        return voId;
    }

    public void setVoId(String voId) {
        this.voId = voId;
    }

    public String getVoCode() {
        return voCode;
    }

    public void setVoCode(String voCode) {
        this.voCode = voCode;
    }

    /**
     * 关闭Session
     */
    @Override
    public void closeSession() {
        ExternalApiInvokeAgent.invokeByObject(Constants.EXTERNAL_API_RESOURCE_TYPE_VO, this.getVoCode(), BASE_SERVICE_CLOSESESSION_OPID,
                new ArrayList<>());
    }

    /**
     * 删除并保存
     */
    @Override
    public Object deleteAndSave(String dataId, JsonNode node) {
        ArrayList<JsonNode> jsonNodes = new ArrayList<>();
        try {
            jsonNodes.add(node);
            jsonNodes.add(JacksonJsonUtil.getMapper().valueToTree(dataId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ExternalApiInvokeAgent.invokeByJsonNode(
                Constants.EXTERNAL_API_RESOURCE_TYPE_VO,
                this.getVoCode(),
                MessageFormat.format("{0}&^^&{1}", voId, BASE_SERVICE_DELETEANDSAVE_OPID),
                jsonNodes);
    }

    @Override
    public Object retrieveWithChildPagination(String dataId, JsonNode node) {
        ArrayList<JsonNode> jsonNodes = new ArrayList<>();
        jsonNodes.add(getJsonNodeIgnoreCase("requestInfo", node));
        jsonNodes.add(JacksonJsonUtil.getMapper().valueToTree(dataId));
        jsonNodes.add(getJsonNodeIgnoreCase("retrieveParam", node));
        return ExternalApiInvokeAgent.invokeByJsonNode(
                Constants.EXTERNAL_API_RESOURCE_TYPE_VO,
                this.getVoCode(),
                MessageFormat.format("{0}&^^&{1}", voId, BASE_SERVICE_RETRIEVE_WITH_CHILD_PAGINATION_OPID),
                jsonNodes);
    }

    @Override
    public Object retrieveChildByIndex(JsonNode node) {
        ArrayList<JsonNode> jsonNodes = new ArrayList<>();
        jsonNodes.add(getJsonNodeIgnoreCase("requestInfo", node));
        jsonNodes.add(getJsonNodeIgnoreCase("nodeCodes", node));
        jsonNodes.add(getJsonNodeIgnoreCase("ids", node));
        jsonNodes.add(getJsonNodeIgnoreCase("pagination", node));
        return ExternalApiInvokeAgent.invokeByJsonNode(
                Constants.EXTERNAL_API_RESOURCE_TYPE_VO,
                this.getVoCode(),
                MessageFormat.format("{0}&^^&{1}", voId, BASE_SERVICE_RETRIEVE_CHILD_BY_INDEX_OPID),
                jsonNodes);
    }

    @Override
    public Object extensionFilter(JsonNode node) {
        ArrayList<JsonNode> jsonNodes = new ArrayList<>();
        jsonNodes.add(getJsonNodeIgnoreCase("requestInfo", node));
        jsonNodes.add(getJsonNodeIgnoreCase("entityFilter", node));
        return ExternalApiInvokeAgent.invokeByJsonNode(
                Constants.EXTERNAL_API_RESOURCE_TYPE_VO,
                this.getVoCode(),
                MessageFormat.format("{0}&^^&{1}", voId, BASE_SERVICE_EXTENSION_FILTER_OPID),
                jsonNodes);
    }


    /**
     * 参数忽略大小写处理
     *
     * @param fieldName
     * @param node
     * @return
     */
    public JsonNode getJsonNodeIgnoreCase(String fieldName, JsonNode node) {
        if (Objects.isNull(fieldName) || Objects.isNull(node)) {
            return null;
        }
        Iterator<String> fieldNames = node.fieldNames();
        if (fieldNames == null || node.size() <= 0) {
            return null;
        }
        List<String> list = convertIteratorToList(fieldNames).stream()
                .filter(x -> x.trim().equalsIgnoreCase(fieldName.trim()))
                .collect(Collectors.toList());
        return list.size() <= 0 ? null : node.get(list.get(0));

    }

    public static <T> List<T> convertIteratorToList(Iterator<T> iter) {
        List<T> copy = new ArrayList<T>();
        while (iter.hasNext()) {
            copy.add(iter.next());
        }
        return copy;
    }
}
