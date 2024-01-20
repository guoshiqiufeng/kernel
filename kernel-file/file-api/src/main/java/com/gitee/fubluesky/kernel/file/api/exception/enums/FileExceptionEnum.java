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
package com.gitee.fubluesky.kernel.file.api.exception.enums;

import com.gitee.fubluesky.kernel.core.enums.ErrorType;
import com.gitee.fubluesky.kernel.core.exception.AbstractExceptionEnum;
import com.gitee.fubluesky.kernel.file.api.constants.FileConstants;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-07-30 10:26
 */
public enum FileExceptionEnum implements AbstractExceptionEnum {

    /**
     * 阿里云OSS未启用
     */
    ALI_OSS_NOT_ENABLE(ErrorType.THIRD_ERROR.getCode(), FileConstants.EXCEPTION_STEP_CODE + "01", "阿里云OSS未启用"),

    /**
     * ftp文件上传未启用
     */
    FTP_NOT_ENABLE(ErrorType.THIRD_ERROR.getCode(), FileConstants.EXCEPTION_STEP_CODE + "01", "ftp文件上传未启用"),

    /**
     * 本地文件上传未启用
     */
    LOCAL_NOT_ENABLE(ErrorType.THIRD_ERROR.getCode(), FileConstants.EXCEPTION_STEP_CODE + "01", "本地文件上传未启用"),

    /**
     * 本地文件上传初始化失败
     */
    LOCAL_INIT_ERROR(ErrorType.THIRD_ERROR.getCode(), FileConstants.EXCEPTION_STEP_CODE + "02", "本地文件上传初始化失败"),

    /**
     * 文件不存在
     */
    FILE_NOT_FOUND(ErrorType.THIRD_ERROR.getCode(), FileConstants.EXCEPTION_STEP_CODE + "04", "文件不存在"),

    /**
     * 上传文件失败，请检查配置信息
     */
    FILE_UPLOAD_ERROR(ErrorType.THIRD_ERROR.getCode(), FileConstants.EXCEPTION_STEP_CODE + "10", "上传文件失败，请检查配置信息"),

    /**
     * 获取文件失败
     */
    FILE_GET_ERROR(ErrorType.THIRD_ERROR.getCode(), FileConstants.EXCEPTION_STEP_CODE + "11", "获取文件失败，请检查配置信息"),

    /**
     * 删除文件失败
     */
    FILE_DELETE_ERROR(ErrorType.THIRD_ERROR.getCode(), FileConstants.EXCEPTION_STEP_CODE + "12", "删除文件失败，请检查配置信息"),

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

    FileExceptionEnum(String typeCode, String code, String message) {
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
