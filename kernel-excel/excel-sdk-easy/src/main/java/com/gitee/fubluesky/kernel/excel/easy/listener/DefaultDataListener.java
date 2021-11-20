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

package com.gitee.fubluesky.kernel.excel.easy.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.gitee.fubluesky.kernel.validator.api.exception.ParamValidateException;
import com.gitee.fubluesky.kernel.validator.api.utils.ValidatorUtil;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-11-18 16:29
 */
@Getter
public class DefaultDataListener<T> extends AnalysisEventListener<T> {

	/**
	 * 实体
	 */
	private final List<T> dataList = Lists.newArrayList();

	@Override
	public void invoke(T data, AnalysisContext context) {
		Integer index = context.readRowHolder().getRowIndex();
		if (index != null) {
			index = index + 1;
			String message = "第" + index + "行";
			try {
				ValidatorUtil.validateEntity(data);
			}
			catch (ParamValidateException e) {
				e.setMessage(message + e.getMessage());
				throw e;
			}
		}
		dataList.add(data);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
	}

}
