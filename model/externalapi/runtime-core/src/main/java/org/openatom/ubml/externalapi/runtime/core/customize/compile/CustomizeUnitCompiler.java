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

package org.openatom.ubml.externalapi.runtime.core.customize.compile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.openatom.ubml.common.spring.SpringUtils;
import org.openatom.ubml.externalapi.runtime.core.customize.entity.CustomizeUnit;
import org.openatom.ubml.externalapi.runtime.core.jit.compile.CompileContext;
import org.openatom.ubml.externalapi.runtime.core.jit.compile.CompileService;

/**
 * CustomizeUnit编译器，调用JIT编译器实现
 */
public class CustomizeUnitCompiler {
    private static CustomizeUnitCompiler instance = new CustomizeUnitCompiler();
    private CompileService compileService;
    private List<String> referBoots;
    private List<String> refers;

    private CustomizeUnitCompiler() {
        initRequiredJars();
    }

    /**
     * 获取动态编译引用的ClassPath Jar包路径。经测试，最小引用为：
     * <ol>
     *     <li>jstack\platform\common\libs\cdp-sgf-base.jar</li>
     *     <li>jstack\platform\common\libs\cdp-sgf-api.jar</li>
     *     <li>jstack\runtime\libs\caf-transaction-api.jar</li>
     *     <li>caf-bootstrap_jar\BOOT-INF\lib\jackson-databind-2.10.0.jar</li>
     *     <li>caf-bootstrap_jar\BOOT-INF\lib\jackson-core-2.10.0.jar</li>
     *     <li>caf-bootstrap_jar\BOOT-INF\lib\jakarta.ws.rs-api-2.1.5.jar</li>
     *     <li>caf-bootstrap_jar\BOOT-INF\lib\lombok-1.18.10.jar</li>
     * </ol>
     *
     * @return
     */
    private void initRequiredJars() {
        referBoots = new ArrayList<>();
        referBoots.add("jackson-databind");
        referBoots.add("jackson-core");
        referBoots.add("jakarta.ws.rs-api");

        refers = new ArrayList<>();
        refers.add("platform" + File.separator + "common" + File.separator + "libs" + File.separator + "cdp-sgf-base.jar");
        refers.add("platform" + File.separator + "common" + File.separator + "libs" + File.separator + "cdp-sgf-api.jar");
        refers.add("runtime" + File.separator + "libs" + File.separator + "caf-transaction-api.jar");
    }

    public static CustomizeUnitCompiler getInstance() {
        return instance;
    }

    public void compile(CustomizeUnit unit) {
        CompileContext cc = buildCompileContext(unit);
        getCompileService().compile(cc);
    }

    public void batCompile(List<CustomizeUnit> units) {
        List<CompileContext> contexts = units.stream().map(this::buildCompileContext).collect(Collectors.toList());
        getCompileService().batchCompile(contexts);
    }

    private CompileContext buildCompileContext(CustomizeUnit unit) {
        CompileContext cc = new CompileContext();
        cc.setFunctionType("eapi");
        cc.setFunctionId(unit.getId());
        cc.setReferBoots(referBoots);
        cc.setRefers(refers);
        cc.setSourceCodes(unit.getAllJavaClassSources());
        return cc;
    }

    private CompileService getCompileService() {
        if (compileService == null) {
            compileService = SpringUtils.getBean(CompileService.class);
        }
        return compileService;
    }
}
