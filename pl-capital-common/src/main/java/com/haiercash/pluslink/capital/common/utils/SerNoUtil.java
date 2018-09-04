package com.haiercash.pluslink.capital.common.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * serno util.
 *
 * @author Liu qingxiang
 * @version 2.0.0
 */
public class SerNoUtil {

    private static String date = null;
    private static AtomicInteger atomicInteger;
    private static String mac = null;

    static {
        date = DateUtils.formatDate(new Date(), "HHmmss");
        atomicInteger = new AtomicInteger(0);
        mac = getLocalMac();
    }

    private static String getLocalMac() {
        try {
            InetAddress ia = InetAddress.getLocalHost();

            byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();

            StringBuffer sb = new StringBuffer();

            for(int i=0;i<mac.length;i++){
                if(i!=0){
                    sb.append("-");
                }
                String s = Integer.toHexString(mac[i] & 0xFF);
                sb.append(s.length()==1?0+s:s);
            }

            return sb.toString().toUpperCase().replace("-", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("000%9d", new Random().nextInt(999999999));
    }

    /***
     * 生成规则:
     * <p>
     *     12位mac地址 + 10位长度唯一值,
     *     如：04121642510000000000
     * </p>
     */
    public static String getNewSerNo() {
        DateFormat format = new SimpleDateFormat("HHmmss");
        String today = format.format(new Date());
        if (!date.equals(today)) {
            atomicInteger.set(0);
            date = format.format(new Date());
        }
        return String.format("%s%s%010d", mac, date, atomicInteger.getAndIncrement());
    }

}
