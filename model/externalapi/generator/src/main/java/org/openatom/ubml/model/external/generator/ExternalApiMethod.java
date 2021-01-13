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

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.openatom.ubml.common.util.StringUtils;
import org.openatom.ubml.model.externalapi.definition.entity.ExternalApi;
import org.openatom.ubml.model.externalapi.definition.entity.Operation;
import org.openatom.ubml.model.externalapi.definition.entity.Parameter;

/**
 * EapiMethod
 *
 * @Author: Fynn Qi
 * @Date: 2020/9/17 14:10
 * @Version: V1.0
 */
public class ExternalApiMethod {

    private static final String EAPI_PARAM_TYPE_OBJECT = "object";

    private static final String EAPI_POSITION_BODY = "Body";
    private static final String EAPI_POSITION_HEADER = "Header";
    private static final String EAPI_POSITION_PATH = "Path";
    private static final String EAPI_POSITION_QUERY = "Query";

    private static final String EAPI_SERVICE_INVOKER = "ServiceInvoker";

    private static final String EAPI_EXCEPTION = "Exception";
    private static final String EAPI_EXCEPTION_RUNTIMEEXCEPTION = "RuntimeException";

    private static final String EAPI_SERVICE_UTILS = "EapiServiceUtils";

    public static String getMethods(ExternalApi eapi) {
        StringBuilder sb = new StringBuilder();
        if (eapi == null
                || eapi.getService() == null
                || eapi.getService().getOperations() == null
                || eapi.getService().getOperations().size() <= 0) {
            return sb.toString();
        }
        List<Operation> operations = eapi.getService().getOperations();
        if (operations == null || operations.size() <= 0) {
            return sb.toString();
        }
        for (Operation operation : operations) {
            sb.append(getMethod(operation, eapi)).append(System.lineSeparator());
        }
        return sb.toString();
    }

    public static String getMethod(Operation operation, ExternalApi metadata) {
        StringBuilder sb = new StringBuilder();
        sb.append(getMethodAnnotation(operation));
        sb.append(
                MessageFormat.format(
                        "  public {0} {1}({2})'{'",
                        getReturnType(operation),
                        getCamelCode(operation.getCode()),
                        getInputParams(operation)))
                .append(System.lineSeparator());
        sb.append(getMethodBody(operation, metadata)).append(System.lineSeparator());
        sb.append("  }").append(System.lineSeparator());
        return sb.toString();
    }

    public static StringBuilder getMethodBody(Operation operation, ExternalApi metadata) {
        StringBuilder sb = new StringBuilder();

        sb.append("    List<JsonNode> jsonNodes=new ArrayList<JsonNode>();")
                .append(System.lineSeparator());
        sb.append("    ObjectMapper mapper = new ObjectMapper();").append(System.lineSeparator());
        sb.append("    try{").append(System.lineSeparator());
        sb.append(getJsonNodeParamList(operation));
        sb.append("    }").append(System.lineSeparator());

        sb.append(MessageFormat.format("    catch ({0} e) '{'", EAPI_EXCEPTION))
                .append(System.lineSeparator());
        sb.append(MessageFormat.format("      throw new {0}(e);", EAPI_EXCEPTION_RUNTIMEEXCEPTION))
                .append(System.lineSeparator());
        sb.append("    }").append(System.lineSeparator());
        sb.append(getReturn(operation, metadata));
        return sb;
    }

    private static StringBuilder getJsonNodeParamList(Operation operation) {
        StringBuilder sb = new StringBuilder();
        List<Parameter> parameters = operation.getParameters();
        if (parameters == null || parameters.size() <= 0) {
            return sb;
        } else {
            List<Parameter> bodyParams =
                    parameters.stream()
                            .filter(x -> EAPI_POSITION_BODY.equals(x.getLocation()))
                            .collect(Collectors.<Parameter>toList());
            int bodyPositionCont = bodyParams.size();
            Iterator<Parameter> it = parameters.iterator();
            while (it.hasNext()) {
                Parameter parameter = it.next();
                if (parameter.isReturnValue()) {
                    continue;
                }
                if (EAPI_POSITION_HEADER.equals(parameter.getLocation())
                        || EAPI_POSITION_PATH.equals(parameter.getLocation())
                        || EAPI_POSITION_QUERY.equals(parameter.getLocation())) {
                    getSimpleParam(sb, parameter);
                } else
                    if (EAPI_POSITION_BODY.equals(parameter.getLocation())) {
                        // Body位置只有一个参数，直接添加到JSONNodes列表中
                        if (bodyPositionCont <= 1) {
                            sb.append("      jsonNodes.add(node);");
                            sb.append(System.lineSeparator());
                        } else {
                            sb.append(
                                    MessageFormat.format(
                                            "      jsonNodes.add({0}.getJsonNodeIgnoreCase(\"{1}\",node));",
                                            EAPI_SERVICE_UTILS, parameter.getCode()));
                            sb.append(System.lineSeparator());
                            sb.append(System.lineSeparator());
                        }
                    }
            }
        }
        return sb;
    }

    private static void getSimpleParam(StringBuilder sb, Parameter parameter) {
        sb.append(
                MessageFormat.format("      jsonNodes.add(mapper.valueToTree({0}));", parameter.getCode()));
        sb.append(System.lineSeparator());
    }

    private static StringBuilder getReturn(Operation operation, ExternalApi metadata) {
        StringBuilder sb = new StringBuilder();
        String resourceType = metadata.getService().getResourceType();
        String resourceId = metadata.getService().getResourceId();
        String operationId = operation.getResourceOperationId();
        int count =
                operation.getParameters() == null
                        ? 0
                        : operation.getParameters().stream()
                        .filter(Parameter::isReturnValue)
                        .toArray()
                        .length;
        sb.append(
                MessageFormat.format(
                        "    {0}.invokeByJsonNode(\"{1}\",\"{2}\",\"{3}\",jsonNodes);",
                        count > 0 ? "return " + EAPI_SERVICE_INVOKER : EAPI_SERVICE_INVOKER,
                        resourceType,
                        resourceId,
                        operationId));
        return sb;
    }

    private static String getReturnType(Operation operation) {
        List<Parameter> parameters = operation.getParameters();
        if (Objects.isNull(parameters) || parameters.size() <= 0) {
            return "void";
        } else {
            int count = parameters.stream().filter(Parameter::isReturnValue).toArray().length;
            if (count > 0) {
                return "Object";
            } else {
                return "void";
            }
        }
    }

    private static String getInputParams(Operation operation) {
        StringBuilder sb = new StringBuilder();
        List<Parameter> parameters = operation.getParameters();
        if (Objects.nonNull(parameters) && parameters.size() > 0) {
            Iterator<Parameter> it = parameters.iterator();
            while (it.hasNext()) {
                Parameter parameter = it.next();
                if (parameter.isReturnValue()) {
                    continue;
                }
                switch (parameter.getLocation()) {
                    case "Header":
                        getHeaderParamByCondition(sb, parameter);
                        break;
                    case "Query":
                        getQueryParamByCondition(sb, parameter);
                        break;
                    case "Path":
                        getPathParamByCondition(sb, parameter);
                        break;
                    default:
                        // Body位置的参数单独处理
                        break;
                }
            }
            // 处理Body位置的参数
            getBodyParamByCondition(sb, parameters);
        }
        return sb.toString();
    }

    private static void getBodyParamByCondition(StringBuilder sb, List<Parameter> parameters) {
        int count =
                parameters.stream().filter(x -> EAPI_POSITION_BODY.equals(x.getLocation())).toArray().length;
        if (count > 0) {
            if (StringUtils.isNotBlank(sb.toString())) {
                // 逗号参数分割
                sb.append(",");
            }
            sb.append(getBodyParam());
        }
    }

    private static String getBodyParam() {
        return "JsonNode node";
    }

    private static void getPathParamByCondition(StringBuilder sb, Parameter parameter) {
        if (StringUtils.isNotBlank(sb.toString())) {
            // 逗号参数分割
            sb.append(",");
        }
        getPathParam(sb, parameter);
    }

    private static void getPathParam(StringBuilder sb, Parameter parameter) {
        sb.append(
                MessageFormat.format(
                        "@PathParam(\"{0}\") {1} {2} ",
                        parameter.getCode(), getParamType(parameter), parameter.getCode()));
    }

    private static void getQueryParamByCondition(StringBuilder sb, Parameter parameter) {
        if (StringUtils.isNotBlank(sb.toString())) {
            // 逗号参数分割
            sb.append(",");
        }
        getQueryParam(sb, parameter);
    }

    private static void getQueryParam(StringBuilder sb, Parameter parameter) {
        sb.append(
                MessageFormat.format(
                        "@QueryParam(\"{0}\") {1} {2} ",
                        parameter.getCode(), getParamType(parameter), parameter.getCode()));
    }

    private static void getHeaderParamByCondition(StringBuilder sb, Parameter parameter) {
        if (StringUtils.isNotBlank(sb.toString())) {
            // 逗号参数分割
            sb.append(",");
        }
        sb.append(getHeaderParam(parameter));
    }

    private static String getHeaderParam(Parameter parameter) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                MessageFormat.format(
                        "@HeaderParam(\"{0}\") {1} {2} ",
                        parameter.getCode(), getParamType(parameter), parameter.getCode()));
        return sb.toString();
    }

    private static String getParamType(Parameter parameter) {
        StringBuilder sb = new StringBuilder();
        // 基本类型
        if (parameter.isPrimitiveType()) {
            if (parameter.isCollection()) {
                sb.append(getListBasicType(parameter));
            } else {
                sb.append(getBasicType(parameter));
            }
        }
        // 复杂类型
        else {
            throw new RuntimeException("不支持复杂类型的Header参数");
        }
        return sb.toString();
    }

    private static StringBuilder getListBasicType(Parameter parameter) {
        StringBuilder sb = new StringBuilder();
        sb.append("String");
        getIteratorList(parameter.getCollectionDepth(), sb);
        return sb;
    }

    private static StringBuilder getIteratorList(int depth, StringBuilder sb) {
        if (depth > 0) {
            sb.append(MessageFormat.format("List<{0}>", sb.toString()));
            return getIteratorList(depth - 1, sb);
        } else {
            return sb;
        }
    }

    private static String getBasicType(Parameter parameter) {
        return "String";
    }

    private static String getCamelCode(String code) {
        return code.replaceFirst(code.substring(0, 1), code.substring(0, 1).toLowerCase());
    }

    private static String getMethodAnnotation(Operation operation) {
        StringBuilder sb = new StringBuilder();
        if (operation.isTransactional()) {
            sb.append(MessageFormat.format("  @GlobalTransactional(name=\"{0}\")", operation.getCode()))
                    .append(System.lineSeparator());
        }
        sb.append(getHttpMethod(operation));
        sb.append(getMethodTemplate(operation));
        Parameter retParam = getReturnParam(operation);
        if (Objects.nonNull(retParam)) {
            if (retParam.isPrimitiveType()
                    && !retParam.isCollection()
                    && retParam.getCollectionDepth() <= 0
                    && !EAPI_PARAM_TYPE_OBJECT.equals(retParam.getModelId())) {
                sb.append("  @Produces(MediaType.TEXT_PLAIN)").append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    private static Parameter getReturnParam(Operation operation) {
        List<Parameter> parameters = operation.getParameters();
        if (Objects.isNull(parameters) || parameters.size() <= 0) {
            return null;
        } else {
            List<Parameter> retParams =
                    parameters.stream().filter(Parameter::isReturnValue).collect(Collectors.toList());
            if (retParams.size() <= 0) {
                return null;
            } else {
                return retParams.get(0);
            }
        }
    }

    private static String getMethodTemplate(Operation operation) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(operation.getHttpPath())) {
            String template = operation.getHttpPath();
            // 处理旧版元数据操作Template
            if (template.contains("无")) {
                sb.append("  @Path(\"/\")");
            } else {
                if (template.startsWith("/")) {
                    sb.append(MessageFormat.format("  @Path(\"{0}\")", template));
                } else {
                    sb.append(MessageFormat.format("  @Path(\"/{0}\")", template));
                }
            }
        } else {
            sb.append("  @Path(\"/\")");
        }
        return sb.append(System.lineSeparator()).toString();
    }

    private static String getHttpMethod(Operation operation) {
        StringBuilder sb = new StringBuilder();
        switch (operation.getHttpMethod()) {
            case "GET":
                sb.append("  @GET");
                break;
            case "POST":
                sb.append("  @POST");
                break;
            case "PUT":
                sb.append("  @PUT");
                break;
            case "PATCH":
                sb.append("  @PATCH");
                break;
            case "DELETE":
                sb.append("  @DELETE");
                break;
            default:
                break;
        }
        return sb.append(System.lineSeparator()).toString();
    }
}
