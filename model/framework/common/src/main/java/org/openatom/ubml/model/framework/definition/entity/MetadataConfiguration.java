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

package org.openatom.ubml.model.framework.definition.entity;

import java.io.Serializable;

/**
 * @Classname MetadataConfiguration
 * @Description metadataConfiguration实体
 * @Date 2019/7/22 13:39
 * @Created by zhongchq
 * @Version 1.0
 */
public class MetadataConfiguration implements Serializable {

    private CommonConfigData common;

    private SerializerConfigData serializer;

    private TransferSerializerConfigData transferSerializer;

    private ManagerConfigData manager;

    private ReferenceConfigData reference;

    private I18nConfigData i18nConfigData;

    private MdExtRuleSerializerConfigData mdExtRuleSerializer;

    /**
     * @author zhongchq
     * @description 过滤器
     * @date 9:28 2019/7/24
     **/
    private FilterConfigData filter;

    public CommonConfigData getCommon() {
        return common;
    }

    public void setCommon(CommonConfigData common) {
        this.common = common;
    }

    public SerializerConfigData getSerializer() {
        return serializer;
    }

    public void setSerializer(SerializerConfigData serializer) {
        this.serializer = serializer;
    }

    public TransferSerializerConfigData getTransferSerializer() {
        return transferSerializer;
    }

    public void setTransferSerializer(
        TransferSerializerConfigData transferSerializer) {
        this.transferSerializer = transferSerializer;
    }

    public ManagerConfigData getManager() {
        return manager;
    }

    public void setManager(ManagerConfigData manager) {
        this.manager = manager;
    }

    public ReferenceConfigData getReference() {
        return reference;
    }

    public void setReference(ReferenceConfigData reference) {
        this.reference = reference;
    }

    public I18nConfigData getI18nConfigData() {
        return i18nConfigData;
    }

    public void setI18nConfigData(I18nConfigData i18nConfigData) {
        this.i18nConfigData = i18nConfigData;
    }

    public MdExtRuleSerializerConfigData getMdExtRuleSerializer() {
        return mdExtRuleSerializer;
    }

    public void setMdExtRuleSerializer(
        MdExtRuleSerializerConfigData mdExtRuleSerializer) {
        this.mdExtRuleSerializer = mdExtRuleSerializer;
    }

    public FilterConfigData getFilter() {
        return filter;
    }

    public void setFilter(FilterConfigData filter) {
        this.filter = filter;
    }

}
