/*
 *
 *   Copyright 2021 fubluesky.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.gitee.fubluesky.kernel.db.autoconfigure;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.gitee.fubluesky.kernel.db.api.constants.DbConstants;
import com.gitee.fubluesky.kernel.db.utils.MybatisConfigurationUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-30 13:31
 */
@Data
@Accessors(chain = true)
@ConfigurationProperties(prefix = Constants.MYBATIS_PLUS)
public class CustomMybatisPlusProperties {

	private String typeAliasesPackage = DbConstants.TYPE_ALIASES_PACKAGE;

	@NestedConfigurationProperty
	private MybatisConfiguration configuration = MybatisConfigurationUtils.getDefault();

	@NestedConfigurationProperty
	private GlobalConfig globalConfig = GlobalConfigUtils.defaults().setBanner(false).setDbConfig(
			new GlobalConfig.DbConfig().setIdType(IdType.AUTO).setLogicDeleteField(DbConstants.LOGIC_DELETE_FIELD));

}
