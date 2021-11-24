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

package com.gitee.fubluesky.kernel.email.api.constants;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-07-27 17:21
 */
public interface MailConstants {

	/**
	 * 邮件模块的名称
	 */
	String MODULE_NAME = "kernel-email";

	/**
	 * 异常枚举的步进值
	 */
	String EXCEPTION_STEP_CODE = "21";

	/**
	 * 邮件服务器主机名
	 */
	String MAIL_HOST = "mail.host";

	/**
	 * 端口号
	 */
	String MAIL_PORT = "mail.port";

	/**
	 * 邮件协议名称
	 */
	String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";

	String MAIL_SMTP = "smtp";

	/**
	 * 是否认证
	 */
	String MAIL_SMTP_AUTH = "mail.smtp.auth";

	/**
	 * 是否 开启SSL加密
	 */
	String MAIL_SMTP_SSL_ENABLE = "mail.smtp.ssl.enable";

	String MAIL_SMTP_SSL_SOCKET_FACTORY = "mail.smtp.ssl.socketFactory";

}
