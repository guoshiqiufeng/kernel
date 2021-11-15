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

package com.gitee.fubluesky.kernel.file.apk.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-19 10:09
 */
@SuppressWarnings("all")
@Data
public class ApkInfo {

	private String versionCode;

	private String versionName;

	private String apkPackage;

	private String minSdkVersion;

	private String apkName;

	private List uses_permission;

	private String url;

}
