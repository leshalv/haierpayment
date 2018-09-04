package com.haiercash.pluslink.capital.router.server.utils;

import cn.jbinfo.api.exception.ApiException;
import com.haiercash.pluslink.capital.common.exception.PlCapitalException;
import com.haiercash.pluslink.capital.common.utils.BaseService;
import com.haiercash.pluslink.capital.constant.CommonConstant;
import com.haiercash.pluslink.capital.router.server.api.controller.enums.ApiReturnCodeEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 参数工具类
 * @author WDY
 * @date 2018-07-14
 * @rmk
 */
public class ParamUtil extends BaseService{

    private final static Logger logger = LoggerFactory.getLogger(ParamUtil.class);

    /**
     * 参数长度校验
     * @author WDY
     * @date 20180714
     * @rmk
     */
    public static String checkApiParamLength(String param,int length,String message,String serNo) {
        String paramMsg = "(" + param + ")";
        if(null == param || "".equals(param.trim())){
            logger.error("【" + serNo + "】" + message + paramMsg +  "不能为空");
            throw new ApiException(ApiReturnCodeEnums.null_filed.getCode(),"," + message + paramMsg);
        }
        if("".equals(param.trim()) || length < param.length()){
            logger.error("【" + serNo + "】" + message + paramMsg +  "最大长度不能超过【" + length + "】位");
            throw new ApiException(ApiReturnCodeEnums.overlong_filed.getCode(),"," + message + paramMsg);
        }
        return param.trim();
    }

    /**
     * 交易流水表检查参数是否为空(BigDecimal验证)
     * @author WDY
     * @date 20180714
     * @rmk
     */
    public static BigDecimal checkApiParamLength(String param, int length, int smallNum, String message,String serNo) {
        String paramMsg = "(" + param + ")";
        if(null == param || "".equals(param.trim())){
            logger.error("【" + serNo + "】" + message + paramMsg +  "不能为空");
            throw new ApiException(ApiReturnCodeEnums.null_filed.getCode(),"," + message + paramMsg);
        }
        int smallCheckLength = length + 1;
        if(param.indexOf(".") != -1){//存在小数位
            if("".equals(param.trim()) || smallCheckLength < param.length()){
                logger.error("【" + serNo + "】" + message + paramMsg +  "有小数位时最大长度不能超过【" + length + "】位");
                throw new ApiException(ApiReturnCodeEnums.overlong_filed.getCode(),"," + message + paramMsg);
            }
        }else{
            if("".equals(param.trim()) || length < param.length()){
                logger.error("【" + serNo + "】" + message + paramMsg +  "无小数位时最大长度不能超过【" + length + "】位");
                throw new ApiException(ApiReturnCodeEnums.overlong_filed.getCode(),"," + message + paramMsg);
            }
        }
        BigDecimal bigParam;
        try{
            bigParam = new BigDecimal(param.trim());
        }catch(Exception e){
            logger.error("【" + serNo + "】" + message + paramMsg +  "不能是金额类型");
            throw new ApiException(ApiReturnCodeEnums.error_filed.getCode(),"," + message + paramMsg);
        }

        BigDecimal settleBigParam = bigParam.setScale(0,BigDecimal.ROUND_DOWN);
        boolean flag = String.valueOf(settleBigParam).length() > (length - smallNum);

        if((length + 1 < String.valueOf(bigParam).length()) || bigParam.compareTo(bigParam.setScale(smallNum,BigDecimal.ROUND_DOWN)) != 0 || flag){
            logger.error("【" + serNo + "】" + message + paramMsg +  "最大长度不能超过" + length + "位,小数长度不能超过" + smallNum + "位");
            throw new ApiException(ApiReturnCodeEnums.overlong_filed.getCode(),"," + message + paramMsg);
        }
        return bigParam.setScale(smallNum,BigDecimal.ROUND_DOWN);
    }

    /**返回yyyyMMddhhmmss**/
    public static String changeDateTime(Date date){
        return getDate(date,CommonConstant.DATE_PATTERN_YYYYMMDD) + getTime(date,CommonConstant.DATE_PATTERN_HHMMSS);
    }

    /**返回yyyyMMddhhmmss**/
    public static Long changeDateTimeLong(Date date){
        return Long.parseLong(changeDateTime(date));
    }

    /**返回hhmmss**/
    public static Long changeTimeLong(Date date){
        return Long.parseLong(getTime(date,CommonConstant.DATE_PATTERN_HHMMSS));
    }

    /**获取当前系统日期**/
    private static String getDate(Date date,String pattern){
        SimpleDateFormat dt = new SimpleDateFormat(pattern);
        return dt.format(date);
    }

    /**获取当前时间**/
    private static String getTime(Date date,String pattern){
        SimpleDateFormat tm = (SimpleDateFormat) DateFormat.getDateInstance();
        tm.applyPattern(pattern);
        return tm.format(date);
    }

    /**获取Id的值并返回**/
    public static String getIdByData(Object obj){
        String id = null;
        for(Field f : obj.getClass().getSuperclass().getSuperclass().getDeclaredFields()){
            f.setAccessible(true); //设置私有变量为可访问
            if("id".equals(f.getName())){
                String key;
                try{
                    key = f.get(obj).toString();
                }catch(Exception e) {
                    logger.error("转换缓存参数异常。。。obj = " + obj.toString() + ",Message = " + e.getMessage(),e);
                    throw new PlCapitalException("转换缓存参数异常。。。obj = " + obj.toString() + ",Message = " + e.getMessage());
                }
                id = key;
            }
        }
        return id;
    }

    /**
     *
     * 方法简述：
     * <p>用于字符串格式补充<br>
     * @Author: huangyao
     * @Since: 2014-1-9
     * @param s : 待处理字符串
     * @param len : 长度
     * @param type: 类型 (int 0:后补space；1：后补0；2：前补space；3：前补0)
     * @return
     */
    public static  String formatString(String s, int len, int type) {
        String str = s == null ? "" : s;
        if (str.length() > len) {
            return str.substring(0, len);
        }
        StringBuilder buffer = new StringBuilder(len);
        char[] array = new char[len-str.length()];
        switch (type) {
            case 0:
                Arrays.fill(array, ' ');
                buffer.append(str).append(array);
                break;
            case 1:
                Arrays.fill(array, '0');
                buffer.append(str).append(array);
                break;
            case 2:
                Arrays.fill(array, ' ');
                buffer.append(array).append(str);
                break;
            case 3:
                Arrays.fill(array, '0');
                buffer.append(array).append(str);
                break;
            default:
                break;
        }
        return buffer.toString();
    }

    /**校验是否超出业务处理时间**/
    public static boolean checkBusiTime(String noBusiStartTime,String noBusiEndTime,Long timeNow,String applSeq){
        boolean flag = false;
        Long noStartTime = Long.parseLong(noBusiStartTime);
        Long noEndTime = Long.parseLong(noBusiEndTime);
        if(noEndTime < noStartTime){//跨天
            // 业务每日暂停开始时间 <= 当前时间 <= 235959 ||
            // 000000 <= 当前时间 <= 业务每日暂停结束时间
            if((noStartTime <= timeNow && CommonConstant.EVERY_DAY_END_TIME >= timeNow) ||
                    (CommonConstant.EVERY_DAY_STAET_TIME <= timeNow && noEndTime >= timeNow)){
                flag = true;
            }
        }else{//没有跨天
            // 业务每日暂停开始时间 <= 当前时间 <= 业务每日暂停结束时间
            if(noStartTime <= timeNow && noEndTime >= timeNow){
                flag = true;
            }
        }
        logger.info("Router：applSeq(" + applSeq + ")校验是否超出业务处理时间," +
                "业务每日暂停开始时间【" + noBusiStartTime + "】," +
                "业务每日暂停结束时间【"+ noBusiEndTime + "】," +
                "当前时间【"+ timeNow +"】," +
                "判断结果【"+ flag +"】");
        return flag;
    }


    public static void checkInterVersion(String interVersion,String reqVersion,String message){
        boolean versionFlag = compareVersion(interVersion,reqVersion);
        logger.info("{}请求版本号：[version:{}],校验结果[{}]",message,reqVersion,versionFlag);
        if(!versionFlag){
            throw new ApiException(ApiReturnCodeEnums.error_servicename.getCode(),",接口版本号不一致,禁止调用");
        }
    }

    /**版本号校验**/
    private static boolean compareVersion(String interVersion,String reqVersion){
        boolean flag = interVersion.equals(reqVersion);
        //TODO
        return flag;
    }

    /**获取机器地址和端口**/
    public static String[] getIpAndHostName(){
        String localIp = null;
        String localhost = null;
        try{
            InetAddress inetAddress =  InetAddress.getLocalHost();
            localIp = inetAddress.getHostAddress();
            localhost = inetAddress.getHostName();
        }catch(Exception e){
            logger.info("获取本机IP信息异常,异常信息{}", e.getMessage(),e);
        }
        if(null == localIp){
            localIp = "";
        }
        if(null == localhost){
            localhost = "";
        }
        if(localIp.length() >30){
            localIp = localIp.substring(0,30);
        }
        if(localhost.length() >30){
            localhost = localhost.substring(0,30);
        }
        String[] result = {localIp,localhost};
        return result;
    }
}
