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

package com.gitee.fubluesky.kernel.excel.api.exception;

import com.gitee.fubluesky.kernel.core.exception.AbstractExceptionEnum;
import com.gitee.fubluesky.kernel.core.exception.ServiceException;
import com.gitee.fubluesky.kernel.excel.api.constants.ExcelConstants;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-11-18 15:34
 */
public class ExcelException extends ServiceException {

	public ExcelException(AbstractExceptionEnum exception) {
		super(ExcelConstants.EXCEL_MODULE_NAME, exception);
	}

	public ExcelException(AbstractExceptionEnum exception, String message) {
		super(ExcelConstants.EXCEL_MODULE_NAME, exception.getCode(), exception.getMessage() + message);
	}

	public ExcelException(String code, String message) {
		super(ExcelConstants.EXCEL_MODULE_NAME, code, message);
	}

}
