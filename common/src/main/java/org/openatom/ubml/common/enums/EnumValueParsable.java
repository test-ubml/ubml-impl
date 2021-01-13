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

package org.openatom.ubml.common.enums;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;

public interface EnumValueParsable<T> {

    T getValue();

    /**
     * 通用的自定义枚举类型转换方法，凡是实现{@link EnumValueParsable}接口的枚举类，并通过泛型指定枚举的值的类型， 即可通过此方法来将给定的值转换为对应的枚举类型。
     *
     * @param value 给定的值
     * @param clazz 要转换的枚举类的class类型
     * @param <T>   给定值的类型
     * @param <E>   要转换的枚举类
     * @return 转换后的枚举类型对象
     * @throws EnumValueParseException 无法在给定枚举类型中找到对应枚举值或者给定值为{@code null}时，会抛出此异常
     */
    static <T, E extends Enum & EnumValueParsable<T>> E parse(T value, Class<E> clazz)
            throws EnumValueParseException {
        return Optional.ofNullable(value)
                .map(val -> {
                    for (E item : clazz.getEnumConstants()) {
                        if (Objects.equals(item.getValue(), val)) {
                            return item;
                        }
                    }
                    return null;
                })
                .orElseThrow(() -> new EnumValueParseException(
                        MessageFormat.format(
                                "Could not parse value {0} to type {1}.",
                                value, clazz.getCanonicalName())));
    }

    class EnumValueParseException extends RuntimeException {

        private static final long serialVersionUID = -2557475060594463395L;

        EnumValueParseException(String message) {
            super(message);
        }

        EnumValueParseException(String message, Throwable cause) {
            super(message, cause);
        }

        EnumValueParseException(Throwable cause) {
            super(cause);
        }

        EnumValueParseException(
                String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}
