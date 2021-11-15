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

package com.gitee.fubluesky.kernel.validator.api.utils;

import com.gitee.fubluesky.kernel.validator.api.exception.ParamValidateException;
import com.gitee.fubluesky.kernel.validator.api.exception.enums.ValidatorExceptionEnum;
import lombok.experimental.UtilityClass;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-09 10:01
 */
@UtilityClass
public class ValidatorUtil {

	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	/**
	 * 校验对象
	 * @param object 待校验对象
	 * @param groups 待校验的组
	 */
	public void validateEntity(Object object, Class<?>... groups) {
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
		if (!constraintViolations.isEmpty()) {
			StringBuilder msg = new StringBuilder();
			for (ConstraintViolation<Object> constraint : constraintViolations) {
				msg.append(constraint.getMessage()).append("; ");
			}
			String message = ValidatorExceptionEnum.VALIDATED_RESULT_ERROR.getMessage() + msg.toString();
			throw new ParamValidateException(ValidatorExceptionEnum.VALIDATED_RESULT_ERROR, message);
		}
	}

}
