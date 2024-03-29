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
package com.gitee.fubluesky.kernel.core.exception;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-07-21 14:19
 */
public interface AbstractExceptionEnum {

    /**
     * 获取异常的状态码
     *
     * @return 状态码
     */
    String getCode();

    /**
     * 获取异常的消息
     *
     * @return 异常消息
     */
    String getMessage();

    /**
     * 获取数字类型异常的状态码 若状态码可正常转换为数字则返回对应数字 若转换失败则返回-1
     *
     * @return 状态码
     */
    default Integer getIntegerCode() {
        String code = getCode();
        try {
            return Integer.parseInt(code);
        } catch (Exception exception) {
            return -1;
        }
    }

}
