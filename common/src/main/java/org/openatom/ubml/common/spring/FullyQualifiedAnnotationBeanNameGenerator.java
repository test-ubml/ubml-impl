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

package org.openatom.ubml.common.spring;

import java.beans.Introspector;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * bean全限定名称命名生成器，注解声明注册的bean，自动命名注册策略将使用类的全限定名称（类名首字母小写）作为bean的名字。
 *
 * <p>使用方法: 在@ComponentScan注解上设置: {@code
 * nameGenerator=FullyQualifiedAnnotationBeanNameGenerator.class}
 *
 * @author shawn hou
 */
public class FullyQualifiedAnnotationBeanNameGenerator extends AnnotationBeanNameGenerator {

    /**
     * Derive a default bean name from the given bean definition.
     *
     * <p>The default implementation simply builds a decapitalized version of the fully qualified
     * class name: e.g. "mypackage.MyJdbcDao" -> "mypackage.myJdbcDao".
     *
     * <p>Note that inner classes will thus have names of the form "outerClassName.InnerClassName",
     * which because of the period in the name may be an issue if you are autowiring by name.
     *
     * @param definition the bean definition to build a bean name for
     * @return the default bean name (never {@code null})
     */
    @Override
    protected String buildDefaultBeanName(BeanDefinition definition) {
        String beanClassName = definition.getBeanClassName();
        Assert.state(beanClassName != null, "No bean class name set");
        String packageName = ClassUtils.getPackageName(beanClassName);
        String shortName = ClassUtils.getShortName(beanClassName);
        String[] sNames = shortName.split("\\.");
        StringBuilder sb = new StringBuilder();
        sb.append(packageName);
        int i = 0;
        for (; i < sNames.length - 1; i++) {
            sb.append('.').append(sNames[i]);
        }
        sb.append('.').append(Introspector.decapitalize(sNames[i]));
        return sb.toString();
    }
}
