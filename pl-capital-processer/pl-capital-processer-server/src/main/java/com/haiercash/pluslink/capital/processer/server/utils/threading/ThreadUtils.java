package com.haiercash.pluslink.capital.processer.server.utils.threading;


public final class ThreadUtils {
    /**
     * 休眠制定的毫秒数,如果失败直接返回
     *
     * @param millis 休眠毫秒数
     */
    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception ignored) {
        }
    }
}
