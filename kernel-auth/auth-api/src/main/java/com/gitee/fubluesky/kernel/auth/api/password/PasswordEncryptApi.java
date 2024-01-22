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
package com.gitee.fubluesky.kernel.auth.api.password;

/**
 * 密码加密接口
 *
 * @author yanghq
 * @version 1.0
 * @since 2021-08-16 16:19
 */
public interface PasswordEncryptApi {

    /**
     * 加密
     *
     * @param password 密码
     * @return 加密后密码
     */
    String encode(String password);

    /**
     * 比较密码是否相同
     *
     * @param rawPassword     密码
     * @param encodedPassword 加密后密码
     * @return 相同 返回 true ,不同 返回false
     */
    Boolean matches(String rawPassword, String encodedPassword);

}
