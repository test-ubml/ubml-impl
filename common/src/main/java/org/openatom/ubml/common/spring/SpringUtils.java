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

import java.lang.annotation.Annotation;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

/**
 * 提供运行时操作Spring容器的能力
 *
 * @author Shawn Hou
 */
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext appContext;
    private static ConfigurableListableBeanFactory beanFactory;

    public static <A extends Annotation> void scanAndRegisterBeanWithAnnotation(@NonNull Class<A> annotationClazz, @NonNull String... basePackages) {
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry)appContext;
        AnnotationScanner scanner = new AnnotationScanner(registry, annotationClazz);
        scanner.setResourceLoader(appContext);
        int count = scanner.scan(basePackages);
    }

    public static Object getBean(String name) {
        return appContext != null ? appContext.getBean(name) : null;
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return appContext != null ? appContext.getBean(name, clazz) : null;
    }

    public static <T> T getBean(Class<T> clazz) {
        return appContext != null ? appContext.getBean(clazz) : null;
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return appContext.getBeansOfType(clazz);
    }

    public static Map<String, Object> getBeansWithAnnotation(
            Class<? extends Annotation> annotationClazz) {
        return appContext.getBeansWithAnnotation(annotationClazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return appContext;
    }
}
