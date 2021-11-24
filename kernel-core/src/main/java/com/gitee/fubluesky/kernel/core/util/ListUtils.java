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

package com.gitee.fubluesky.kernel.core.util;

import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-14 11:41
 */
@UtilityClass
public class ListUtils {

	/**
	 * 判断是否不为空
	 * @param list list对象
	 * @return boolean 非空返回true,空则返回false
	 */
	public boolean isNotEmpty(List<?> list) {
		return list != null && list.size() > 0;
	}

	/**
	 * 判断是否为空
	 * @param list list对象
	 * @return boolean 空返回true,非空则返回false
	 */
	public boolean isEmpty(List<?> list) {
		return list == null || list.size() == 0;
	}

	/**
	 * 对象转换
	 * @param obj list对象
	 * @param clazz 转换的类
	 * @param <T> clazz的类
	 * @return list 转换后的list
	 */
	public <T> List<T> castList(Object obj, Class<T> clazz) {
		List<T> result = Lists.newArrayList();
		if (obj instanceof List<?>) {
			for (Object o : (List<?>) obj) {
				result.add(clazz.cast(o));
			}
			return result;
		}
		return null;
	}

}
