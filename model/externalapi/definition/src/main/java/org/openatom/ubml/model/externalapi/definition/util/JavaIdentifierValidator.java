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

package org.openatom.ubml.model.externalapi.definition.util;

/**
 * Validator fo java code
 *
 * @author haozhibei
 */
public class JavaIdentifierValidator {

    private final static String JAVA_IDENTIFIER_EMPTY_STRING = "";

    /**
     * 对单独的name进行校验
     *
     * @param name
     * @return
     */
    public static boolean isValidJavaIdentifier(String name) {
        // 确定是否允许将指定字符作为 Java 标识符中的首字符。
        if (name.length() == 0 || !Character.isJavaIdentifierStart(name.charAt(0))) {
            return false;
        }

        String nameExceptFirst = name.substring(1);
        // 确定指定字符是否可以是 Java 标识符中首字符以外的部分。
        for (int i = 0; i < nameExceptFirst.length(); i++) {
            if (!Character.isJavaIdentifierPart(nameExceptFirst.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 对 package name 和 class name 进行校验
     *
     * @param javaName
     * @return
     */
    public static boolean isValidJavaIdentifierName(String javaName) {
        if (JAVA_IDENTIFIER_EMPTY_STRING.equals(javaName)) {
            return false;
        }
        boolean flag = true;
        try {
            if (!javaName.endsWith(".")) {
                int index = javaName.indexOf(".");
                if (index != -1) {
                    String[] splitNames = javaName.split("\\.");
                    for (String splitName : splitNames) {
                        if (JAVA_IDENTIFIER_EMPTY_STRING.equals(splitName)) {
                            flag = false;
                            break;
                        } else
                            if (!isValidJavaIdentifier(splitName)) {
                                flag = false;
                                break;
                            }
                    }
                } else
                    if (!isValidJavaIdentifier(javaName)) {
                        flag = false;
                    }

            } else {
                flag = false;
            }

        } catch (Exception ex) {
            flag = false;
            ex.printStackTrace();
        }
        return flag;
    }
}