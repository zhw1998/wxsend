package com.rwin.wxsend.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 日期帮助类
 *
 * @author chenli
 */
public class DateUtil {

	/**
	 * 星期 方式显示。星期一
	 */
	public final static int WeekType_xq = 2;
	/**
	 * 周 方式显示。如周一
	 */
	public final static int WeekType_z = 1;


	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	/**
	 * 一秒的毫秒数
	 */
	public static final long MILLIS_SECOND = 1000;

	/**
	 * 一分钟的毫秒数
	 */
	public static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

	/**
	 * 一小时的毫秒数
	 */
	public static final long MILLIS_HOUR = 60 * MILLIS_MINUTE;

	/**
	 * 一天的毫秒数
	 */
	public static final long MILLIS_DAY = 24 * MILLIS_HOUR;

	/**
	 * 一分钟的秒数
	 */
	public static final long MIN_SECOND = 60;

	/**
	 * 一小时的秒数
	 */
	public static final long SECOND_HOUR = 60 * MIN_SECOND;

	/**
	 * 默认的日期格式 yyyy-MM-dd
	 */
	public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	public final static String China_small_date_format = "yyyy年M月d日";

	public final static String Date_format = "M月d日";

	public final static String small_date_format = "yyyy-MM";
	/**
	 * 默认的日期格式 yyyy-MM-dd
	 */
	public final static String China_date_format = "yyyy年MM月dd日";
	/**
	 * 默认的日期格式 yyyy-MM-dd
	 */
	public final static String China_long_date_format = "yyyy年MM月dd日 HH:mm:ss";
	/**
	 * 日期格式 yyyy-MM-dd HH:mm:ss
	 */
	public final static String LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 日期格式 00-00-00 00:00:00
	 */
	public final static String None_long_date = "00-00-00 00:00:00";
	/**
	 * 日期格式 yyyyMMddHHmmss
	 */
	public final static String None_date_format = "yyyyMMddHHmmss";
	public final static String None_date_format_long = "yyyyMMddHHmmssSSS";
	public final static String China_time_12 = "h:mm";

	public final static String Suffix_dayStart = " 00:00:00";
	public final static String Suffix_dayEnd = " 23:59:59";


	private DateUtil() {
		// Not instantiable.
	}


	/**
	 * 取得当前日期
	 *
	 * @return Date 当前日期
	 */
	public static Date getCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 返回当前日期对应的默认格式的字符串OrganizeController
	 * yyyy-MM-dd 方式
	 *
	 * @return String 当前日期对应的字符串
	 */
	public static String getCurrentStringDate() {
		return convertDate2String(getCurrentDate(), DEFAULT_DATE_FORMAT);
	}

	/**
	 * 返回当前日期对应的指定格式的字符串
	 *
	 * @param dateFormat - 日期格式
	 * @return String 当前日期对应的字符串
	 */
	public static String getCurrentStringDate(String dateFormat) {
		return convertDate2String(getCurrentDate(), dateFormat);
	}

	/**
	 * 把格式yyyy-MM-dd 转换成 yyyy年MM月dd日的格式
	 * @param date 日期。必须是 yyyy-MM-dd的格式。否则返回原来的格式。
	 * @return String 当前日期对应的字符串
	 */
	public static String convertCNDate(String date) {
		Date date1 = convertString2Date(date, "yyyy-MM-dd");
		return convertDate2String(date1, "yyyy年MM月dd日");
	}

	/**
	 * 将日期转换成指定格式的字符串
	 *
	 * @param date       - 要转换的日期
	 * @param dateFormat - 日期格式
	 * @return String 日期对应的字符串
	 */
	public static String convertDate2String(Date date, String dateFormat) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = null;
		if (StringUtils.isNotBlank(dateFormat)) {
			try {
				sdf = new SimpleDateFormat(dateFormat);
			} catch (Exception e) {
				sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
			}
		} else {
			sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		}
		return sdf.format(date);
	}

	/**
	 * 将日期转换成指定格式的字符串（格式：yyyy-MM-dd）
	 *
	 * @param date - 要转换的日期
	 * @return String 日期对应的字符串
	 */
	public static String convertDate2String(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		return sdf.format(date);
	}

	/**
	 * 将日期转换成指定格式的字符串（格式：yyyy-MM-dd HH:mm:ss）
	 *
	 * @param date - 要转换的日期
	 * @return String 日期对应的字符串
	 */
	public static String convertDate2StringL(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(LONG_DATE_FORMAT);
		return sdf.format(date);
	}


	/**
	 * 将字符串转换成日期 （格式：yyyy-MM-dd）
	 *
	 * @param stringDate - 要转换的字符串格式的日期
	 * @return Date 字符串对应的日期
	 */
	public static Date convertString2Date(String stringDate) {
		return convertString2Date(stringDate, DEFAULT_DATE_FORMAT);
	}

	/**
	 * 将字符串转换成日期
	 *
	 * @param stringDate - 要转换的字符串格式的日期
	 * @param dateFormat - 要转换的字符串对应的日期格式
	 * @return Date 字符串对应的日期
	 */
	public static Date convertString2Date(String stringDate, String dateFormat) {
		SimpleDateFormat sdf = null;
		if (StringUtils.isNotBlank(dateFormat)) {
			try {
				sdf = new SimpleDateFormat(dateFormat);
			} catch (Exception e) {
				sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
			}
		} else {
			sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		}
		try {
			return sdf.parse(stringDate);
		} catch (ParseException pe) {
			return new Date(System.currentTimeMillis());
		}
	}

	/**
	 * 将一种格式的日期字符串转换成默认格式的日期字符串（格式：yyyy-MM-dd）
	 *
	 * @param oldStringDate - 要格式化的日期字符串
	 * @param oldFormat     - 要格式化的日期的格式！
	 * @return String 格式化后的日期字符串
	 */
	public static String formatStringDate(String oldStringDate, String oldFormat) {
		return convertDate2String(convertString2Date(oldStringDate, oldFormat), DEFAULT_DATE_FORMAT);
	}

	/**
	 * 将一种格式的日期字符串转换成另一种格式的日期字符串
	 *
	 * @param oldStringDate - 要格式化的日期字符串
	 * @param oldFormat     - 要格式化的日期的格式
	 * @param newFormat     - 格式化后的日期的格式
	 * @return String 格式化后的日期字符串
	 */
	public static String formatStringDate(String oldStringDate, String oldFormat, String newFormat) {
		return convertDate2String(convertString2Date(oldStringDate, oldFormat), newFormat);
	}

	/**
	 * 根据年份和月份判断该月有几天
	 *
	 * @param year  - 年份
	 * @param month - 月份
	 * @return int
	 */
	public static int days(int year, int month) {
		int total = 30;
		switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				total = 31;
				break;
			case 2:
				if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
					total = 29;
				} else {
					total = 28;
				}
				break;
			default:
				break;
		}
		return total;
	}

	/**
	* 功能描述：查询本周第一天
	* @author maojincheng
	* @date 2019/6/28 0028
	* @param
	* @return
	*/
	public static Date getWeekStart() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 根据年份和月份判断该月的第一天是星期几
	 *
	 * @param year  - 年份
	 * @param month - 月份
	 * @return int
	 */
	@SuppressWarnings("deprecation")
	public static int firstDayOfWeek(int year, int month) {
		Date firstDate = new Date(year - 1900, month - 1, 1);
		return firstDate.getDay();
	}

	/**
	 * 根据年份和月份判断该月的最后一天是星期几
	 *
	 * @param year  - 年份
	 * @param month - 月份
	 * @return int
	 */
	@SuppressWarnings("deprecation")
	public static int lastDayOfWeek(int year, int month) {
		Date lastDate = new Date(year - 1900, month - 1, days(year, month));
		return lastDate.getDay();
	}

	/**
	 * 给定一个日期,返回是一周中的第几天 星期日为每周的第一天,星期六为每周的最后一天
	 **/
	public static int dayOfWeek(Date date) {
		// 首先定义一个calendar，必须使用getInstance()进行实例化
		Calendar aCalendar = Calendar.getInstance();
		// 里面野可以直接插入date类型
		aCalendar.setTime(date);
		// 计算此日期是一周中的哪一天
		int x = aCalendar.get(Calendar.DAY_OF_WEEK);
		return x;
	}

	/**
	 * 获取当前年份
	 *
	 * @return int
	 */
	@SuppressWarnings("deprecation")
	public static int getCurrentYear() {
		return getCurrentDate().getYear() + 1900;
	}

	/**
	 * 获取当前月份(1-12)
	 *
	 * @return int
	 */
	@SuppressWarnings("deprecation")
	public static int getCurrentMonth() {
		return getCurrentDate().getMonth() + 1;
	}

	/**
	 * 获取当前季度
	 */
	public static int getCurrentQuarter() {
		return getCurrentMonth() / 4 + 1;
	}

	/**
	 * 获取上一个季度
	 */
	public static int getLastQuarter() {
		return getCurrentMonth() / 4;
	}

	/**
	 * 获取给定日期的下一个月的日期，返回格式为yyyy-MM-dd
	 *
	 * @param stringDate - 给定日期
	 * @param format     - 给定日期的格式
	 * @return String
	 */
	@SuppressWarnings("deprecation")
	public static String getNextMonth(String stringDate, String format) {
		Date date = convertString2Date(stringDate, format);
		int year = date.getYear() + 1900;
		int month = date.getMonth() + 1;
		if (month == 12) {
			year = year + 1;
			month = 1;
		} else {
			month = month + 1;
		}
		return year + "-" + (month < 10 ? "0" : "") + month + "-01";
	}

	/**
	 * 获取给定日期的下一个月的日期，返回格式为yyyy-MM-dd
	 *
	 * @param stringDate - 给定日期
	 * @return String
	 */
	public static String getNextMonth(String stringDate) {
		return getNextMonth(stringDate, DEFAULT_DATE_FORMAT);
	}

	/**
	 * 获取给定日期的前一天
	 *
	 * @param stringDate - 给定日期
	 * @param format     - 给定日期的格式
	 * @return String
	 */
	@SuppressWarnings("deprecation")
	public static String getBeforDate(String stringDate, String format) {
		Date date = convertString2Date(stringDate, format);
		int year = date.getYear() + 1900;
		int month = date.getMonth() + 1;
		int day = date.getDate();
		if (day == 1) {
			if (month == 1) {
				return (year - 1) + "-12-31";
			} else {
				month = month - 1;
				day = days(year, month);
				return year + "-" + (month < 10 ? "0" : "") + month + "-" + day;
			}
		} else {
			return year + "-" + (month < 10 ? "0" : "") + month + "-" + (day < 11 ? "0" : "") + (day - 1);
		}
	}

	/**
	 * 获取给定日期的前一天
	 *
	 * @param stringDate - 给定日期
	 * @return String
	 */
	public static String getBeforDate(String stringDate) {
		return getBeforDate(stringDate, DEFAULT_DATE_FORMAT);
	}

	/**
	 * 获取日期的前后日期; +为后 -为前
	 *
	 * @param date  当前日期
	 * @param day_i 加减天数  +为后 -为前
	 * @return
	 */
	public static final Date getEditDayDate(Date date, int day_i) {
		try {
			Calendar day = Calendar.getInstance();
			day.setTime(date);
			day.add(Calendar.DATE, day_i);
			return day.getTime();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 功能描述：获取某年第一天
	 *
	 * @param
	 * @return
	 * @author maojincheng
	 * @date 2019/5/5 0005
	 */
	public static Date getFirstDayOfYear(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		return calendar.getTime();
	}

	/**
	 * 功能描述：获取一年的最后一天
	 *
	 * @param
	 * @return
	 * @author maojincheng
	 * @date 2019/5/5 0005
	 */
	public static Date getLastDayOfYear(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		return calendar.getTime();
	}

	/**
	 * 得到当前日期的前后日期 +为后 -为前
	 *
	 * @param currentDate 当前日期
	 * @param day_i
	 * @param format      日期格式
	 * @return
	 */
	public static final String getBefDateString(String currentDate, int day_i, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = sdf.parse(currentDate);
			Date eDate = getEditDayDate(date, day_i);
			return sdf.format(eDate);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 得到一个月的天数
	 *
	 * @param dt 时间格式yyyyMMdd的字符串
	 */
	@SuppressWarnings("static-access")
	public static int getMonthDays(String dt) {
		return getMonthDays(dt, "yyyyMMdd");
	}

	/**
	 * 得到一个月的天数
	 *
	 * @param dt     时间字符串
	 * @param format 时间格式
	 */
	public static int getMonthDays(String dt, String format) {
		int result = -1;
		if (dt == null) {
			return result;
		}
		try {
			Date date = DateUtil.convertString2Date(dt, format);
			Calendar cld = Calendar.getInstance();
			cld.setTime(date);
			result=  cld.getActualMaximum(Calendar.DAY_OF_MONTH);
		} catch (Exception e) {
			logger.error("日期dt={},format={}获取一个月的天数异常", dt, format);
		}
		return result;
	}


	/**
	 * 得到当前日期的星期
	 *
	 * @param weekType {@link #WeekType_xq}
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getDateWeek(int weekType) {
		return getDateWeek(new Date(), weekType);
	}

	/**
	* 功能描述：获取日期星期几或周几
	* @param
	* @return
	* @author maojincheng
	* @date
	*/
	public static String getDateWeek(Date date, int weekType) {
		String result = "星期";
		if (weekType == WeekType_z) {
			result = "周";
		}
		int i = date.getDay();
		switch (i) {
			case 1:
				result = result + "一";
				break;
			case 2:
				result = result + "二";
				break;
			case 3:
				result = result + "三";
				break;
			case 4:
				result = result + "四";
				break;
			case 5:
				result = result + "五";
				break;
			case 6:
				result = result + "六";
				break;
			case 0:
				if (weekType == WeekType_z) {
					result = "周日";
				}else{
					result = "星期日";
				}
				break;
		}
		return result;
	}

	public static String getChinaDateStr(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("HH");
		String str = df.format(date);
		int a = Integer.parseInt(str);
		String datestr = "";
		if (a >= 0 && a <= 6) {
			datestr = "凌晨";
		}
		if (a > 6 && a <= 12) {
			datestr = "上午";
		}
		if (a > 12 && a <= 13) {
			datestr = "中午";
		}
		if (a > 13 && a <= 18) {
			datestr = "下午";
		}
		if (a > 18 && a <= 24) {
			datestr = "晚上";
		}
		return datestr;
	}

	/**
	 * 得到这个月的第一天
	 *
	 * @param date
	 **/
	public static Date getMonthFirstDay(Date date) {
		String strdate = convertDate2String(date);
		strdate = strdate.substring(0, 8) + "01";
		return convertString2Date(strdate);
	}


	/**
	 * 功能描述: 判断是否月初
	 *
	 * @param date
	 * @return
	 * @aurthor  zhangjianwei
	 * @date  2019/9/26
	 */
	public static boolean isMonthFirst(Date date){
		if(date.getDate() == 1){
			return true;
		}
		return false;
	}
	/**
	 * 功能描述: 判断是否月末
	 *
	 * @param date
	 * @return
	 * @aurthor  zhangjianwei
	 * @date  2019/9/26
	 */
	public static boolean isMonthLast(Date date){
		Date lastDay = getEditDayDate(date, 1);
		if(date.getMonth() != lastDay.getMonth()){
			return true;
		}
		return false;
	}
	/**
	 * 秒数转化为小时格式 HH:MM:SS
	 *
	 * @param sec 秒数
	 **/
	public static String convertSecToHour(int sec) {
		String time = "";
		int hour = 0;
		int minute = 0;
		int second = 0;
		hour = sec / 3600 > 0 ? sec / 3600 : 0;
		minute = (sec - hour * 3600) / 60 > 0 ? (sec - hour * 3600) / 60 : 0;
		second = sec - hour * 3600 - minute * 60 > 0 ? sec - hour * 3600 - minute * 60 : 0;
		String shour = String.valueOf(hour).length() < 2 ? "0" + String.valueOf(hour) : String.valueOf(hour);
		String sminute = String.valueOf(minute).length() < 2 ? "0" + String.valueOf(minute) : String.valueOf(minute);
		String ssecond = String.valueOf(second).length() < 2 ? "0" + String.valueOf(second) : String.valueOf(second);
		time = shour + ":" + sminute + ":" + ssecond;
		return time;
	}

	/**
	 * 把时间毫秒数转化为时间
	 *
	 * @param stime
	 * @return
	 * @author chenli
	 * @data Jan 17, 2013
	 */
	public static final Date millisToDate(String stime) {
		long time = Long.parseLong(stime);
		return new Date(time);
	}

	SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	SimpleDateFormat sFullFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static final String DATE_SEPARATOR = "-/";

	/**
	 * 作日期分析之用
	 */
	static StringTokenizer sToken;

	/**
	 * 将日期变为字符串格式 *
	 */
	public String format(GregorianCalendar pCal) {
		return sDateFormat.format(pCal.getTime());
	}

	/**
	 * 将时间转换成string类型 （格式yyyy-MM-dd）
	 */
	public String format(Date pDate) {
		return sDateFormat.format(pDate);
	}

	/**
	 * 将时间转换成string类型 （格式yyyy-MM-dd HH:mm:ss）
	 */
	public String fullFormat(Date pDate) {
		return sFullFormat.format(pDate);
	}

	/**
	 * 将字符串格式的日期转换为Calender
	 **/
	public static GregorianCalendar parse2Cal(String pDateStr) {
		sToken = new StringTokenizer(pDateStr, DATE_SEPARATOR);
		int vYear = Integer.parseInt(sToken.nextToken());
		// GregorianCalendar的月份是从0开始算起的，变态！！
		int vMonth = Integer.parseInt(sToken.nextToken()) - 1;
		int vDayOfMonth = Integer.parseInt(sToken.nextToken());
		return new GregorianCalendar(vYear, vMonth, vDayOfMonth);
	}

	/**
	 * 将字符串类型的日期(yyyy-MM-dd)转换成Date
	 **/
	public Date parse2Date(String pDate) {
		try {
			return sDateFormat.parse(pDate);
		} catch (ParseException ex) {
			return null;
		}
	}

	/**
	 * 给定两个时间相差的月数,String版
	 **/
	public static int monthsBetween(String pFormerStr, String pLatterStr) {
		GregorianCalendar vFormer = parse2Cal(pFormerStr);
		GregorianCalendar vLatter = parse2Cal(pLatterStr);
		return monthsBetween(vFormer, vLatter);
	}

	/**
	 * 给定两个时间相差的月数
	 */
	public static int monthsBetween(GregorianCalendar pFormer, GregorianCalendar pLatter) {
		GregorianCalendar vFormer = pFormer, vLatter = pLatter;
		boolean vPositive = true;
		if (pFormer.before(pLatter)) {
			vFormer = pFormer;
			vLatter = pLatter;
		} else {
			vFormer = pLatter;
			vLatter = pFormer;
			vPositive = false;
		}

		int vCounter = 0;
		while (vFormer.get(GregorianCalendar.YEAR) != vLatter.get(GregorianCalendar.YEAR)
				|| vFormer.get(GregorianCalendar.MONTH) != vLatter.get(GregorianCalendar.MONTH)) {
			vFormer.add(Calendar.MONTH, 1);
			vCounter++;
		}
		if (vPositive) {
			return vCounter;
		} else {
			return -vCounter;
		}
	}



	/**
	 * 转换当前时间为string类型 （格式yyyy-MM-dd HH:mm:ss）
	 *
	 * @return
	 * @author chenli
	 * @data Jan 18, 2013
	 */
	public static String nowLongFormat() {
		return DateUtil.convertDate2String(new Date(), LONG_DATE_FORMAT);
	}

	/**
	 * 当前日期前几天或者后几天的日期
	 *
	 * @param n 单位天
	 */
	public static String afterNDay(int n) {
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat(LONG_DATE_FORMAT);
		c.setTime(new Date());
		c.add(Calendar.DATE, n);
		Date d2 = c.getTime();
		String s = df.format(d2);
		return s;
	}

	/**
	 * 当前日期前几天或者后几天的日期
	 *
	 * @param n 单位天
	 */
	public static Date afterNDayObj(int n) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, n);
		return c.getTime();
	}

	/**
	 * 转换string类型时间为时间类型（格式yyyy-MM-dd HH:mm:ss）
	 *
	 * @param stringDate
	 * @return
	 * @author chenli
	 * @data Jan 18, 2013
	 */
	public static Date convertString2LongDate(String stringDate) {
		return convertString2Date(stringDate, LONG_DATE_FORMAT);
	}

	/**
	 * 用于处理excel导出 hzc EDIT 2007-12-25
	 **/
	public static String convert2String(String date) {
		SimpleDateFormat sdf = null;
		if (StringUtils.isNotBlank(date)) {
			if (date.trim().length() <= 10) {
				sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
			} else {
				sdf = new SimpleDateFormat(LONG_DATE_FORMAT);
			}
		} else {
			sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		}
		try {
			return sdf.format(sdf.parse(date));
		} catch (ParseException pe) {
			return "";
		}
	}

	/**
	 * 判断obj是否为时间类型 目前只判断是否 符合yyyy-MM-dd这种格式
	 *
	 * @param obj
	 * @return
	 * @author chenli
	 * @data Jan 18, 2013
	 */
	public static boolean isDate(String obj) {
		if (StringUtils.isNotBlank(obj)) {
			if (obj.indexOf("~") > -1) {
				return false;
			}
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
				sdf.parse(obj);
				return true;
			} catch (Exception e) {
				return false;
			}
		} else{
			return false;
		}
	}

	/**
	 * 该方法获取当前时间的时分秒 实用场景 当记录保存时间时自动截取到了日，小时数没有了。则可以根据该方法获取当前的小时数用来补充前面带空格 zhl
	 * add
	 *
	 * @return
	 */
	public static String getSystemHHMMSS() {
		return convertDate2String(getCurrentDate(), LONG_DATE_FORMAT).substring(10, 19);
	}

	/**
	 * 获取当前时间的前几天
	 *
	 * @param date 当前时间
	 * @param days 正数时间往前推 负数时间往后移 （单位天）
	 * @return
	 * @author chenli
	 * @data Jan 18, 2013
	 */
	public static Date minusDays(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - days);
		return c.getTime();
	}

	/**
	 * 得到当前日期的前后日期 +为后 -为前 分钟
	 *
	 * @param mm_i 单位分钟
	 * @return
	 */
	public static final String getBefDateMMString(String currentDate, int mm_i, String format) {
		if (StringUtils.isBlank(currentDate)) {
			return null;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = sdf.parse(currentDate);
			Calendar day = Calendar.getInstance();
			day.setTime(date);
			day.add(Calendar.MINUTE, mm_i);
			return sdf.format(day.getTime());
		} catch (Exception e) {
			return null;
		}
	}


	/**
	 * 毫秒时间转换成可读字符串<br>
	 * <li>800->800毫秒</li>
	 * <li>1800->1秒800毫秒</li>
	 *
	 * @param millisecond
	 * @return
	 */
	public static String millisToFormatStr(long millisecond) {
		StringBuilder sb = new StringBuilder();
		if (millisecond < MILLIS_SECOND) {
			sb.append(millisecond);
			sb.append("毫秒");
		} else {
			Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			c.setTimeInMillis(millisecond);
			if (millisecond < MILLIS_MINUTE) {
				sb.append(c.get(Calendar.SECOND));
				sb.append("秒");
			} else if (millisecond < MILLIS_HOUR) {
				sb.append(c.get(Calendar.MINUTE));
				sb.append("分");
				sb.append(c.get(Calendar.SECOND));
				sb.append("秒");
			} else if (millisecond < MILLIS_DAY) {
				sb.append(c.get(Calendar.HOUR_OF_DAY));
				sb.append("时");
				sb.append(c.get(Calendar.MINUTE));
				sb.append("分");
				sb.append(c.get(Calendar.SECOND));
				sb.append("秒");
			} else {
				int year = (c.get(Calendar.YEAR) - 1970);
				if (year > 0) {
					sb.append(year + "年");
				}
				int date = c.get(Calendar.DAY_OF_YEAR) - 1;
				sb.append(date + "天");
				sb.append(c.get(Calendar.HOUR_OF_DAY));
				sb.append("时");
				sb.append(c.get(Calendar.MINUTE));
				sb.append("分");
				sb.append(c.get(Calendar.SECOND));
				sb.append("秒");
			}
		}
		return sb.toString();
	}

	/**
	 * 功能描述:
	 * 毫秒数转换成常用时间字符串
	 *
	 * @param ms 毫秒数
	 * @return
	 * @Date 2019/4/15
	 * @Author Huangg
	 **/
	public static String millisToFormatCommonStr(long ms) {
		return millisToFormatCommonStr(ms, 2);
	}

	/**
	 * 功能描述:
	 * 毫秒数转换成常用时间字符串
	 *
	 * @param ms    毫秒数
	 * @param digit 显示位数  如：1：结果为“1年”、“2年” 2：结果为：“2月11天”、“2年1月”
	 * @return
	 * @Date 2019/4/15
	 * @Author update by allen
	 **/
	public static String millisToFormatCommonStr(long ms, int digit) {
		StringBuilder sb = new StringBuilder();
		int cDigit = 0;
		long yd = 12 * 24 * 60 * 60 * 30 * 1000;//年月毫秒数
		long md = 24 * 60 * 60 * 30 * 1000;//每月毫秒数
		long nd = 24 * 60 * 60 * 1000;//每天毫秒数
		long nh = 60 * 60 * 1000;//每小时毫秒数
		long nm = 60 * 1000;//每分钟毫秒数

		long year = ms / yd;   // 计算差多少年
		long month = ms / md;   // 计算差多少天
		long day = ms % md / nd;   // 计算差多少天
		long hour = ms % md % nd / nh; // 计算差多少小时
		long min = ms % md % nd % nh / nm;  // 计算差多少分钟


		if (cDigit < digit && year > 0) {
			sb.append(year).append("年");
			cDigit++;
		}
		if (cDigit < digit && month > 0) {
			sb.append(month).append("月");
			cDigit++;
		}
		if (cDigit < digit && day > 0) {
			sb.append(day).append("天");
			cDigit++;
		}
		if (cDigit < digit && hour > 0) {
			sb.append(hour).append("小时");
			cDigit++;
		}
		if (cDigit < digit && min > 0) {
			sb.append(min).append("分钟");
		}
		if (sb.length() == 0) {//默认值
			sb.append("1分钟");
		}
		return sb.toString();
	}

	/**
	 *功能描述 秒数转换成常用时间字符串
	 * @author zhuyongquan
	 * @date 2020/5/7
	  * @param ss
	 * @return java.lang.String
	 */
	public static String secondToFormatCommonStr(long ss) {
		return secondToFormatCommonStr(ss, 2);
	}

	/**
	 *功能描述 秒数转换成常用时间字符串
	 * @author zhuyongquan
	 * @date 2020/5/7
	  * @param ss
	 * @param digit	显示位数  如：1：结果为“1天”、“2天”” 2：结果为：“2天11时”、“11时10分”
	 * @return java.lang.String
	 */
	public static String secondToFormatCommonStr(long ss, int digit) {
		StringBuilder sb = new StringBuilder();
		int cDigit = 0;
		long nd = 24 * 60 * 60;//每天毫秒数
		long nh = 60 * 60;//每小时毫秒数
		long nm = 60;//每分钟毫秒数
		long ns = 1;//每秒毫秒数

		long day = ss / nd;   // 计算差多少天
		long hour = ss % nd / nh; // 计算差多少小时
		long min = ss % nd % nh / nm;  // 计算差多少分钟
		long second = ss % nd % nh % nm / ns;  // 计算差多少秒
		if (cDigit < digit && day > 0) {
			sb.append(day).append("天");
			cDigit++;
		}
		if (cDigit < digit && hour > 0) {
			sb.append(hour).append("时");
			cDigit++;
		}
		if (cDigit < digit && min > 0) {
			sb.append(min).append("分");
			cDigit++;
		}
		if (cDigit < digit && second >= 0) {
			sb.append(second).append("秒");
		}
		return sb.toString();
	}

	/**
	 * 获取当天起始时间 凌晨00:00:00 000
	 *
	 * @return
	 */
	public static Date getTadayStart() {
		return getStartDay(new Date());
	}

	/**
	 * 获取当天结束时间 结束23:59:59 999
	 *
	 * @return
	 */
	public static Date getTadayEnd() {
		return getEndDay(new Date());
	}

	/**
	 * 判断一个是否是夏令时（4月份中旬第一周日凌晨2点至9月份中旬第一周日凌晨2点）
	 *
	 * @param date
	 * @return
	 * @author Run the ant(wangyijie)
	 * @data 2018年6月26日
	 */
	@SuppressWarnings("deprecation")
	public static boolean isDST(Date date) {
		//获取当前的年份
		int nowYear = date.getYear() + 1900;
		//获取今年4月份的第一天是星期几
		int startFirstDay = DateUtil.firstDayOfWeek(nowYear, 4);
		//因为方法返回的周日为0，此处做转换
		if (startFirstDay == 0) {
			startFirstDay = 7;
		}
		//判断本月第二个周日是几号
		int startDay = 1 + (7 - startFirstDay) + 7;
		//如果不在4月中旬（4.11-4.20）,则再加7天，即为夏令时开始时间
		if (startDay < 11) {
			startDay += 7;
		}
		Date startTime = DateUtil.convertString2Date(nowYear + "-04-" + startDay + " 02:00:00", DateUtil.LONG_DATE_FORMAT);

		//获取今年9月份的第一天是星期几
		int endFirstDay = DateUtil.firstDayOfWeek(nowYear, 9);
		//因为方法返回的周日为0，此处做转换
		if (endFirstDay == 0) {
			endFirstDay = 7;
		}
		//判断本月第二个周日是几号
		int endDay = 1 + (7 - endFirstDay) + 7;
		//如果不在9月中旬（9.11-9.20）,则再加7天，即为夏令时结束时间
		if (endDay < 11) {
			endDay += 7;
		}
		Date endTime = DateUtil.convertString2Date(nowYear + "-09-" + endDay + " 02:00:00", DateUtil.LONG_DATE_FORMAT);

		if (date.after(startTime) && date.before(endTime)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取指定时间的凌晨00:00:00 000
	 *
	 * @param day
	 * @return
	 */
	public static Date getStartDay(Date day) {
		Calendar c = Calendar.getInstance();
		c.setTime(day);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取指定时间的结束23:59:59 999
	 *
	 * @param day
	 * @return
	 */
	public static Date getEndDay(Date day) {
		Calendar c = Calendar.getInstance();
		c.setTime(day);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	/**
	 * 根据用户的出生日期，计算用户的年龄
	 *
	 * @param birthday
	 * @return
	 * @author chenli
	 * @data 2017-10-19
	 */
	public static int getAgeByBirth(Date birthday) {
		int age = 0;
		try {
			Calendar now = Calendar.getInstance();
			now.setTime(new Date());// 当前时间

			Calendar birth = Calendar.getInstance();
			birth.setTime(birthday);

			if (birth.after(now)) {//如果传入的时间，在当前时间的后面，返回0岁
				age = 0;
			} else {
				age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
				if (now.get(Calendar.DAY_OF_YEAR) <= birth.get(Calendar.DAY_OF_YEAR)) {
					age -= 1;
				}
			}
			return age;
		} catch (Exception e) {//兼容性更强,异常后返回数据
			return 0;
		}
	}

	public static Date getStartDayOfYear(Integer year){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		int first = c.getActualMinimum(Calendar.DAY_OF_YEAR);
		c.set(Calendar.DAY_OF_YEAR, first);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static Date getEndDayOfYear(Integer year){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		int last = c.getActualMaximum(Calendar.DAY_OF_YEAR);
		c.set(Calendar.DAY_OF_YEAR, last);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	/**
	* 功能描述：获取年份接口
	* @author maojincheng
	* @date 2019/7/1 0001
	* @param
	* @return
	*/
	public static int getYearByDate(Date date) {
		return date.getYear() + 1900;
	}

	/**
	 * 功能描述: 根据日期获得月份
	 *
	 * @param date 日期 格式固定需要为yyyy-mm-dd
	 * @return
	 * @aurthor  zhangjianwei
	 * @date  2019/8/8
	 */
	public static String getDateMonth(String date){
		String month = "";
		if("0".equals(date.substring(5,6))){
			month += date.substring(6,7);
		}else{
			month += date.substring(5,7);
		}
		return month;
	}
	/**
	 * 功能描述: 计算两个时间之间的小时数，如果开始时间大于结束时间 则结束时间以第二天计算
	 *
	 * @param startTime 开始时间 格式 HH:mm:ss
	 * @param endTime 结束时间 格式 HH:mm:ss
	 * @return
	 * @aurthor  zhangjianwei
	 * @date  2019/8/8
	 */
	public static float getHour(String startTime, String endTime){
		Date startDate =  DateUtil.convertString2Date("1970-01-01 "+ startTime, DateUtil.LONG_DATE_FORMAT);
		Date endDate = DateUtil.convertString2Date("1970-01-01 "+ endTime, DateUtil.LONG_DATE_FORMAT);
		if(startTime.compareTo(endTime) > 0){
			endDate = DateUtil.convertString2Date("1970-01-02 "+ endTime, DateUtil.LONG_DATE_FORMAT);
		}
		long diff = endDate.getTime() - startDate.getTime();
		return (float)diff/3600000;
	}

	/**
	 * 功能描述: 计算两个时间之间的小时数，如果开始时间大于结束时间 则结束时间以第二天计算
	 *
	 * @param startDate 开始时间 格式 YY:MM:DD HH:mm:ss
	 * @param endDate 结束时间 格式 YY:MM:DD HH:mm:ss
	 * @return
	 * @aurthor  zhangjianwei
	 * @date  2019/8/8
	 */
	public static Integer getHour(Date startDate, Date endDate){
		Long start = startDate.getTime();
		Long end = endDate.getTime();
		Long diff = end - start;
		return new Double(Math.ceil(diff/3600000.0)).intValue();
	}

	/**
	 * 功能描述: 设置日期每天开始时间
	 * @return
	 * @author zhuweidong
	 * @date 2019/10/9
	 */
	public static String setDateDayStartTime(String date) {
		date += Suffix_dayStart;
		return date;
	}

	/**
	 * 功能描述: 设置日期每天结束时间
	 * @return
	 * @author zhuweidong
	 * @date 2019/10/9
	 */
	public static String setDateDayEndTime(String date) {
		date += Suffix_dayEnd;
		return date;
	}

	/**
	 * 计算day1 与 day2 的相差天数。
	 * 如果day1 小于 day2 返回负值
	 * @param day1 时间1
	 * @param day2 时间2
	 * @result
	 * @author chenLi
	 * @date 2019-11-28
	 */
	public static long diffDay(Date day1, Date day2) {
		return diff(day1, day2, Calendar.DATE);
	}

	/**
	 * 计算day1 与 day2 的时间差，返回单位参考第三个参数。
	 * 如果day1 小于 day2 返回负值
	 * @param day1 时间1
	 * @param day2 时间2
	 * @param timeUnit {@link Calendar#SECOND} {@link Calendar#DATE}
	 * @result
	 * @author chenLi
	 * @date 2019-11-28
	 */
	public static long diff(Date day1, Date day2, int timeUnit) {
		long result = 0L;
		long diffTime = 0L;
		if (day1 == null && day2 == null){
			return 0L;
		} else if (day1 == null) {
			diffTime = day2.getTime();
		} else if (day2 == null) {
			diffTime = day1.getTime();
		}else{
			diffTime = day1.getTime() - day2.getTime();
		}

		switch (timeUnit) {
			case Calendar.MILLISECOND:
				result = TimeUnit.MILLISECONDS.toMillis(diffTime);
				break;
			case Calendar.SECOND:
				result = TimeUnit.MILLISECONDS.toSeconds(diffTime);
				break;
			case Calendar.MINUTE:
				result = TimeUnit.MILLISECONDS.toMinutes(diffTime);
				break;
			case Calendar.HOUR:
				result = TimeUnit.MILLISECONDS.toHours(diffTime);
				break;
			case Calendar.DATE:
				result = TimeUnit.MILLISECONDS.toDays(diffTime);
				break;
			default:
		}
		return result;
	}

	/**
	 * 功能描述: 获取随机日期
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @author zhuweidong
	 * @date 2020/1/15
	 */
	public static Date getRandomDate(String beginDate, String endDate) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(LONG_DATE_FORMAT);
			Date start = format.parse(beginDate);
			Date end = format.parse(endDate);

			if (start.getTime() >= end.getTime()) {
				return null;
			}
			long date = random(start.getTime(), end.getTime());
			return new Date(date);
		} catch (Exception e) {
			logger.warn("String：{}转化为Date出错,", beginDate, e);
		}
		return null;
	}

	private static long random(long begin, long end) {
		long rtn = begin + (long) (Math.random() * (end - begin));
		if (rtn == begin || rtn == end) {
			return random(begin, end);
		}
		return rtn;
	}

	/**
	 * 功能描述：获取某年某月第一天
	 * @param year 年份
	 * @param month 月份 从1 开始
	 * @result
	 * @author chenLi
	 * @date 2020-6-20
	 */
	public static Date getFirstDay(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		return calendar.getTime();
	}

	/**
	 * 功能描述：获取某年某月的最后一天
	 * @param year 年份
	 * @param month 月份 从1 开始
	 * @result
	 * @author chenLi
	 * @date 2020-6-20
	 */
	public static Date getLastDay(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.SECOND, -1);
		return calendar.getTime();
	}
	/**
	 * 根据年和月返回 周数。如果前后不满一周的将会扩充。取返回值之间的区间即可
	 *  <br/>假如4月1号 - 4月30号。6月1号为星期三   则  第一周：3月30号-4月5号  第二周：4月6号-4月12号 第三周：4月13号-4月19号 第四周 4月20号-4月26号 第五周：4月27号-5月3号
	 *  <br/> 将返回3月30号/4月6号/4月13号/4月20号/4月27号/5月4号
	 * @param year 年份
	 * @param month 月份 从1开始
	 * @return 返回的值为周一的时间集合。
	 */
	public static List<Date> listWeek(int year, int month) {
		Date firstDay = getFirstDay(year, month);
		Date lastDay = getLastDay(year, month);
		return listWeek(firstDay, lastDay);
	}

	/**
	 * 根据开始时间和结束时间返回 周数。如果前后不满一周的将会扩充。取返回值之间的区间即可
	 *  <br/>假如4月1号 - 4月30号。6月1号为星期三   则  第一周：3月30号-4月5号  第二周：4月6号-4月12号 第三周：4月13号-4月19号 第四周 4月20号-4月26号 第五周：4月27号-5月3号
	 *  <br/> 将返回3月30号/4月6号/4月13号/4月20号/4月27号/5月4号
	 * @param firstDay
	 * @param lastDay
	 * @Author chenLi
	 * @Date 2020/6/21
	 * @return 返回的值为周一的时间集合。
	 */
	public static List<Date> listWeek(Date firstDay, Date lastDay) {
		if (firstDay == null || lastDay == null || lastDay.before(firstDay)) {
			return Collections.emptyList();
		}
		List<Date> result = new ArrayList<>();
		//开始时间
		Calendar start = Calendar.getInstance(Locale.CHINA);
		start.setTime(firstDay);
		start.setFirstDayOfWeek(Calendar.MONDAY);
		start.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		start.set(Calendar.MILLISECOND, 0);
		//结束时间
		Calendar end = Calendar.getInstance(Locale.CHINA);
		end.setTime(lastDay);
		end.setFirstDayOfWeek(Calendar.MONDAY);
		end.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		end.set(Calendar.HOUR_OF_DAY, 0);
		end.set(Calendar.MINUTE, 0);
		end.set(Calendar.SECOND, 0);
		end.set(Calendar.MILLISECOND, 0);
		end.add(Calendar.DAY_OF_MONTH, 6);

		result.add(start.getTime());
		while (start.before(end)) {
			start.add(Calendar.DAY_OF_MONTH, 7);
			result.add(start.getTime());
		}
		return result;
	}

	public static void main(String[] args) {
		Calendar deadline = Calendar.getInstance();
		deadline.set(Calendar.HOUR, 8);
		deadline.set(Calendar.MINUTE, 30);
		deadline.set(Calendar.SECOND, 0);
		System.out.println(DateUtil.convertDate2StringL(deadline.getTime()));
	}
}
