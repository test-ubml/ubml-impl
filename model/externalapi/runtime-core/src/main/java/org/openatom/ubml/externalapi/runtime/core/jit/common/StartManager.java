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

package org.openatom.ubml.externalapi.runtime.core.jit.common;

import java.util.Map;
import org.openatom.ubml.externalapi.runtime.core.jit.extension.StartEvent;
import org.openatom.ubml.externalapi.runtime.core.jit.extension.StartEventArgs;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * The startManager
 *
 * @author haozhibei
 */
@Component("jitInitManager")
public class StartManager implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationStartedEvent) {
            String jitBaseDirectory = DirectoryManager.getJitBaseDirectory();
            DirectoryManager.createDir(jitBaseDirectory);
            String bootLibsDirectory = DirectoryManager.getUnZipBootJarDirectory();
            DirectoryManager.createDir(bootLibsDirectory);
            ApplicationContext applicationContext = ((ApplicationStartedEvent)event).getApplicationContext();
            Map<String, StartEvent> eventMap = applicationContext.getBeansOfType(StartEvent.class);
            if (eventMap != null) {
                for (Map.Entry<String, StartEvent> entry : eventMap.entrySet()) {
                    entry.getValue().fire(new StartEventArgs());
                }
            }
        }
    }
}
