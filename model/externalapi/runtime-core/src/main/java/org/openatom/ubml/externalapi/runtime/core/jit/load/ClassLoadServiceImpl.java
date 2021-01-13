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

import org.springframework.stereotype.Component;

/**
 * @author haozhibei
 */
@Component("org.openatom.ubml.externalapi.runtime.core.jit.load.ClassLoadServiceImpl")
public class ClassLoadServiceImpl implements ClassloaderService {
    @Override
    public Class load(String functionType, String functionId, String fullName) {
        return ClassLoaderManager.load(functionType, functionId, fullName);
    }

    @Override
    public void unload(String functionType, String functionId) {
        ClassLoaderManager.unLoad(functionType, functionId);
    }
}
