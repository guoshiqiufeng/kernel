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

package com.gitee.fubluesky.kernel.core.enums;

import lombok.Getter;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-11-23 14:32
 */
@Getter
public enum ErrorType {

    /**
     * 用户操作异常
     */
    USER_OPERATION_ERROR("1", "用户操作异常"),

    /**
     * 业务异常
     */
    BUSINESS_ERROR("2", "业务异常"),

    /**
     * 第三方异常
     */
    THIRD_ERROR("3", "第三方异常"),

    /**
     * 空
     */
    EMPTY("", "")

    ;

    private final String code;

    private final String describe;

    ErrorType(String code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public static ErrorType getErrorType(String code) {
        if (null != code) {
            for (ErrorType errorType : ErrorType.values()) {
                if (errorType.getCode().equals(code)) {
                    return errorType;
                }
            }
        }
        return null;
    }

    public static String codeToDescribe(String code) {
        if (null != code) {
            for (ErrorType errorType : ErrorType.values()) {
                if (errorType.getCode().equals(code)) {
                    return errorType.getDescribe();
                }
            }
        }
        return "未知";
    }
}
