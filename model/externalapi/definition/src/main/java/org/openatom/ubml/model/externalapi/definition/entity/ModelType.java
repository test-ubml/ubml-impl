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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openatom.ubml.common.enums.EnumValueParsable;

/**
 * The modelType.
 *
 * @author haozhibei
 */
public enum ModelType implements EnumValueParsable<Integer> {

    VO(0),

    DTO(1);

    private int value;

    ModelType(int enumValue) {
        this.value = enumValue;
    }

    @JsonValue
    @Override
    public Integer getValue() {
        return value;
    }

    @JsonCreator
    public static ModelType of(Integer value) throws EnumValueParseException {
        return EnumValueParsable.parse(value, ModelType.class);
    }
}
