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
package com.gitee.fubluesky.kernel.auth;

import cn.hutool.core.util.StrUtil;
import com.gitee.fubluesky.kernel.auth.api.AuthApi;
import com.gitee.fubluesky.kernel.auth.api.LoginApi;
import com.gitee.fubluesky.kernel.auth.api.exception.AuthException;
import com.gitee.fubluesky.kernel.auth.api.exception.enums.AuthExceptionEnum;
import com.gitee.fubluesky.kernel.auth.api.password.PasswordEncryptApi;
import com.gitee.fubluesky.kernel.auth.api.pojo.AuthProperties;
import com.gitee.fubluesky.kernel.auth.api.pojo.login.LoginRequest;
import com.gitee.fubluesky.kernel.auth.api.pojo.login.LoginResponse;
import com.gitee.fubluesky.kernel.auth.api.pojo.login.LoginUser;
import com.gitee.fubluesky.kernel.auth.api.pojo.login.UserLoginInfoDTO;
import com.gitee.fubluesky.kernel.cache.api.CacheOperatorApi;
import com.gitee.fubluesky.kernel.core.util.HttpServletUtil;
import com.gitee.fubluesky.kernel.jwt.api.JwtApi;
import com.gitee.fubluesky.kernel.jwt.api.pojo.DefaultJwtPayload;
import com.gitee.fubluesky.kernel.security.api.CaptchaApi;
import com.gitee.fubluesky.kernel.security.api.pojo.CaptchaProperties;

/***
 * @author yanghq
 * @version 1.0
 * @since 2021-08-12 15:17
 */
public class AuthImpl implements AuthApi {

    private final AuthProperties authProperties;

    private final LoginApi loginApi;

    private final PasswordEncryptApi passwordEncryptApi;

    /**
     * 用户信息缓存
     */
    private final CacheOperatorApi<LoginUser> loginUserTokenCache;

    private final CacheOperatorApi<LoginUser> loginUserCache;

    private final CaptchaApi captchaApi;

    /**
     * 验证码配置
     */
    private final CaptchaProperties captchaProperties;

    private final JwtApi jwtApi;

    public AuthImpl(AuthProperties authProperties, LoginApi loginApi, PasswordEncryptApi passwordEncryptApi,
                    CacheOperatorApi<LoginUser> loginUserTokenCache, CacheOperatorApi<LoginUser> loginUserCache,
                    CaptchaApi captchaApi, CaptchaProperties captchaProperties, JwtApi jwtApi) {
        this.authProperties = authProperties;
        this.loginApi = loginApi;
        this.passwordEncryptApi = passwordEncryptApi;
        this.loginUserTokenCache = loginUserTokenCache;
        this.loginUserCache = loginUserCache;
        this.captchaApi = captchaApi;
        this.captchaProperties = captchaProperties;
        this.jwtApi = jwtApi;
    }

    /**
     * 登录
     *
     * @param loginRequest 登录信息
     * @return token
     */
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return login(loginRequest, true);
    }

    /**
     * 登录 逻辑处理 不链接数据库 进行校验 调用此方法前 自行进行数据库数据校验等操作
     *
     * @param loginRequest     登录信息
     * @param validatePassword 是否校验密码
     * @return LoginResponse
     */
    @Override
    public LoginResponse login(LoginRequest loginRequest, boolean validatePassword) {
        return doLogin(loginRequest, validatePassword);
    }

    /**
     * 退出登录
     */
    @Override
    public void logout() {
        String token = loginApi.getToken();
        if (StrUtil.isEmpty(token)) {
            return;
        }
        Long userId = loginApi.getLoginUser().getUserId();
        loginUserTokenCache.delete(token);
        if (userId != null) {
            loginUserCache.delete(userId + "");
        }
    }

    /**
     * 登录 实际逻辑操作
     *
     * @param loginRequest     请求参数
     * @param validatePassword 是否校验密码
     * @return 登录结果
     */
    private LoginResponse doLogin(LoginRequest loginRequest, Boolean validatePassword) {
        if (loginRequest == null || StrUtil.isEmpty(loginRequest.getAccount()) || StrUtil.isEmpty(loginRequest.getPassword())) {
            throw new AuthException(AuthExceptionEnum.USERNAME_PASSWORD_EMPTY);
        }

        loginRequest.setAccount(loginRequest.getAccount().trim());
        loginRequest.setPassword(loginRequest.getPassword().trim());

        if (captchaProperties.getCaptchaEnable()) {
            String captchaKey = loginRequest.getCaptchaKey();
            String captchaCode = loginRequest.getCaptchaCode();

            if (StrUtil.isEmpty(captchaKey) || StrUtil.isEmpty(captchaCode)) {
                throw new AuthException(AuthExceptionEnum.CAPTCHA_EMPTY);
            }
            if (!captchaApi.validateCaptcha(captchaKey, captchaCode)) {
                throw new AuthException(AuthExceptionEnum.CAPTCHA_ERROR);
            }
        }

        // 获取用户信息
        UserLoginInfoDTO userValidateInfo = loginRequest.getUserServiceApi()
                .getUserLoginInfo(loginRequest.getAccount());

        // 校验用户密码是否正确
        if (validatePassword) {
            Boolean checkResult = passwordEncryptApi.matches(loginRequest.getPassword(),
                    userValidateInfo.getUserPassword());
            if (!checkResult) {
                throw new AuthException(AuthExceptionEnum.USERNAME_PASSWORD_ERROR);
            }
        } else {
            // 自定义密码校验
            loginRequest.getUserServiceApi().validatePassword(loginRequest.getPassword(), userValidateInfo);
        }

        LoginUser loginUser = userValidateInfo.getLoginUser();

        String appId = HttpServletUtil.getRequest().getHeader(authProperties.getJwtAppIdHeaderName());
        // 生成用户的token
        DefaultJwtPayload defaultJwtPayload = new DefaultJwtPayload(loginUser.getUserId(), loginUser.getAccount(),
                appId, loginRequest.getRememberMe());

        String jwtToken = createToken(loginUser, defaultJwtPayload);

        return new LoginResponse(loginUser, jwtToken, defaultJwtPayload.getExpirationDate());
    }

    /**
     * 创建用户token 设置缓存
     *
     * @param loginUser  用户信息
     * @param rememberMe 是否记住
     * @return token
     */
    @Override
    public String createToken(LoginUser loginUser, Boolean rememberMe) {
        String appId = HttpServletUtil.getRequest().getHeader(authProperties.getJwtAppIdHeaderName());
        DefaultJwtPayload defaultJwtPayload = new DefaultJwtPayload(loginUser.getUserId(), loginUser.getAccount(),
                appId, rememberMe);
        return createToken(loginUser, defaultJwtPayload);
    }

    private String createToken(LoginUser loginUser, DefaultJwtPayload defaultJwtPayload) {
        String jwtToken = jwtApi.generateToken(defaultJwtPayload);
        String token = authProperties.getTokenPrefix() + jwtToken;
        loginUser.setToken(token);
        if (defaultJwtPayload.getExpirationDate() != null && defaultJwtPayload.getExpirationDate() != 0) {
            long expire = defaultJwtPayload.getExpirationDate() - System.currentTimeMillis();
            // token cache
            loginUserTokenCache.add(jwtToken, loginUser, expire);
        }
        return jwtToken;
    }

}
