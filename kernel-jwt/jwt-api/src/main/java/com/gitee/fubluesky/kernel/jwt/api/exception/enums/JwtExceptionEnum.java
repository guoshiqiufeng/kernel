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
package com.gitee.fubluesky.kernel.jwt.api.exception.enums;

import com.gitee.fubluesky.kernel.core.enums.ErrorType;
import com.gitee.fubluesky.kernel.core.exception.AbstractExceptionEnum;
import com.gitee.fubluesky.kernel.jwt.api.constants.JwtConstants;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-07-23 15:41
 */
public enum JwtExceptionEnum implements AbstractExceptionEnum {

    /**
     * jwt解析异常
     */
    JWT_PARSE_ERROR(ErrorType.BUSINESS_ERROR.getCode(), JwtConstants.EXCEPTION_STEP_CODE + "01", "jwt解析错误！"),

    /**
     * jwt过期了
     */
    JWT_EXPIRED_ERROR(ErrorType.BUSINESS_ERROR.getCode(), JwtConstants.EXCEPTION_STEP_CODE + "02", "jwt过期了！"),

    /**
     * jwt生成异常
     */
    JWT_CREATE_ERROR(ErrorType.BUSINESS_ERROR.getCode(), JwtConstants.EXCEPTION_STEP_CODE + "03", "jwt生成异常"),

    ;

    /**
     * 异常分类 用户端异常: 1 业务异常: 2 第三方异常: 3
     */
    private final String typeCode;

    /**
     * 错误编码
     */
    private final String code;

    /**
     * 错误信息
     */
    private final String message;

    JwtExceptionEnum(String typeCode, String code, String message) {
        this.typeCode = typeCode;
        this.code = code;
        this.message = message;
    }

    /**
     * 获取异常的状态码
     *
     * @return 状态码
     */
    @Override
    public String getCode() {
        return typeCode + code;
    }

    /**
     * 获取异常的消息
     *
     * @return 异常消息
     */
    @Override
    public String getMessage() {
        return message;
    }

}
