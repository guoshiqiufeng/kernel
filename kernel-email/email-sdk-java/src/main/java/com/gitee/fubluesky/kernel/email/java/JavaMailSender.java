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

package com.gitee.fubluesky.kernel.email.java;

import com.sun.mail.util.MailSSLSocketFactory;
import com.gitee.fubluesky.kernel.core.constants.CoreConstants;
import com.gitee.fubluesky.kernel.email.api.MailSenderApi;
import com.gitee.fubluesky.kernel.email.api.constants.MailConstants;
import com.gitee.fubluesky.kernel.email.api.exception.MailException;
import com.gitee.fubluesky.kernel.email.api.exception.enums.MailExceptionEnum;
import com.gitee.fubluesky.kernel.email.api.pojo.MailProperties;
import com.gitee.fubluesky.kernel.email.api.pojo.SendMailParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-07-27 17:49
 */
@Slf4j
public class JavaMailSender implements MailSenderApi {

	private final MailProperties mailProperties;

	public JavaMailSender(MailProperties mailProperties) {
		this.mailProperties = mailProperties;
	}

	/**
	 * 普通邮件
	 * @param sendMailParam 参数
	 */
	@Override
	public void sendMail(SendMailParam sendMailParam) {
		checkMailParam(sendMailParam);
		sendMail(sendMailParam.getTo(), sendMailParam.getTitle(), sendMailParam.getContent(), false);
	}

	/**
	 * html的邮件
	 * @param sendMailParam 参数
	 */
	@Override
	public void sendMailHtml(SendMailParam sendMailParam) {
		checkMailParam(sendMailParam);
		sendMail(sendMailParam.getTo(), sendMailParam.getTitle(), sendMailParam.getContent(), true);
	}

	/**
	 * 发送邮件
	 * @param addressList 邮件地址
	 * @param subject 主题
	 * @param content 内容
	 */
	private void sendMail(String addressList, String subject, String content, Boolean isHtml) {
		// 配置
		Properties prop = new Properties();
		// 设置邮件服务器主机名，这里是163
		prop.put(MailConstants.MAIL_HOST, mailProperties.getHost());
		// 发送邮件协议名称
		prop.put(MailConstants.MAIL_TRANSPORT_PROTOCOL, MailConstants.MAIL_SMTP);

		if (mailProperties.getPort() != null) {
			prop.put(MailConstants.MAIL_PORT, mailProperties.getPort());
		}
		try {
			// SSL加密
			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			// 设置信任所有的主机
			sf.setTrustAllHosts(true);
			prop.put(MailConstants.MAIL_SMTP_SSL_ENABLE, CoreConstants.FLAG_TRUE);
			prop.put(MailConstants.MAIL_SMTP_SSL_SOCKET_FACTORY, sf);

			Session session;

			if (mailProperties.getAuth()) {
				// 是否认证
				prop.put(MailConstants.MAIL_SMTP_AUTH, true);
				// 创建会话对象
				session = Session.getDefaultInstance(prop, new Authenticator() {
					// 认证信息，需要提供"用户账号","授权码"
					@Override
					public PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(mailProperties.getUser(), mailProperties.getPass());
					}
				});
			}
			else {
				session = Session.getDefaultInstance(prop);
			}

			if (mailProperties.getDebug()) {
				// 是否打印出debug信息
				session.setDebug(true);
			}

			// 创建邮件
			MimeMessage message = new MimeMessage(session);
			Address[] internetAddressTo = InternetAddress.parse(addressList);
			// 邮件发送者
			message.setFrom(new InternetAddress(mailProperties.getFrom()));
			// 邮件接受者
			message.setRecipients(Message.RecipientType.TO, internetAddressTo);
			// 邮件主题
			message.setSubject(subject);

			if (isHtml) {
				message.setContent(content, "text/html;charset=UTF-8");
			}
			else {
				message.setText(content);
			}

			// 邮件发送
			Transport transport = session.getTransport();
			transport.connect();
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		}
		catch (Exception e) {
			log.error("email send error:", e);
			throw new MailException(MailExceptionEnum.EMAIL_SEND_ERROR, e.getMessage());
		}
	}

	/**
	 * 校验参数
	 * @param sendMailParam 参数
	 */
	private void checkMailParam(SendMailParam sendMailParam) {
		// 校验配置信息
		if (mailProperties == null) {
			String format = MessageFormat.format(MailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getMessage(), "");
			throw new MailException(MailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getCode(), format);
		}
		// 服务器主机名
		if (StringUtils.isEmpty(mailProperties.getHost())) {
			String format = MessageFormat.format(MailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getMessage(), "服务器主机名");
			throw new MailException(MailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getCode(), format);
		}
		// 用户名
		if (mailProperties.getAuth() && StringUtils.isEmpty(mailProperties.getUser())) {
			String format = MessageFormat.format(MailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getMessage(), "用户名");
			throw new MailException(MailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getCode(), format);
		}
		// 密码
		if (mailProperties.getAuth() && StringUtils.isEmpty(mailProperties.getPass())) {
			String format = MessageFormat.format(MailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getMessage(), "密码");
			throw new MailException(MailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getCode(), format);
		}
		// 发送方
		if (StringUtils.isEmpty(mailProperties.getFrom())) {
			String format = MessageFormat.format(MailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getMessage(), "发送方");
			throw new MailException(MailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getCode(), format);
		}

		// 校验实体
		if (sendMailParam == null) {
			String format = MessageFormat.format(MailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getMessage(), "");
			throw new MailException(MailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getCode(), format);
		}
		// 收件人邮箱
		if (StringUtils.isEmpty(sendMailParam.getTo())) {
			String format = MessageFormat.format(MailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getMessage(), "收件人邮箱");
			throw new MailException(MailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getCode(), format);
		}
		// 邮件标题
		if (StringUtils.isEmpty(sendMailParam.getTitle())) {
			String format = MessageFormat.format(MailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getMessage(), "邮件标题");
			throw new MailException(MailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getCode(), format);
		}
		// 邮件内容
		if (StringUtils.isEmpty(sendMailParam.getContent())) {
			String format = MessageFormat.format(MailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getMessage(), "邮件内容");
			throw new MailException(MailExceptionEnum.EMAIL_PARAM_EMPTY_ERROR.getCode(), format);
		}
	}

}
