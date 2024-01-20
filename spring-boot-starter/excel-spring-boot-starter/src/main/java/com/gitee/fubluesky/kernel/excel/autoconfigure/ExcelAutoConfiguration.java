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
package com.gitee.fubluesky.kernel.excel.autoconfigure;

import com.gitee.fubluesky.kernel.excel.api.ExcelApi;
import com.gitee.fubluesky.kernel.excel.api.pojo.ExcelProperties;
import com.gitee.fubluesky.kernel.excel.easy.EasyExcelService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-11-19 14:19
 */
@Configuration
@ConditionalOnProperty(prefix = "kernel.excel", name = "enabled", havingValue = "true")
public class ExcelAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "kernel.excel")
    @ConditionalOnMissingBean(ExcelProperties.class)
    public ExcelProperties excelProperties() {
        return new ExcelProperties();
    }

    @Bean
    @ConditionalOnMissingBean(ExcelApi.class)
    public ExcelApi excelApi() {
        return new EasyExcelService();
    }

}
