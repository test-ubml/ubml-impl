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

package org.openatom.ubml.externalapi.runtime.core.jit.load;


/**
 * The ClassLoaderService
 *
 * @author haozhibei
 */
public interface ClassloaderService {
    /**
     * 加载指定功能包中的类
     *
     * @param functionType  功能包类型
     * @param functionId    功能包Id
     * @param classFullName 类全名
     * @return 类对象
     */
    Class load(String functionType, String functionId, String classFullName);

    /**
     * 卸载功能包的类加载器
     *
     * @param functionType 功能包类型
     * @param functionId   功能包Id
     */
    void unload(String functionType, String functionId);
}
