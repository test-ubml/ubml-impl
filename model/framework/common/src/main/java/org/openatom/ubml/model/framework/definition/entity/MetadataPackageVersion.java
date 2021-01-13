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

package org.openatom.ubml.model.framework.definition.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 元数据包版本
 */
@JsonIgnoreProperties(value = "versionInt", allowGetters = false, allowSetters = false)
public class MetadataPackageVersion {
    /**
     * 版本号位数 常量确定版本号位数
     */
    private static final int VERSION_LENGTH = 3;

    /**
     * 版本号分隔符 例如版本分隔符为'.'，版本号位数为3，版本号形式则为"1.0.0"
     */
    private static final char SEPARATOR = '.';

    private int[] versionInt;

    /**
     * 构造函数 初始化前两位版本信息
     */
    public MetadataPackageVersion() {
        versionInt = new int[VERSION_LENGTH];
        versionInt[0] = 1;
        versionInt[1] = 1;
    }

    /**
     * 构造函数
     *
     * @param versionStr 根据版本字符串构造版本信息
     */
    public MetadataPackageVersion(String versionStr) {
        setVersionString(versionStr);
    }

    /**
     * 构造函数
     *
     * @param packageVersion
     */
    public MetadataPackageVersion(MetadataPackageVersion packageVersion) {
        versionInt = packageVersion.getVersionInt();
    }

    /**
     * 在指定位置给版本号增加指定数量
     *
     * @param position 指定位置。最左边位置号为0，向右依次增1.
     * @param quantity 指定数量
     */
    public final void add(int position, int quantity) {
        if (position > VERSION_LENGTH - 1) {
            throw new RuntimeException("指定版本号位置超出有效范围。");
        }
        versionInt[position] += quantity;
    }

    /**
     * 在指定位置给版本号增加1
     *
     * @param position 指定位置。最左边位置号为1，向右依次增1.
     */
    public final void Add(int position) {
        if (position > VERSION_LENGTH) {
            throw new RuntimeException("指定版本号位置超出有效范围。");
        }
        versionInt[position] += 1;
    }

    /**
     * 版本号末位增加指定数量
     *
     * @param quantity 指定数量
     */
    public final void AddLast(int quantity) {
        versionInt[VERSION_LENGTH - 1] += quantity;
    }

    /**
     * 版本号末位增1
     */
    public final void AddLast() {
        versionInt[VERSION_LENGTH - 1] += 1;
    }

    /**
     * 比较
     *
     * @param another
     * @return
     */
    public final int CompareTo(Object another) {
        if (!(another instanceof MetadataPackageVersion)) {
            throw new RuntimeException("MetadataPackageVersion的CompareTo方法只能传入MetadataPackageVersion类型的参数。");
        }
        MetadataPackageVersion anotherVersion = (MetadataPackageVersion)another;
        for (int i = 0; i < VERSION_LENGTH; i++) {
            if (this.versionInt[i] > anotherVersion.getVersionInt()[i]) {
                return 1;
            }
            if (this.versionInt[i] < anotherVersion.getVersionInt()[i]) {
                return -1;
            }
        }
        return 0;
    }

    /**
     * 字符串型版本信息
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String privateVersionString;

    public final String getVersionString() {
        return privateVersionString;
    }

    public final void setVersionString(String value) {
        privateVersionString = value;
    }

    /**
     * 整型版本信息
     */
    public final int[] getVersionInt() {
        return versionInt;
    }

    /**
     * 转换成字符型
     *
     * @return 版本字符串
     */
    @Override
    public String toString() {
        return getVersionString();
    }

}