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
package com.gitee.fubluesky.kernel.file.api.utils;

import cn.hutool.core.util.StrUtil;
import com.gitee.fubluesky.kernel.file.api.constants.FileConstants;
import lombok.experimental.UtilityClass;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-17 11:11
 */
@UtilityClass
public class FileUrlUtils {

    /**
     * 获取相对文件地址
     *
     * @param prefix 文件服务前缀地址
     * @param url    文件地址
     * @return {String} 相对文件地址
     */
    public static String getRelativeAddress(String prefix, String url) {
        if (StrUtil.isBlank(url)) {
            return "";
        }
        if (prefix == null) {
            return "";
        }
        if (StrUtil.isNotBlank(url) && url.indexOf(prefix) == 0) {
            url = url.replace(prefix, "");
        }
        return url;
    }

    /**
     * 获取完整文件地址
     *
     * @param prefix 文件服务前缀地址
     * @param url    文件地址
     * @return {String} 完整文件地址
     */
    public static String getFullAddress(String prefix, String url) {
        if (StrUtil.isBlank(url)) {
            return "";
        }

        if (prefix == null) {
            return "";
        }
        if (StrUtil.isNotBlank(url)) {
            if (url.indexOf(FileConstants.NETWORK_HTTP) != 0 || url.indexOf(FileConstants.NETWORK_HTTPS) != 0) {
                url = prefix + url;
            }
        }
        return url;
    }

}
