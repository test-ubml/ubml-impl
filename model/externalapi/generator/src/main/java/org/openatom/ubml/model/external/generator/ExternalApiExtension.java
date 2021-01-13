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
import org.openatom.ubml.common.util.StringUtils;
import org.openatom.ubml.model.externalapi.definition.Constants;
import org.openatom.ubml.model.externalapi.definition.entity.ExternalApi;

/**
 * EapiExtension
 *
 * @Author: Fynn Qi
 * @Date: 2020/9/17 14:10
 * @Version: V1.0
 */
public class ExternalApiExtension {

    public static boolean hasBaseClass(ExternalApi eapi) {
        switch (getResourceType(eapi)) {
            case Constants.EXTERNAL_API_RESOURCE_TYPE_VO:
            case Constants.EXTERNAL_API_RESOURCE_TYPE_VO_SIMPLE:
                return true;
            default:
                return false;
        }
    }

    public static boolean hasProperties(ExternalApi eapi) {
        switch (getResourceType(eapi)) {
            case Constants.EXTERNAL_API_RESOURCE_TYPE_VO:
            case Constants.EXTERNAL_API_RESOURCE_TYPE_VO_SIMPLE:
                return true;
            default:
                return false;
        }
    }

    public static String getBaseClass(ExternalApi eapi) {
        StringBuilder sb = new StringBuilder();
        switch (getResourceType(eapi)) {
            case Constants.EXTERNAL_API_RESOURCE_TYPE_VO:
                sb.append("com.inspur.edp.sgf.base.VoBaseServiceImpl");
                break;
            case Constants.EXTERNAL_API_RESOURCE_TYPE_VO_SIMPLE:
                sb.append("com.inspur.edp.sgf.base.simple.SimpleVoBaseServiceImpl");
                break;
            default:
                break;
        }
        return sb.toString();
    }

    public static String getProperties(ExternalApi eapi) {

        StringBuilder sb = new StringBuilder();
        switch (getResourceType(eapi)) {
            case Constants.EXTERNAL_API_RESOURCE_TYPE_VO:
            case Constants.EXTERNAL_API_RESOURCE_TYPE_VO_SIMPLE:
                sb.append(getVoProperties(eapi));
                break;
            default:
                break;
        }
        return sb.toString();
    }

    private static String getVoProperties(ExternalApi metadata) {
        StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator());
        sb.append(MessageFormat.format("  private String voId = \"{0}\";", metadata.getId()))
                .append(System.lineSeparator());
        // 注意：VO的ConfigID用基类的voCode属性存储,eapi中resourceId字段必须在调用该方法前就得将Eapi中对应的字段值更新成最新的ConfigId
        sb.append(
                MessageFormat.format(
                        "  private String voCode = \"{0}\";", metadata.getService().getResourceId()))
                .append(System.lineSeparator());

        sb.append("  @Override").append(System.lineSeparator());
        sb.append("  public String getVoId() {return voId;}").append(System.lineSeparator()).append(System.lineSeparator());

        sb.append("  @Override").append(System.lineSeparator());
        sb.append("  public void setVoId(String voId) {this.voId = voId;}").append(System.lineSeparator()).append(System.lineSeparator());

        sb.append("  @Override").append(System.lineSeparator());
        sb.append("  public String getVoCode() {return voCode;}").append(System.lineSeparator()).append(System.lineSeparator());

        sb.append("  @Override").append(System.lineSeparator());
        sb.append("  public void setVoCode(String voCode) {this.voCode = voCode;}").append(System.lineSeparator());

        return sb.toString();
    }

    private static String getResourceType(ExternalApi eapi) {
        String resourceType = eapi.getService().getResourceType();
        if (StringUtils.isBlank(resourceType)) {
            throw new RuntimeException(
                    String.format(
                            "外部服务元数据【%s】(ID:%s)的资源类型为空，无法生成外部服务代理类代码", eapi.getCode(), eapi.getClass()));
        }
        return resourceType;
    }
}
