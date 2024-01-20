/*
 * Copyright (c) 2021-2024, fubluesky (fubluesky@foxmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gitee.fubluesky.kernel.db.mybatisplus.handler;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.DbProperties;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * mybatis plus 自动填充 配置
 *
 * @author yanghq
 * @version 1.0
 * @since 2021-05-10 15:26
 */
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static final String LANG_INTEGER = "Integer";

    private DbProperties dbProperties;

    public MyMetaObjectHandler() {
    }

    public MyMetaObjectHandler(DbProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        if (!dbProperties.getMetaEnabled()) {
            return;
        }
        String createDate = dbProperties.getCreateDate();
        String modifyDate = dbProperties.getModifyDate();
        String isDeleted = dbProperties.getIsDeleted();
        // 判断是否有相关字段
        boolean hasCreateDate = metaObject.hasSetter(createDate);
        boolean hasUpdateDate = metaObject.hasSetter(modifyDate);
        boolean hasDeleted = metaObject.hasSetter(isDeleted);

        // 有字段则自动填充
        if (hasCreateDate) {
            strictInsertFill(metaObject, createDate, LocalDateTime.class, LocalDateTime.now());
        }
        if (hasUpdateDate) {
            strictInsertFill(metaObject, modifyDate, LocalDateTime.class, LocalDateTime.now());
        }
        if (hasDeleted) {
            String type = metaObject.getGetterType(isDeleted).getSimpleName();
            if (StrUtil.isNotBlank(type) && LANG_INTEGER.equals(type)) {
                strictInsertFill(metaObject, isDeleted, Integer.class, 0);
            } else {
                strictInsertFill(metaObject, isDeleted, Boolean.class, false);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (!dbProperties.getMetaEnabled()) {
            return;
        }
        String modifyDate = dbProperties.getModifyDate();
        Object val = getFieldValByName(modifyDate, metaObject);
        // 没有自定义值时才更新字段
        if (val == null) {
            strictUpdateFill(metaObject, modifyDate, LocalDateTime.class, LocalDateTime.now());
        }
    }

    /**
     * 严格模式填充策略,默认有值不覆盖,如果提供的值为null也不填充
     *
     * @param metaObject metaObject meta object parameter
     * @param fieldName  java bean property name
     * @param fieldVal   java bean property value of Supplier
     * @return this
     * @since 3.3.0
     */
    @Override
    public MetaObjectHandler strictFillStrategy(MetaObject metaObject, String fieldName, Supplier<?> fieldVal) {
        Object obj = fieldVal.get();
        if (Objects.nonNull(obj)) {
            metaObject.setValue(fieldName, obj);
        }
        return this;
    }
}
