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

package org.openatom.ubml.common.util;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.text.MessageFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Json工具类
 *
 * @author Shawn Hou
 */
public class JacksonJsonUtil {
    private static final Logger LOG = LoggerFactory.getLogger(JacksonJsonUtil.class);
    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        // 忽略不识别的字段
        MAPPER.configure(Feature.IGNORE_UNKNOWN, true);
        LOG.info(
                "MapperFeature {}: {}. Unknown properties will be quietly ignored.",
                Feature.IGNORE_UNKNOWN,
                true);

        // 允许没有引号包裹的字段
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        LOG.info(
                "JsonParser.Feature {}: {}. Allow use of unquoted field names.",
                JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,
                true);

        // 当遇到不能识别的属性时，正常执行
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        LOG.info(
                "MapperFeature {}: {}. It will not result in a failure if an unknown property is encountered.",
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                false);
        // 忽略字段大小写
        MAPPER.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        LOG.info(
                "MapperFeature {}: {}. The bean properties will be matched using their lower-case equivalents, any case-combination should work.",
                MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES,
                true);
        // 允许JSON字符串包含非JSON标准控制字符
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        LOG.info(
                "JsonParser.Feature {}: {}. allow JSON Strings to contain unquoted control characters (ASCII characters with value less than 32, including tab and line feed characters) or not.",
                JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS,
                true);
    }

    private JacksonJsonUtil() {
    }

    public static String toJson(Object object) throws JsonParseException {
        try {
            if (object == null) {
                return "";
            }
            return MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw new JsonParseException(
                    MessageFormat.format(
                            "Failed to serialize the object {0} which belong to class {1}.",
                            object, object.getClass().getCanonicalName()),
                    e);
        }
    }

    public static <T> T toObject(String json, Class<T> clazz) throws JsonParseException {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            throw new JsonParseException(
                    MessageFormat.format(
                            "Failed to deserialize from json string to class object {}.",
                            clazz.getCanonicalName()),
                    e);
        }
    }

    public static <T> T toObject(JsonNode jsonNode, Class<T> clazz) throws JsonParseException {
        try {
            return MAPPER.treeToValue(jsonNode, clazz);
        } catch (Exception e) {
            throw new JsonParseException(
                    MessageFormat.format(
                            "Failed to parse JsonNode to class object {0}.", clazz.getCanonicalName()),
                    e);
        }
    }

    public static <T> T toObject(InputStream in, Class<T> clazz) throws JsonParseException {
        try {
            return MAPPER.readValue(in, clazz);
        } catch (Exception e) {
            LOG.error(
                    "Failed to deserialize from input stream to class object {0}.",
                    clazz.getCanonicalName(),
                    e);
            throw new JsonParseException(
                    MessageFormat.format(
                            "Failed to deserialize from input stream to class object {0}.",
                            clazz.getCanonicalName()),
                    e);
        }
    }

    public static JsonNode toJsonNode(String json) throws JsonParseException {
        try {
            return MAPPER.readTree(json);
        } catch (Exception e) {
            throw new JsonParseException(
                    MessageFormat.format("Failed to read json tree from string \n{0}\n", json), e);
        }
    }

    public static JsonNode toJsonNode(InputStream in) {
        try {
            return MAPPER.readTree(in);
        } catch (Exception e) {
            throw new JsonParseException("Failed to read json tree from input stream.", e);
        }
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }

    public static class JsonParseException extends RuntimeException {

        private static final long serialVersionUID = -1860342689755875479L;

        public JsonParseException() {
            super("An error occurred when serialize or deserialize json string.");
        }

        public JsonParseException(String message) {
            super(message);
        }

        public JsonParseException(String message, Throwable cause) {
            super(message, cause);
        }

        public JsonParseException(Throwable cause) {
            super(cause);
        }

        public JsonParseException(
                String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}
