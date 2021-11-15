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

package com.gitee.fubluesky.kernel.file.api;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * 文件操作 接口
 *
 * @author yanghq
 * @version 1.0
 * @since 2021-07-30 11:15
 */
public interface FileOperatorApi {

	/**
	 * 获取http路径前缀
	 * @return http路径前缀
	 */
	String getHttpPrefix();

	/**
	 * 根据时间规则生成文件路径
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @return 返回上传路径
	 */
	default String getPath(String prefix, String suffix) {
		// 生成uuid
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		// 年月日
		String day = FastDateFormat.getInstance("yyyyMMdd").format(new Date());
		// 文件路径
		String path = day + "/" + uuid;

		if (StringUtils.isNotBlank(prefix)) {
			path = prefix + "/" + path;
		}

		return path + suffix;
	}

	/**
	 * 文件上传
	 * @param data 文件字节数组
	 * @param path 文件路径，包含文件名
	 * @return 返回http地址
	 */
	default String upload(byte[] data, String path) {
		return upload(new ByteArrayInputStream(data), path);
	}

	/**
	 * 文件上传
	 * @param inputStream 字节流
	 * @param path 文件路径，包含文件名
	 * @return 返回http地址
	 */
	String upload(InputStream inputStream, String path);

	/**
	 * 获取文件流
	 * @param path 文件路径，包含文件名
	 * @return 文件流
	 */
	default InputStream getFile(String path) {
		return new ByteArrayInputStream(getFileBytes(path));
	}

	/**
	 * 获取文件字节
	 * @param path 文件路径，包含文件名
	 * @return 文件字节
	 */
	byte[] getFileBytes(String path);

	/**
	 * 文件删除
	 * @param path 文件路径，包含文件名
	 */
	void delete(String path);

}
