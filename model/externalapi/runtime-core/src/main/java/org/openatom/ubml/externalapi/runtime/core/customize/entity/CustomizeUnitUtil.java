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

package org.openatom.ubml.externalapi.runtime.core.customize.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openatom.ubml.externalapi.runtime.core.jit.compile.JavaSourceCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomizeUnitUtil {

    private static Logger logger = LoggerFactory.getLogger(CustomizeUnit.class);

    /**
     * 检查重复的类和Endpoint，并移除重复的CustomizeUnit
     *
     * @param unitMap
     */
    public static void checkAndFilterRepeatedUnit(Map<String, CustomizeUnit> unitMap) {
        //检查重复的类
        HashMap<String, String> classNameIdMap = new HashMap<>();
        List<String> ids = new ArrayList<>(unitMap.keySet());
        for (String id : ids) {
            CustomizeUnit unit = unitMap.get(id);
            List<JavaSourceCode> javaSources = unit.getAllJavaClassSources();
            for (JavaSourceCode javaClassSource : javaSources) {
                String clzName = javaClassSource.getFullClassName();
                if (classNameIdMap.containsKey(clzName)) {
                    logger.error("Eapi【{}】与【{}】中类名称重复【{}】，请检查", id, classNameIdMap.get(clzName), clzName);
                    unitMap.remove(id);
                    break;
                } else {
                    classNameIdMap.put(javaClassSource.getFullClassName(), id);
                }
            }
        }

        //检查重复的Endpoint
        HashMap<String, String> endpointIdMap = new HashMap<>();
        ids = new ArrayList<>(unitMap.keySet());
        for (String id : ids) {
            CustomizeUnit unit = unitMap.get(id);
            if (endpointIdMap.containsKey(unit.getEndpointAddress())) {
                logger.error("Eapi【{}】与【{}】中EndpointAddress重复【{}】，请检查", id, endpointIdMap.get(unit.getEndpointAddress()), unit.getEndpointAddress());
                unitMap.remove(id);
            } else {
                endpointIdMap.put(unit.getEndpointAddress(), id);
            }
        }
    }
}
