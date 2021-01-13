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

public class I18nResource {

    /**
     * 语言
     */
    private String language;

    /**
     * 资源类型
     */
    private ResourceType resourceType;

    /**
     * 资源位置
     */
    private ResourceLocation resourceLocation;

    /**
     * 资源包内容
     */
    private I18nResourceItemCollection stringResources;

    /**
     * 资源包内容
     */
    private I18nResourceItemCollection imageResources;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    public void setResourceLocation(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public I18nResourceItemCollection getStringResources() {
        return stringResources;
    }

    public void setStringResources(I18nResourceItemCollection stringResources) {
        this.stringResources = stringResources;
    }

    public I18nResourceItemCollection getImageResources() {
        return imageResources;
    }

    public void setImageResources(I18nResourceItemCollection imageResources) {
        this.imageResources = imageResources;
    }

}
