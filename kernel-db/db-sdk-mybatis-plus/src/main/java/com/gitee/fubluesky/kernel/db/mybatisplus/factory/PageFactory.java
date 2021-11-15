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

package com.gitee.fubluesky.kernel.db.mybatisplus.factory;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gitee.fubluesky.kernel.core.util.HttpServletUtil;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-07-22 15:37
 */
@UtilityClass
public class PageFactory {

	/**
	 * 每页条数
	 */
	private static final String PAGE_SIZE_PARAM_NAME = "limit";

	/**
	 * 当前页
	 */
	private static final String PAGE_NO_PARAM_NAME = "page";

	/**
	 * 默认分页,不指定排序
	 */
	public <T> Page<T> defaultPage() {
		return doPage(null, null, "", false);
	}

	/**
	 * 默认分页,指定排序 倒序
	 * @param orderField 排序字段
	 */
	public <T> Page<T> defaultPage(String orderField) {
		Map<String, Integer> map = getPageSizeAndPageNo();
		return doPage(null, null, orderField, false);
	}

	/**
	 * 默认分页,指定排序
	 * @param orderField 排序字段
	 * @param isAsc 是否正序排列；true 正序 false 倒序
	 */
	public <T> Page<T> defaultPage(String orderField, boolean isAsc) {
		return doPage(null, null, orderField, isAsc);
	}

	/**
	 * 默认分页,指定排序
	 * @param orderField 排序字段
	 * @param isAsc 是否正序排列；true 正序 false 倒序
	 */
	public <T> Page<T> page(int pageSize, int pageNo, String orderField, boolean isAsc) {
		return doPage(pageSize, pageNo, orderField, isAsc);
	}

	private Map<String, Integer> getPageSizeAndPageNo() {
		Map<String, Integer> maps = new ConcurrentHashMap<>(2);
		int pageSize = 20;
		int pageNo = 1;

		HttpServletRequest request = HttpServletUtil.getRequest();

		// 每页条数
		String pageSizeString = request.getParameter(PAGE_SIZE_PARAM_NAME);
		if (StringUtils.isNotEmpty(pageSizeString)) {
			pageSize = Integer.parseInt(pageSizeString);
		}

		// 第几页
		String pageNoString = request.getParameter(PAGE_NO_PARAM_NAME);
		if (StringUtils.isNotEmpty(pageNoString)) {
			pageNo = Integer.parseInt(pageNoString);
		}
		maps.put(PageFactory.PAGE_SIZE_PARAM_NAME, pageSize);
		maps.put(PageFactory.PAGE_NO_PARAM_NAME, pageNo);
		return maps;
	}

	private <T> Page<T> doPage(Integer pageSize, Integer pageNo, String orderField, boolean isAsc) {
		if (pageSize == null || pageNo == null) {
			Map<String, Integer> map = getPageSizeAndPageNo();
			pageSize = map.get(PageFactory.PAGE_SIZE_PARAM_NAME);
			pageNo = map.get(PageFactory.PAGE_NO_PARAM_NAME);
		}
		if (StringUtils.isEmpty(orderField)) {
			return new Page<>(pageNo, pageSize);
		}
		else {
			Page<T> page = new Page<>(pageNo, pageSize);
			if (isAsc) {
				page.addOrder(OrderItem.asc(orderField));
			}
			else {
				page.addOrder(OrderItem.desc(orderField));
			}
			return page;
		}
	}

}
