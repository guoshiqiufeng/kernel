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

package com.gitee.fubluesky.kernel.sms.api;

import java.util.Map;

/**
 * 发送短信
 *
 * @author yanghq
 * @version 1.0
 * @since 2021-08-04 10:34
 */
public interface SmsSenderApi {

	/**
	 * 发送短信
	 * @param phoneNumber 手机号
	 * @param templateCode 模板
	 * @param params 参数
	 */
	void sendSms(String phoneNumber, String templateCode, Map<String, Object> params);

}
