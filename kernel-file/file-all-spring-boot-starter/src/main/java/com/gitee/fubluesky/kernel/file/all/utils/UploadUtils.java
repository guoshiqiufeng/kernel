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

import com.gitee.fubluesky.kernel.file.ali.AliFileOperator;
import com.gitee.fubluesky.kernel.file.api.FileOperatorApi;
import com.gitee.fubluesky.kernel.file.ftp.FtpFileOperator;
import com.gitee.fubluesky.kernel.file.local.LocalFileOperator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-19 9:33
 */
@Slf4j
@Component
public class UploadUtils {

	private static FileOperatorApi aliFileOperator;

	private static FileOperatorApi ftpFileOperator;

	private static FileOperatorApi localFileOperator;

	private static Boolean aliEnabled;

	@Autowired(required = false)
	public void setAliFileOperator(AliFileOperator aliFileOperator) {
		UploadUtils.aliFileOperator = aliFileOperator;
	}

	@Autowired(required = false)
	public void setFtpFileOperator(FtpFileOperator ftpFileOperator) {
		UploadUtils.ftpFileOperator = ftpFileOperator;
	}

	@Autowired(required = false)
	public void setLocalFileOperator(LocalFileOperator localFileOperator) {
		UploadUtils.localFileOperator = localFileOperator;
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

	private static Boolean localEnabled;

	@Value("${kernel.file.local.enabled:false}")
	public void setLocalEnabled(Boolean localEnabled) {
		UploadUtils.localEnabled = localEnabled;
	}

	/**
	 * 文件上传
	 * @param data 文件字节数组
	 * @param path 文件路径
	 * @return 返回http地址
	 */
	public static String upload(byte[] data, String path) {
		return upload(data, "", path, true);
	}

	/**
	 * 文件上传
	 * @param data 文件字节数组
	 * @param path 文件路径
	 * @param enableHttpPrefix 是否启用文件前缀返回
	 * @return 返回http地址
	 */
	public static String upload(byte[] data, String path, boolean enableHttpPrefix) {
		return upload(data, "", path, enableHttpPrefix);
	}

	/**
	 * 文件上传
	 * @param data 文件字节数组
	 * @param savePrefixPath 保存地址前缀
	 * @param path 文件路径
	 * @return 返回http地址
	 */
	public static String upload(byte[] data, String savePrefixPath, String path) {
		return upload(data, savePrefixPath, path, true);
	}

	/**
	 * 文件上传
	 * @param data 文件字节数组
	 * @param savePrefixPath 保存地址前缀
	 * @param path 文件路径
	 * @param enableHttpPrefix 是否启用文件前缀返回
	 * @return 返回http地址
	 */
	public static String upload(byte[] data, String savePrefixPath, String path, boolean enableHttpPrefix) {
		String url = "";
		if (localEnabled) {
			url = localFileOperator.upload(data, savePrefixPath, path);
			log.debug("local oss upload url: {}", url);
		}
		if (ftpEnabled) {
			if (StringUtils.isNotBlank(url)) {
				url = ftpFileOperator.upload(data, "", url, false);
			}
			else {
				url = ftpFileOperator.upload(data, savePrefixPath, path);
			}
			log.debug("ftp oss upload url: {}", url);
		}
		if (aliEnabled) {
			if (StringUtils.isNotBlank(url)) {
				url = aliFileOperator.upload(data, "", url, false);
			}
			else {
				url = aliFileOperator.upload(data, savePrefixPath, path);
			}
			log.debug("ali oss upload url: {}", url);
		}
		if (enableHttpPrefix) {
			return getHttpPrefix() + url;
		}
		else {
			return url;
		}
	}

	/**
	 * 获取文件上传访问地址前缀
	 * @return 文件访问地址前缀
	 */
	public static String getHttpPrefix() {
		String httpPrefix = "";
		if (localEnabled) {
			httpPrefix = localFileOperator.getHttpPrefix();
		}
		if (ftpEnabled) {
			httpPrefix = ftpFileOperator.getHttpPrefix();
		}
		if (aliEnabled) {
			httpPrefix = aliFileOperator.getHttpPrefix();
		}
		return httpPrefix;
	}

}
