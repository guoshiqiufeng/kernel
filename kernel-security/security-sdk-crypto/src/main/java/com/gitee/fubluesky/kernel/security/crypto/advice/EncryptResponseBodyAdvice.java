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
// import com.gitee.fubluesky.kernel.security.crypto.utils.NeedCrypto;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
// import org.springframework.core.MethodParameter;
// import org.springframework.http.MediaType;
// import org.springframework.http.converter.HttpMessageConverter;
// import org.springframework.http.server.ServerHttpRequest;
// import org.springframework.http.server.ServerHttpResponse;
// import org.springframework.web.bind.annotation.ControllerAdvice;
// import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
//
// import java.util.List;
//
/// **
// * @author yanghq
// * @version 1.0
// * @since 2021-11-10 16:49
// */
// @Slf4j
// @ControllerAdvice
// @ConditionalOnProperty(prefix = "kernel.crypto.response.encrypt", name = "enabled" ,
/// havingValue = "true", matchIfMissing = true)
// public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {
//
// @Value("${kernel.crypto.request.decrypt.charset:UTF-8}")
// private String charset = "UTF-8";
//
// @Autowired
// @Qualifier("rrCrypto")
// private Crypto crypto;
//
// @Override
// public boolean supports(MethodParameter returnType, Class<? extends
/// HttpMessageConverter<?>> converterType) {
// return true;
// }
//
// @Override
// public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType
/// selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
/// ServerHttpRequest request, ServerHttpResponse response) {
// boolean encrypt = NeedCrypto.needEncrypt(returnType);
// if( !encrypt ){
// return body;
// }
//
// if(!(body instanceof ResponseMsg)){
// return body;
// }
//
// //只针对ResponseMsg的data进行加密
// ResponseMsg responseMsg = (ResponseMsg) body;
// Object data = responseMsg.getData();
// if(null == data){
// return body;
// }
//
// String xx;
// Class<?> dataClass = data.getClass();
// if(dataClass.isPrimitive() || (data instanceof String)){
// xx = String.valueOf(data);
// }else {
// //JavaBean、Map、List等先序列化
// if(List.class.isAssignableFrom(dataClass)){
// xx = JsonUtil.serializeList((List<Object>) data);
// }else if(Map.class.isAssignableFrom(dataClass)){
// xx = JsonUtil.serializeMap((Map<String, Object>) data);
// }else {
// xx = JsonUtil.serializeJavaBean(data);
// }
// }
//
// responseMsg.setData(crypto.encrypt(xx, charset));
//
// return responseMsg;
// }
// }
