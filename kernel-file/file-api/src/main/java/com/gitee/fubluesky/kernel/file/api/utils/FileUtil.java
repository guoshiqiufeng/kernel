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

package com.gitee.fubluesky.kernel.file.api.utils;

import com.gitee.fubluesky.kernel.file.api.exception.FileException;
import com.gitee.fubluesky.kernel.file.api.exception.enums.FileExceptionEnum;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-12-24 11:14
 */
@Slf4j
@UtilityClass
public class FileUtil {

	/**
	 * 文件是否存在
	 * @param filePath 文件路径
	 * @return true 存在， false 不存在
	 */
	public static boolean exist(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}

	/**
	 * 创建文件夹列表
	 * @param filePath 文件路径
	 */
	public static void mkdir(String filePath) {
		File file = new File(filePath);
		file.mkdirs();
	}

	/**
	 * 获取文件流
	 * @param filePath 文件路径
	 * @return 文件流
	 */
	public static byte[] readBytes(String filePath) {
		InputStream inputStream = readInputStream(filePath);
		return IoUtils.readStreamAsByteArray(inputStream);
	}

	/**
	 * 获取文件流
	 * @param filePath 文件路径
	 * @return 文件流
	 */
	public static InputStream readInputStream(String filePath) {
		InputStream inputStream = null;
		File file = new File(filePath);
		if (!file.exists()) {
			throw new FileException(FileExceptionEnum.FILE_NOT_FOUND);
		}
		try {
			inputStream = new FileInputStream(file);
		}
		catch (FileNotFoundException e) {
			throw new FileException(FileExceptionEnum.FILE_NOT_FOUND);
		}
		return inputStream;
	}

	public static File createNewFile(String filePath) {
		File file = new File(filePath);
		try {
			// 创建文件夹
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			// 删除文件
			if (file.exists()) {
				file.delete();
			}
			// 重新创建
			file.createNewFile();
		}
		catch (IOException e) {
			log.error("FileUtil createNewFile error: {}", e.getMessage());
		}
		return file;
	}

	/**
	 * 存储文件流
	 * @param inputStream 文件流
	 * @param filePath 文件路径
	 * @return true 存储成功；false 存储失败
	 */
	public static boolean writeInputStream(InputStream inputStream, String filePath) {
		try {
			File file = createNewFile(filePath);
			FileOutputStream out = new FileOutputStream(file);
			byte[] data = new byte[1024];
			int num = 0;
			while ((num = inputStream.read(data, 0, data.length)) != -1) {
				out.write(data, 0, num);
			}
			out.close();
			data = null;
			return true;
		}
		catch (Exception e) {
			log.error("FileUtil writeInputStream error: {}", e.getMessage());
			return false;
		}
	}

	/**
	 * 删除文件
	 * @param filePath 文件路径
	 */
	public static void del(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			return;
		}
		if (file.isFile()) {
			// 删除文件
			file.delete();
		}
	}

}
