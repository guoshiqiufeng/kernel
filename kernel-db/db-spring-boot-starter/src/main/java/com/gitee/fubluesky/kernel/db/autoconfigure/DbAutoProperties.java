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

import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.DbProperties;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-11-24 11:29
 */
@Accessors(chain = true)
@ConfigurationProperties(prefix = "kernel.db")
public class DbAutoProperties extends DbProperties {

}
