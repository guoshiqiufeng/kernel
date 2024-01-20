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
package com.gitee.fubluesky.kernel.core.util;

import lombok.experimental.UtilityClass;

/**
 * 随机数生成
 *
 * @author yanghq
 * @version 1.0
 * @since 2021-08-17 9:59
 */
@UtilityClass
public class RandomUtil {

    /***
     * 产生四位随机数字
     * @return 随机数字
     */
    public String get4Random() {
        return getRandom(4);
    }

    /***
     * 产生四位随机数字
     * @return 随机数字
     */
    public String get6Random() {
        return getRandom(6);
    }

    /***
     * 产生四位随机数字
     * @return 随机数字
     */
    public String get8Random() {
        return getRandom(8);
    }

    /**
     * 产生随机数字
     *
     * @param num 位数
     * @return 随机数字
     */
    public String getRandom(int num) {
        int[] a = new int[num];
        StringBuilder ss = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            int str = (int) (10 * (Math.random()));
            ss.append(str);
        }
        return ss.toString();
    }

}
