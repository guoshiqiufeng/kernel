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
package com.gitee.fubluesky.kernel.security.sign.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-11-11 09:17
 */
@Data
public class SignProperties implements Serializable {

    private static final long serialVersionUID = -8998290936324394431L;

    /**
     * 是否启用
     */
    private Boolean enabled = false;

    /**
     * requestBody 签名
     */
    private Boolean requestBodyEnable = true;

    /**
     * get请求 签名
     */
    private Boolean requestGetEnable = false;

    /**
     * 签名 盐
     */
    private String salt = "kernel";

    /**
     * 签名header参数名
     */
    private String signHeaderName = "sign";

    /**
     * 使用多盐 不开启所有签名使用 salt 开启签名使用appId对应盐
     */
    private Boolean usedMultiSalt = false;

    /**
     * appId 用于使用多 盐 根据appId从 saltMap中查找对应盐
     */
    private String appIdHeaderName = "appId";

    /**
     * 盐 使用appId对应的盐
     */
    private Map<String, String> saltMap;

}
