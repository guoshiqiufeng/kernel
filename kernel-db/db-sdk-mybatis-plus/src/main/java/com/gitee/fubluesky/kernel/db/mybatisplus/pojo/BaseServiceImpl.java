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
package com.gitee.fubluesky.kernel.db.mybatisplus.pojo;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitee.fubluesky.kernel.db.api.exception.DbException;
import com.gitee.fubluesky.kernel.db.api.exception.enums.DbExceptionEnum;
import com.gitee.fubluesky.kernel.db.api.pojo.domain.BaseDomain;
import com.gitee.fubluesky.kernel.db.api.pojo.page.PageResult;
import com.gitee.fubluesky.kernel.db.mybatisplus.factory.PageFactory;
import com.gitee.fubluesky.kernel.db.mybatisplus.factory.PageResultFactory;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * 通用业务逻辑实现
 *
 * @author <a href="mailto:fubluesky@foxmail.com">yanghq</a>
 * @version 1.0
 * @since 2021-02-05 13:42
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseDomain> extends ServiceImpl<M, T>
        implements IBaseService<T> {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(T domain) {
        return super.save(domain);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(Long id) {
        if (checkId(id)) {
            return super.removeById(id);
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(T domain) {
        return super.updateById(domain);
    }

    @Override
    public T get(Long id) {
        T domain = super.getById(id);
        if (null == domain) {
            throw new DbException(DbExceptionEnum.DATA_NONE_ERROR);
        }
        return domain;
    }

    @Override
    public PageResult findPage(T domain) {
        for (Field field : domain.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if ("String".equals(field.getType().getSimpleName())) {
                    if (StrUtil.isBlank((String) field.get(domain))) {
                        field.set(domain, null);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        LambdaQueryWrapper<T> wrapper = Wrappers.lambdaQuery(domain);
        Page<T> page = super.page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    /**
     * 批量删除
     *
     * @param ids id数组
     * @return {@code boolean}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long[] ids) {
        super.removeByIds(Arrays.asList(ids));
        return true;
    }

    /**
     * 检查 主键 是否存在
     *
     * @param id {@code Long} ID
     * @return {@code boolean} ID 不存在则抛出异常
     */
    protected boolean checkId(Long id) {
        if (getById(id) == null) {
            throw new DbException(DbExceptionEnum.DATA_NONE_ERROR);
        }
        return true;
    }

}
