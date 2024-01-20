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
package com.gitee.fubluesky.kernel.push.jpush;

import cn.hutool.core.util.StrUtil;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.GroupPushClient;
import cn.jpush.api.push.GroupPushResult;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import com.gitee.fubluesky.kernel.push.api.PushApi;
import com.gitee.fubluesky.kernel.push.jpush.pojo.JpushProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * 极光推送
 *
 * @author yanghq
 * @version 1.0
 * @since 2021-11-16 17:29
 */
@Slf4j
public class JpushService implements PushApi {

    /**
     * 一次推送最大数量 (极光限制1000)
     */
    private static final int MAX_SIZE = 800;

    private GroupPushClient groupPushClient = null;

    private JPushClient jPushClient = null;

    private JpushProperties jpushProperties;

    public JpushService() {
    }

    public JpushService(JpushProperties jpushProperties) {
        this.jpushProperties = jpushProperties;
        init();
    }

    private void init() {
        // 初始化
        try {
            if (StrUtil.isNotBlank(jpushProperties.getGroupAppKey())) {
                groupPushClient = new GroupPushClient(jpushProperties.getGroupSecret(),
                        jpushProperties.getGroupAppKey());
            } else {
                jPushClient = new JPushClient(jpushProperties.getSecret(), jpushProperties.getAppKey());
            }
        } catch (Exception e) {
            log.error("初始化推送服务失败。error:{}", e.getMessage());
        }
    }

    @Override
    public boolean pushMessage(List<String> accountNameList, String pushData) {
        String[] strArr = new String[accountNameList.size()];
        accountNameList.toArray(strArr);
        return this.pushMessage(pushData, strArr);
    }

    @Override
    public boolean pushMessageByAlias(List<String> aliasList, String pushData) {
        String[] strArr = new String[aliasList.size()];
        aliasList.toArray(strArr);

        return this.pushMessageByAlias(pushData, strArr);
    }

    /**
     * 发送消息
     *
     * @param msgContent 内容
     * @param alias      别名
     * @return 是否成功
     */
    private boolean pushMessageByAlias(String msgContent, String... alias) {
        return sendPush(PushPayload.newBuilder().setPlatform(Platform.android_ios()).setAudience(Audience.alias(alias))
                .setMessage(Message.content(msgContent)).build());
    }

    /**
     * 发送消息
     *
     * @param msgContent      内容
     * @param registrationIds 注册id
     * @return 是否成功
     */
    private boolean pushMessage(String msgContent, String... registrationIds) {
        return sendPush(PushPayload.newBuilder().setPlatform(Platform.android_ios())
                .setAudience(Audience.registrationId(registrationIds)).setMessage(Message.content(msgContent)).build());
    }

    /**
     * 发送推送
     *
     * @param pushPayload 载体
     * @return 是否成功
     */
    private boolean sendPush(PushPayload pushPayload) {
        log.info("发送极光推送请求: {}", pushPayload);
        try {
            if (groupPushClient != null) {
                GroupPushResult result = groupPushClient.sendGroupPush(pushPayload);
                for (Map.Entry<String, PushResult> entry : result.getAppResultMap().entrySet()) {
                    PushResult pushResult = entry.getValue();
                    PushResult.Error error = pushResult.error;
                    if (error != null) {
                        log.info("AppKey: " + entry.getKey() + " error code : " + error.getCode() + " error message: "
                                + error.getMessage());
                    } else {
                        log.info("AppKey: " + entry.getKey() + " sendno: " + pushResult.sendno + " msg_id:"
                                + pushResult.msg_id);
                    }
                }
            } else {
                PushResult result = jPushClient.sendPush(pushPayload);
                if (result != null && result.isResultOK()) {
                    log.info("极光推送请求成功: {}", result);
                    return true;
                } else {
                    log.info("极光推送请求失败: {}", result);
                    return false;
                }
            }
        } catch (APIConnectionException e) {
            log.error("极光推送连接异常: ", e);
        } catch (APIRequestException e) {
            log.error("极光推送请求异常: ", e);
        }
        return false;
    }

}
