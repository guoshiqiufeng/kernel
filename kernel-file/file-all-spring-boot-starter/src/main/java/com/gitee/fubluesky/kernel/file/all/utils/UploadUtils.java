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

package com.gitee.fubluesky.kernel.file.all.utils;

import com.gitee.fubluesky.kernel.file.api.FileOperatorApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-19 9:33
 */
@Component
public class UploadUtils {

	private static FileOperatorApi aliFileOperator;

	private static FileOperatorApi ftpFileOperator;

	private static Boolean aliEnabled;

	@Autowired
	public void setAliFileOperator(FileOperatorApi aliFileOperator) {
		UploadUtils.aliFileOperator = aliFileOperator;
	}

	@Autowired
	public void setFtpFileOperator(FileOperatorApi ftpFileOperator) {
		UploadUtils.ftpFileOperator = ftpFileOperator;
	}

	@Value("${kernel.file.ali.enabled:false}")
	public void setOssEnabled(Boolean aliEnabled) {
		UploadUtils.aliEnabled = aliEnabled;
	}

	private static Boolean ftpEnabled;

	@Value("${kernel.file.ftp.enabled:false}")
	public void setFtpEnabled(Boolean ftpEnabled) {
		UploadUtils.ftpEnabled = ftpEnabled;
	}

	/**
	 * 文件上传
	 * @param data 文件字节数组
	 * @param path 文件路径
	 * @return 返回http地址
	 */
	public static String upload(byte[] data, String path) {
		return upload(data, "", path);
	}

	/**
	 * 文件上传
	 * @param data 文件字节数组
	 * @param savePrefixPath 保存地址前缀
	 * @param path 文件路径
	 * @return 返回http地址
	 */
	public static String upload(byte[] data, String savePrefixPath, String path) {
		String url = "";
		if (aliEnabled) {
			url = aliFileOperator.getHttpPrefix() + aliFileOperator.upload(data, savePrefixPath, path);
		}
		if (ftpEnabled) {
			url = aliFileOperator.getHttpPrefix() + ftpFileOperator.upload(data, savePrefixPath, path);
		}
		return url;
	}

	/**
	 * 获取文件上传访问地址前缀
	 * @return 文件访问地址前缀
	 */
	public static String getHttpPrefix() {
		String httpPrefix = "";
		if (aliEnabled) {
			httpPrefix = aliFileOperator.getHttpPrefix();
		}
		if (ftpEnabled) {
			httpPrefix = ftpFileOperator.getHttpPrefix();
		}
		return httpPrefix;
	}

}
