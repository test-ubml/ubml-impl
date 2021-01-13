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

package org.openatom.ubml.model.externalapi.definition.entity;

import java.text.MessageFormat;
import java.util.ArrayList;
import org.openatom.ubml.common.util.JacksonJsonUtil;
import org.openatom.ubml.model.common.definition.entity.AbstractMetadataContent;

/**
 * The Definition of ExternalApi.
 *
 * @author haozhibei
 */
public class ExternalApi extends AbstractMetadataContent {

    private String id;

    private String version;

    private String description;

    private String businessObject;

    private String application;

    private String microServiceUnit;

    private String baseHttpPath;

    private Service service;

    private ArrayList<Model> models;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBusinessObject() {
        return businessObject;
    }

    public void setBusinessObject(String businessObject) {
        this.businessObject = businessObject;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getMicroServiceUnit() {
        return microServiceUnit;
    }

    public void setMicroServiceUnit(String microServiceUnit) {
        this.microServiceUnit = microServiceUnit;
    }

    public String getBaseHttpPath() {
        return baseHttpPath;
    }

    public void setBaseHttpPath(String baseHttpPath) {
        this.baseHttpPath = baseHttpPath;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public ArrayList<Model> getModels() {
        return models;
    }

    public void setModels(ArrayList<Model> models) {
        this.models = models;
    }

    public ExternalApi() {
        this.service = new Service();
        this.models = new ArrayList<>();
    }

    public String getRouter() {
        String router = MessageFormat.format(
                "/api/{0}/{1}/v{2}/{3}",
                this.getApplication(),
                this.getMicroServiceUnit(),
                this.getVersion(),
                this.getBaseHttpPath());
        return router.toLowerCase();
    }

    public String getEndPoint() {
        String endPoint = MessageFormat.format(
                "/{0}/{1}/v{2}/{3}",
                this.getApplication(),
                this.getMicroServiceUnit(),
                this.getVersion(),
                this.getBaseHttpPath());
        return endPoint.toLowerCase();
    }

    @Override
    public Object clone() {
        return JacksonJsonUtil.toObject(JacksonJsonUtil.toJson(this), ExternalApi.class);
    }
}
