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
package com.gitee.fubluesky.kernel.security.sign.aspect;

import com.gitee.fubluesky.kernel.security.sign.annotation.Sign;
import com.gitee.fubluesky.kernel.security.sign.api.SignApi;
import com.gitee.fubluesky.kernel.security.sign.pojo.SignProperties;
import com.gitee.fubluesky.kernel.security.sign.utils.SignUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 签名切面
 *
 * @author yanghq
 * @version 1.0
 * @since 2021-11-11 13:04
 */
@Slf4j
@Aspect
@Order(2)
@Component
public class SignAspect {

    @Autowired
    private SignProperties signProperties;

    @Autowired
    private SignApi signApi;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restControllerPointcut() {
    }

    @Pointcut("execution(* com.gitee.fubluesky.kernel.db.mybatisplus.pojo.BaseRestController.*(..)))")
    public void baseControllerPointCut() {
    }

    @Before("restControllerPointcut() || baseControllerPointCut()")
    public void sign(JoinPoint pjPoint) throws Throwable {
        String method = httpServletRequest.getMethod();
        // 非get请求直接放行
        if (!HttpMethod.GET.name().equals(method)) {
            return;
        }
        // 配置未开启签名
        if (!signProperties.getEnabled()) {
            log.debug("signProperties is disabled");
            return;
        }
        // 配置未开启签名
        if (!signProperties.getRequestGetEnable()) {
            log.debug("signProperties requestGetEnable is disabled");
            return;
        }
        // 当前类未开启签名
        Sign classAnnotation = pjPoint.getTarget().getClass().getAnnotation(Sign.class);
        if (classAnnotation != null && !classAnnotation.value()) {
            log.debug("class is disabled");
            return;
        }
        // 未开启方法签名
        MethodSignature methodSignature = (MethodSignature) pjPoint.getSignature();
        Method pointMethod = pjPoint.getTarget().getClass().getMethod(methodSignature.getName(),
                methodSignature.getParameterTypes());
        Sign methodAnnotation = pointMethod.getAnnotation(Sign.class);
        if (methodAnnotation != null && !methodAnnotation.value()) {
            log.debug("method is disabled");
            return;
        }
        // 处理参数
        processParameter();
    }

    /**
     * 处理参数
     */
    private void processParameter() {
        String appId = httpServletRequest.getHeader(signProperties.getAppIdHeaderName());
        log.debug("appId: {}", appId);
        String sign = httpServletRequest.getHeader(signProperties.getSignHeaderName());
        log.debug("sign: {}", sign);
        String uri = httpServletRequest.getRequestURI();
        if (StringUtils.isNotBlank(uri)) {
            uri = uri.replace("//", "/");
        }
        log.debug("uri: {}", uri);
        String businessInfo = httpServletRequest.getQueryString();
        if (businessInfo == null) {
            businessInfo = "";
        }
        String body = SignUtils.getSignBody(uri + businessInfo, appId, signProperties);
        log.debug("body: {}", body);
        signApi.validateSign(sign, body);
    }

}
