/// *
// *
// * Copyright 2021 fubluesky.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// * https://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
// package com.gitee.fubluesky.kernel.security.crypto.advice;
//
// import com.gitee.fubluesky.kernel.security.crypto.Crypto;
// import com.gitee.fubluesky.kernel.security.crypto.utils.DecryptHttpInputMessage;
// import com.gitee.fubluesky.kernel.security.crypto.utils.NeedCrypto;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
// import org.springframework.core.MethodParameter;
// import org.springframework.http.HttpInputMessage;
// import org.springframework.http.converter.HttpMessageConverter;
// import org.springframework.web.bind.annotation.ControllerAdvice;
// import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
//
// import java.io.IOException;
// import java.lang.reflect.Type;
//
/// **
// * RequestBody请求参数解密
// * @author yanghq
// * @version 1.0
// * @since 2021-11-10 16:17
// */
// @Slf4j
// @ControllerAdvice
// @ConditionalOnProperty(prefix = "kernel.crypto.request.decrypt", name = "enabled" ,
/// havingValue = "true", matchIfMissing = true)
// public class DecryptRequestBodyAdvice implements RequestBodyAdvice {
//
// @Value("${kernel.crypto.request.decrypt.charset:UTF-8}")
// private String charset = "UTF-8";
//
// @Autowired
// @Qualifier("rrCrypto")
// private Crypto crypto;
//
// @Override
// public boolean supports(MethodParameter methodParameter, Type targetType, Class<?
/// extends HttpMessageConverter<?>> converterType) {
// return true;
// }
//
// @Override
// public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter
/// parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType)
/// throws IOException {
// if(NeedCrypto.needDecrypt(parameter) ){
// return new DecryptHttpInputMessage(inputMessage, charset, crypto);
// }
// return inputMessage;
// }
//
// @Override
// public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter
/// parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
// return body;
// }
//
// @Override
// public Object handleEmptyBody(Object body, HttpInputMessage inputMessage,
/// MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>>
/// converterType) {
// return body;
// }
// }
