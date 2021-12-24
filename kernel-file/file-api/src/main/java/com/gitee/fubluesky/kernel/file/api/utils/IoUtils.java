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

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-07-31 16:08
 */
@Slf4j
public class IoUtils {

	public static byte[] readStreamAsByteArray(InputStream inputStream) {
		if (inputStream == null) {
			return new byte[0];
		}
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len;
			while ((len = inputStream.read(buffer)) != -1) {
				output.write(buffer, 0, len);
			}
			output.flush();
			return output.toByteArray();
		}
		catch (Exception e) {
			log.error("IoUtils readStreamAsByteArray error: {}", e.getMessage());
			return new byte[0];
		}
	}

}
