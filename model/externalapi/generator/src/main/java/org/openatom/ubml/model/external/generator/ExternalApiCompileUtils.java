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

/**
 * EapiCompileUtils
 *
 * @Author: Fynn Qi
 * @Date: 2020/9/18 9:40
 * @Version: V1.0
 */
public class ExternalApiCompileUtils {

    public static String upperFirstChar(String code) {
        return code.replaceFirst(code.substring(0, 1), code.substring(0, 1).toUpperCase());
    }

    public static String getCamelCode(String code) {
        return code.replaceFirst(code.substring(0, 1), code.substring(0, 1).toLowerCase());
    }
}
