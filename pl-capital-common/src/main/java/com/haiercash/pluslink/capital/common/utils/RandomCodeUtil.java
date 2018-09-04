package com.haiercash.pluslink.capital.common.utils;

import java.util.Random;

/**
 * 随机数生成工具类
 *
 * @date 2016/12/19
 */
public class RandomCodeUtil {
    /**
     * 生成n位随机数
     *
     * @return
     */
    public static String getRandomNumber(int length) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; ++i) {
            builder.append(String.valueOf(random.nextInt(10)));
        }
        return builder.toString();
    }
}
