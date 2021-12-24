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

package com.gitee.fubluesky.kernel.file.local.pojo;

import com.gitee.fubluesky.kernel.file.api.constants.FileConstants;
import com.gitee.fubluesky.kernel.file.api.exception.FileException;
import com.gitee.fubluesky.kernel.file.api.exception.enums.FileExceptionEnum;
import com.gitee.fubluesky.kernel.file.api.pojo.FileProperties;
import com.gitee.fubluesky.kernel.file.api.utils.FileUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-02 9:21
 */
@Slf4j
@EqualsAndHashCode(callSuper = true)
@Data
public class LocalFileProperties extends FileProperties {

	/**
	 * 是否启用
	 */
	private Boolean enabled = false;

	private String mvcPath = "file";

	/**
	 * 本地文件存储位置（linux）
	 */
	private String localFileSavePathLinux = "/tmp/tempFilePath";

	/**
	 * 本地文件存储位置（windows）
	 */
	private String localFileSavePathWin = "D:\\tempFilePath";

	public String getSavePath() {
		try {
			String os = System.getProperty("os.name");
			String savePath;
			if (os.startsWith(FileConstants.OS_WINDOWS)) {
				String savePathWindows = getLocalFileSavePathWin();
				if (!FileUtil.exist(savePathWindows)) {
					FileUtil.mkdir(savePathWindows);
				}
				savePath = savePathWindows;
			}
			else {
				String savePathLinux = getLocalFileSavePathLinux();
				if (!FileUtil.exist(savePathLinux)) {
					FileUtil.mkdir(savePathLinux);
				}
				savePath = savePathLinux;
			}
			return savePath;
		}
		catch (Exception e) {
			log.error("get save path fail", e);
			throw new FileException(FileExceptionEnum.LOCAL_INIT_ERROR);
		}
	}

}
