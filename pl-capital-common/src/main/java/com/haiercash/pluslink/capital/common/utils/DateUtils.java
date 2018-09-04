package com.haiercash.pluslink.capital.common.utils;

import com.haiercash.pluslink.capital.common.exception.RedisException;
import com.haiercash.pluslink.capital.enums.RedisStatusEnum;
import com.haiercash.pluslink.capital.enums.TimeEnum;
import org.apache.commons.lang3.StringUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具
 *
 * @author: jiangkeliang@haiercash.com
 * @since: 12-12-3 下午5:18
 * @version: 1.0.0
 */
public class DateUtils {

    private static String PATTERN = "yyyyMMddHHmmss";

    private static String DATEPATTERN = "yyyy-MM-dd";

    /**
     * 解析日期 默认格式yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static Date parseString(String date) {
        return parseString(date, PATTERN);
    }


    /**
     * 解析日期
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Date parseString(String date, String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            pattern = PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date back = null;
        try {
            back = sdf.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return back;
    }


    /**
     * 日期格式化 默认格式yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return formatDate(date, PATTERN);
    }

    /**
     * 日期格式化
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            pattern = PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String back = sdf.format(date);
        return back;
    }

    /**
     * @param date
     * @return boolean
     * @description 判断日期是否是当天
     * @author Richard Core
     * @date 2016/10/10  11:17
     * @method isToday
     */
    public static boolean isToday(Date date) {
        if (date == null) {
            return false;
        }
        Date now = new Date();
        String nowDate = formatDate(now, "yyyyMMdd");
        Date begin = parseString(nowDate + "000000", PATTERN);
        Date end = parseString(nowDate + "235959", PATTERN);
        if (begin.before(date) && date.before(end)) {
            return true;
        }
        if (begin.equals(date) || end.equals(date)) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前日期对应的会计日期
     * @return
     */
    public static Date getWorkDate() {

        return getWorkDate(new Date());
    }

    /**
     * 通过交易日期计算会计日期
     * @return
     */
    public static Date getWorkDate(Date tradeTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(tradeTime);
        int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);                  //获取交易日期的时（24小时制）
        if (hourOfDay >= 22) {
            cal.add(Calendar.DAY_OF_MONTH, 1);                                  //当交易日期为22时以后，计入下一会计日期
        }
        //将时分秒设为0
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    /**
     * 今天的日期
     * @return
     */
    public static Date getTodayDate() {
        String strDate = null;
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        strDate = df.format(d);
        Date date = null;
        try {
            date = df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 昨天的日期
     * @return
     */
    public static Date getYesterDate() {
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
        date=calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = null;
        date1 = formatter.format(date);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = null;
        try {
            date2 = df.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date2;
    }

    /**
     * 计算两个日期间相差天数（逾期天数）
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getOverdueDays(Date startDate, Date endDate) {
        if(startDate.getTime() - endDate.getTime() <= 0){
            return 0;
        }else{
            Double intervals = ((startDate.getTime() - endDate.getTime())/(1000*3600*24.0));
            return (int)Math.ceil(intervals);
        }
    }

    //转化为妙
    public static int changeSecond(int time,TimeEnum type){
        int timeParam = 0;
        try{
            int second = time;
            int minute = second * 60;
            int hour = minute * 60;
            int day = hour * 24;
            int month = day * 30;
            int year = day * 365;
            if(TimeEnum.year.getCode().equals(type.getCode())){//年
                timeParam = year;
            }
            if(TimeEnum.month.getCode().equals(type.getCode())){//年
                timeParam = month;
            }
            if(TimeEnum.day.getCode().equals(type.getCode())){//年
                timeParam = day;
            }
            if(TimeEnum.hour.getCode().equals(type.getCode())){//年
                timeParam = hour;
            }
            if(TimeEnum.minute.getCode().equals(type.getCode())){//年
                timeParam = minute;
            }
            if(TimeEnum.second.getCode().equals(type.getCode())){//年
                timeParam = time;
            }
        }catch(Exception e){
            throw new RedisException(RedisStatusEnum.redis_time_change_error, ("time = " + time + ",type = " + type));
        }
        return timeParam;
    }

   /* public static void main(String arge[]){
        AccountTradeFinishedFlow accountTradeFinishedFlow = new AccountTradeFinishedFlow();

        accountTradeFinishedFlow.setId("1");
        accountTradeFinishedFlow.setRtnFeeCrmNo("111");
        List<AccountTradeFinishedFlow> items = new ArrayList<AccountTradeFinishedFlow>();

        items.forEach(AccountTradeFinishedFlow->AccountTradeFinishedFlow.getId());

//lambda
//Output : A,B,C,D,E
        items.forEach(item->System.out.println(item));

        items.forEach(System.out::println);
    }*/
}
