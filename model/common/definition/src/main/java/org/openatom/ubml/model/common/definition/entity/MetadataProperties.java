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

package org.openatom.ubml.model.common.definition.entity;

/**
 * The type MetadataProperties
 *
 * @author: Jack Lee
 */
public class MetadataProperties {
    /**
     * metadata schema version
     */
    private String schemaVersion;

    public void setSchemaVersion(String schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public String getSchemaVersion() {
        return schemaVersion;
    }

    @Override
    public Object clone() {
        MetadataProperties properties = new MetadataProperties();
        properties.setSchemaVersion(this.schemaVersion);
        return properties;
    }

}
