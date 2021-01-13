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

package org.openatom.ubml.model.externalapi.definition.temp.lcm;

import java.util.List;
import org.openatom.ubml.model.common.definition.entity.GspMetadata;

/**
 * The type MetadataService
 *
 * @author: haozhibei
 */
public interface MetadataService {
    List<GspMetadata> getMetadataList(String path, List<String> list);

    void deleteMetadata(String path, String s);

    GspMetadata initializeMetadataEntity(GspMetadata api);

    void createMetadata(String path, GspMetadata api);

    GspMetadata loadMetadata(String name, String path);

    void saveMetadata(GspMetadata api, String path);
}
