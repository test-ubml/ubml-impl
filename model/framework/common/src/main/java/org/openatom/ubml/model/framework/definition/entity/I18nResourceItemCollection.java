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

import java.util.ArrayList;

public class I18nResourceItemCollection extends ArrayList<I18nResourceItem> {

    /**
     * 根据key获取对应内容
     *
     * @param key
     * @return {@link I18nResourceItem}
     */
    public I18nResourceItem getResourceItemByKey(String key) {
        /**
         * 循环获取集合中资源项，然后对比key，如果key相同，返回值
         */
        for (int i = 0; i <= this.size(); i++) {
            I18nResourceItem item = this.get(i);
            if (item.getKey().equals(key)) {
                return item;
            }
        }
        return null;
    }

    public void setResourceItemByKey(String key, I18nResourceItem item) {
        I18nResourceItem entity = getResourceItemByKey(key);
        if (item == null) {
            throw new RuntimeException("未找到该资源项对应的对象！");
        }
        this.set(this.indexOf(item), item);
    }

    /**
     * 根据key判断是否含有该对象
     *
     * @param key
     * @return 是否含有该key对应的对象
     */
    public boolean contains(String key) {
        I18nResourceItem item = getResourceItemByKey(key);
        if (item == null)
            return false;
        return true;
    }

    /**
     * 根据key删除
     *
     * @param key
     */
    public void remove(String key) {
        I18nResourceItem item = getResourceItemByKey(key);
        if (item == null) {
            throw new RuntimeException("删除的对象不存在!");
        }

        this.remove(this.indexOf(item));
    }

    /**
     * 添加
     *
     * @param collection 数据集合
     */
    public void addRange(I18nResourceItemCollection collection) {
        if (collection == null || collection.size() == 0) {
            return;
        }

        collection.forEach(item -> {
            this.add(item);
        });
    }

}
