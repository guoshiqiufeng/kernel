/*
 * Copyright (c) 2021-2024, fubluesky (fubluesky@foxmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gitee.fubluesky.kernel.sms.api.exception;

import com.gitee.fubluesky.kernel.core.exception.AbstractExceptionEnum;
import com.gitee.fubluesky.kernel.core.exception.ServiceException;
import com.gitee.fubluesky.kernel.sms.api.constants.SmsConstants;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-03 14:19
 */
public class SmsException extends ServiceException {

    public SmsException(AbstractExceptionEnum exception) {
        super(SmsConstants.MODULE_NAME, exception);
    }

    public SmsException(AbstractExceptionEnum exception, String message) {
        super(SmsConstants.MODULE_NAME, exception.getCode(), message);
    }

    public SmsException(String code, String message) {
        super(SmsConstants.MODULE_NAME, code, message);
    }

}
