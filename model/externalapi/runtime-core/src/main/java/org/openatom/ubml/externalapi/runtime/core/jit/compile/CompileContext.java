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

package org.openatom.ubml.externalapi.runtime.core.jit.compile;

import java.util.List;

/**
 * The Compile Context
 *
 * @author haozhibei
 */
public class CompileContext {

    private String functionId;

    private String functionType;

    private List<JavaSourceCode> sourceCodes;

    private List<String> referBoots;

    private List<String> refers;

    private String version;

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getFunctionType() {
        return functionType;
    }

    public void setFunctionType(String functionType) {
        this.functionType = functionType;
    }

    public List<JavaSourceCode> getSourceCodes() {
        return sourceCodes;
    }

    public void setSourceCodes(List<JavaSourceCode> sourceCodes) {
        this.sourceCodes = sourceCodes;
    }

    public List<String> getReferBoots() {
        return referBoots;
    }

    public void setReferBoots(List<String> referBoots) {
        this.referBoots = referBoots;
    }

    public List<String> getRefers() {
        return refers;
    }

    public void setRefers(List<String> refers) {
        this.refers = refers;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
