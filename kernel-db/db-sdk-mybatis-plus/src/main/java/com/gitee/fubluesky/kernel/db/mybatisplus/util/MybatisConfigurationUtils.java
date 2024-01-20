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
package com.gitee.fubluesky.kernel.db.mybatisplus.util;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import lombok.experimental.UtilityClass;
import org.apache.ibatis.type.JdbcType;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-30 13:48
 */
@UtilityClass
public class MybatisConfigurationUtils {

    /**
     * 获取默认配置
     *
     * @return MybatisConfiguration
     */
    public MybatisPlusProperties.CoreConfiguration getDefault() {
        MybatisPlusProperties.CoreConfiguration mybatisConfiguration = new MybatisPlusProperties.CoreConfiguration();

        mybatisConfiguration.setCacheEnabled(false);
        mybatisConfiguration.setCallSettersOnNulls(true);
        mybatisConfiguration.setJdbcTypeForNull(JdbcType.NULL);

        return mybatisConfiguration;
    }

}
