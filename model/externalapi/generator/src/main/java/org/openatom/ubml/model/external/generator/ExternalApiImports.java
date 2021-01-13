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

package org.openatom.ubml.model.external.generator;

/**
 * EapiImports
 *
 * @Author: Fynn Qi
 * @Date: 2020/9/16 16:24
 * @Version: V1.0
 */
public class ExternalApiImports {

    public static String getServiceImports() {
        StringBuilder sb = new StringBuilder();
        sb.append("import java.lang.RuntimeException;").append(System.lineSeparator());
        sb.append("import com.fasterxml.jackson.databind.ObjectMapper;").append(System.lineSeparator());
        sb.append("import java.lang.Exception;").append(System.lineSeparator());
        sb.append("import com.inspur.edp.sgf.api.annotation.EapiService;").append(System.lineSeparator());
        sb.append("import com.inspur.edp.sgf.api.service.ServiceInvoker;").append(System.lineSeparator());
        sb.append("import com.inspur.edp.sgf.api.utils.EapiServiceUtils;").append(System.lineSeparator());
        sb.append("import com.inspur.edp.caf.transaction.api.annoation.GlobalTransactional;").append(System.lineSeparator());
        sb.append("import java.util.List;").append(System.lineSeparator());
        sb.append("import com.fasterxml.jackson.databind.JsonNode;").append(System.lineSeparator());
        sb.append("import javax.ws.rs.*;").append(System.lineSeparator());
        sb.append("import javax.ws.rs.core.MediaType;").append(System.lineSeparator());
        sb.append("import java.util.ArrayList;").append(System.lineSeparator());
        sb.append("import java.lang.String;").append(System.lineSeparator());
        sb.append("import java.lang.Object;").append(System.lineSeparator());
        return sb.toString();
    }

    public static String getConfigImports() {
        StringBuilder sb = new StringBuilder();
        sb.append("import org.springframework.context.annotation.Configuration;").append(System.lineSeparator());
        sb.append("import org.springframework.context.annotation.Bean;").append(System.lineSeparator());
        sb.append("import org.springframework.context.annotation.Lazy;").append(System.lineSeparator());
        sb.append("import io.iec.edp.caf.rest.RESTServiceRegistrar;").append(System.lineSeparator());
        sb.append("import io.iec.edp.caf.rest.RESTEndpoint;").append(System.lineSeparator());
        return sb.toString();
    }


}
