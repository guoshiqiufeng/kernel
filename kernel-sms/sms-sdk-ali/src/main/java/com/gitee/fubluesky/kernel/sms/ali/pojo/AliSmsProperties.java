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

package com.gitee.fubluesky.kernel.sms.ali.pojo;

import com.gitee.fubluesky.kernel.sms.api.pojo.SmsProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-04 11:24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AliSmsProperties extends SmsProperties {

	/**
	 * 是否启用
	 */
	private Boolean enabled = false;

	/**
	 * 地域id
	 */
	private String regionId = "cn-hangzhou";

	/**
	 * accessKeyId
	 */
	private String accessKeyId;

	/**
	 * accessKeySecret
	 */
	private String accessKeySecret;

	/**
	 * 签名名称
	 */
	private String signName;

	/**
	 * domain
	 */
	private String smsDomain = "dysmsapi.aliyuncs.com";

	/**
	 * version
	 */
	private String smsVersion = "2017-05-25";

	/**
	 * SendSms
	 */
	private String smsSendAction = "SendSms";

}
