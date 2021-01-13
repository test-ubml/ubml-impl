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

package org.openatom.ubml.externalapi.runtime.core.jit.query;

import java.util.List;

/**
 * 动态类管理API
 *
 * @author haozhibei
 */
public interface QueryService {
    /**
     * 获取指定类型所有已经编译的功能概要信息
     *
     * @param functionType 功能包类型
     * @return 已编译的功能包概要信息
     */
    List<FunctionSummaryInfo> query(String functionType);

    /**
     * 获取已经编译的功能包概要信息
     *
     * @param functionType 功能包类型
     * @param functionId   功能包Id
     * @return 已编译的功能包概要信息
     */
    FunctionSummaryInfo query(String functionType, String functionId);

    /**
     * 删除指定类型、指定Id的功能包
     *
     * @param functionType 删除的功能包类型
     * @param functionId   删除的功能包Id
     */
    void delete(String functionType, String functionId);

    /**
     * 删除指定类型的所有的功能包
     *
     * @param functionType 删除的功能包类型
     */
    void delete(String functionType);

    /**
     * 批量删除指定类型、指定Id的功能包
     *
     * @param functionType 删除的功能包类型
     * @param functionIds  删除的功能包Id列表
     */
    void batchDelete(String functionType, List<String> functionIds);

}
