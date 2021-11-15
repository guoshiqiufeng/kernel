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

package com.gitee.fubluesky.kernel.sms.ali;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.gitee.fubluesky.kernel.sms.ali.constants.AliConstants;
import com.gitee.fubluesky.kernel.sms.ali.pojo.AliSmsProperties;
import com.gitee.fubluesky.kernel.sms.api.SmsSenderApi;
import com.gitee.fubluesky.kernel.sms.api.exception.SmsException;
import com.gitee.fubluesky.kernel.sms.api.exception.enums.SmsExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.Map;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-04 11:27
 */
@Slf4j
public class AliSmsSender implements SmsSenderApi {

	private IAcsClient iAcsClient;

	private final AliSmsProperties aliSmsProperties;

	public AliSmsSender(AliSmsProperties aliSmsProperties) {
		this.aliSmsProperties = aliSmsProperties;
	}

	private void init() {
		DefaultProfile profile = DefaultProfile.getProfile(aliSmsProperties.getRegionId(),
				aliSmsProperties.getAccessKeyId(), aliSmsProperties.getAccessKeySecret());
		iAcsClient = new DefaultAcsClient(profile);
	}

	/**
	 * 发送短信
	 * @param phoneNumber 手机号
	 * @param templateCode 模板
	 * @param params 参数
	 */
	@Override
	public void sendSms(String phoneNumber, String templateCode, Map<String, Object> params) {
		init();
		log.info("start send sms，phone number：{},templateCode：{},params：{}", phoneNumber, templateCode, params);
		// 检验参数
		validatorSendSmsParams(phoneNumber, templateCode, params);

		String jsonResult = createSmsRequest(phoneNumber, templateCode, params);

		JsonObject jsonObject = new Gson().fromJson(jsonResult, JsonObject.class);
		if (!AliConstants.OK.equals(jsonObject.get(AliConstants.CODE).getAsString())) {
			log.error("send ali yun sms error！message :{}", jsonObject.get(AliConstants.MESSAGE).getAsString());
			throw new SmsException(SmsExceptionEnum.SMS_SEND_ERROR);
		}
	}

	private String createSmsRequest(String phoneNumber, String templateCode, Map<String, Object> params) {
		CommonRequest request = new CommonRequest();
		request.setSysDomain(aliSmsProperties.getSmsDomain());
		request.setSysVersion(aliSmsProperties.getSmsVersion());
		request.setSysAction(aliSmsProperties.getSmsSendAction());

		// 接收短信的手机号码
		request.putQueryParameter("PhoneNumbers", phoneNumber);

		// 短信签名名称。请在控制台签名管理页面签名名称一列查看（必须是已添加、并通过审核的短信签名）。
		request.putQueryParameter("SignName", aliSmsProperties.getSignName());

		// 短信模板ID
		request.putQueryParameter("TemplateCode", templateCode);

		// 短信模板变量对应的实际值，JSON格式。
		request.putQueryParameter("TemplateParam", new Gson().toJson(params));

		// 请求失败这里会抛ClientException异常
		CommonResponse commonResponse;
		try {
			commonResponse = iAcsClient.getCommonResponse(request);
			String data = commonResponse.getData();
			String jsonResult = data.replaceAll("'\'", "");
			log.info("response result :{}", jsonResult);
			return jsonResult;
		}
		catch (ClientException e) {
			log.error("send ali yun sms error！", e);

			// 组装错误信息
			throw new SmsException(SmsExceptionEnum.SMS_SEND_ERROR);
		}
	}

	/**
	 * 校验参数
	 * @param phoneNumber 手机号
	 * @param templateCode 模板
	 * @param params 参数
	 */
	private void validatorSendSmsParams(String phoneNumber, String templateCode, Map<String, Object> params) {
		if (StringUtils.isBlank(phoneNumber)) {
			String format = MessageFormat.format(SmsExceptionEnum.SEND_SMS_PARAM_NULL.getMessage(), "手机号码");
			throw new SmsException(SmsExceptionEnum.SEND_SMS_PARAM_NULL, format);
		}

		if (StringUtils.isBlank(templateCode)) {
			String format = MessageFormat.format(SmsExceptionEnum.SEND_SMS_PARAM_NULL.getMessage(), "模板编码");
			throw new SmsException(SmsExceptionEnum.SEND_SMS_PARAM_NULL, format);
		}

		if (params == null || params.isEmpty()) {
			String format = MessageFormat.format(SmsExceptionEnum.SEND_SMS_PARAM_NULL.getMessage(), "模板参数");
			throw new SmsException(SmsExceptionEnum.SEND_SMS_PARAM_NULL, format);
		}

		if (aliSmsProperties == null) {
			String format = MessageFormat.format(SmsExceptionEnum.SEND_SMS_PARAM_NULL.getMessage(), "短信配置");
			throw new SmsException(SmsExceptionEnum.SEND_SMS_PARAM_NULL, format);
		}

		if (!aliSmsProperties.getEnabled()) {
			throw new SmsException(SmsExceptionEnum.SEND_SMS_UN_ENABLE);
		}

	}

}
