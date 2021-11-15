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

package com.gitee.fubluesky.kernel.db.api.pojo.page;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页返回对象
 *
 * @author yanghq
 * @version 1.0
 * @since 2021-07-22 10:08
 */
@Data
public class PageResult implements Serializable {

	private static final long serialVersionUID = -2146478495200872401L;

	/**
	 * 总记录数
	 */
	private int totalCount;

	/**
	 * 每页记录数
	 */
	private int pageSize;

	/**
	 * 总页数
	 */
	private int totalPage;

	/**
	 * 当前页数
	 */
	private int currPage;

	/**
	 * 列表数据
	 */
	private List<?> list;

	/**
	 * 分页
	 * @param list 列表数据
	 * @param totalCount 总记录数
	 * @param pageSize 每页记录数
	 * @param currPage 当前页数
	 */
	public PageResult(List<?> list, int totalCount, int pageSize, int currPage) {
		this.list = list;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currPage = currPage;
		this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
	}

}
