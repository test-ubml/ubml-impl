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
 * EapiPackage
 *
 * @Author: Fynn Qi
 * @Date: 2020/9/16 18:31
 * @Version: V1.0
 */
public class ExternalApiPackage {

    public static String getPackage(String packageName) {
        //换行并空格
        return String.format("package %s;", packageName) + System.lineSeparator();
    }
}
