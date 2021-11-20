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

package com.gitee.fubluesky.kernel.excel.api;

import com.gitee.fubluesky.kernel.excel.api.pojo.ExportParam;

import java.io.InputStream;
import java.util.List;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-11-18 15:48
 */
public interface ExcelApi {

	/**
	 * 下载
	 * @param exportParam 下载参数
	 */
	void download(ExportParam exportParam);

	/**
	 * 从文件流中 组装 对象列表
	 * @param inputStream 文件流
	 * @param clazz 对象
	 * @return 对象列表
	 */
	<T> List<T> getList(InputStream inputStream, Class<T> clazz);

}
