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

package org.openatom.ubml.model.external.generator.dt;

import java.util.ArrayList;
import java.util.List;
import org.openatom.ubml.common.spring.SpringUtils;
import org.openatom.ubml.model.externalapi.definition.temp.lcm.AfterGeneratorAction;
import org.openatom.ubml.model.externalapi.definition.temp.lcm.GenerateService;
import org.openatom.ubml.model.externalapi.definition.temp.lcm.MavenDependency;

/**
 * 功能描述:
 *
 * @ClassName: AfterGenerator
 * @Author: Fynn Qi
 * @Date: 2020/3/6 15:38
 * @Version: V1.0
 */
public class AfterGenerateHook implements AfterGeneratorAction {

    @Override
    public void afterGenerator(String projPath) {
        GenerateService generateService = SpringUtils.getBean(GenerateService.class);
        List<MavenDependency> dependencies = new ArrayList<>();

        MavenDependency apiDependency = new MavenDependency();
        apiDependency.setGroupId("com.inspur.edp");
        apiDependency.setArtifactId("cdp-sgf-api");
        dependencies.add(apiDependency);

        MavenDependency baseDependency = new MavenDependency();
        baseDependency.setGroupId("com.inspur.edp");
        baseDependency.setArtifactId("cdp-sgf-base");
        dependencies.add(baseDependency);

        MavenDependency transactionalDependency = new MavenDependency();
        transactionalDependency.setGroupId("com.inspur.edp");
        transactionalDependency.setArtifactId("caf-transaction-api");
        dependencies.add(transactionalDependency);

        generateService.modifyPom(projPath, dependencies);
    }
}
