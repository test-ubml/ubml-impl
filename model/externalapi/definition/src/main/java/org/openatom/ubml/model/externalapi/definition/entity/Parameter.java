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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Parameter in EApi.
 */
public class Parameter {

    private String id;

    private String code;

    private String name;

    private String description;

    private boolean collection;

    private boolean returnValue;

    private boolean primitiveType;

    private String primitiveTypeKind;

    @JsonProperty("$ref")
    private String structuredTypeRefId;

    private String location;

    private Boolean required;

    private Integer collectionDepth;

    public String getPrimitiveTypeKind() {
        return primitiveTypeKind;
    }

    public void setPrimitiveTypeKind(String primitiveTypeKind) {
        this.primitiveTypeKind = primitiveTypeKind;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCollection() {
        return collection;
    }

    public void setCollection(boolean collection) {
        this.collection = collection;
    }

    public boolean isReturnValue() {
        return returnValue;
    }

    public void setReturnValue(boolean returnValue) {
        this.returnValue = returnValue;
    }

    public boolean isPrimitiveType() {
        return primitiveType;
    }

    public void setPrimitiveType(boolean primitiveType) {
        this.primitiveType = primitiveType;
    }

    public String getStructuredTypeRefId() {
        return structuredTypeRefId;
    }

    public void setStructuredTypeRefId(String structuredTypeRefId) {
        this.structuredTypeRefId = structuredTypeRefId;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Integer getCollectionDepth() {
        return collectionDepth;
    }

    public void setCollectionDepth(Integer collectionDepth) {
        this.collectionDepth = collectionDepth;
    }
}
