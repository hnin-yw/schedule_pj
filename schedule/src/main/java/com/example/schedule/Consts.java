package com.example.schedule;

import org.springframework.stereotype.Component;

@Component
public class Consts {
	public final static int MAX_CODE_LENGTH = 7;
	public final static int MAX_CODE_CONT_LENGTH = 20;
	public final static int MAX_NAME_LENGTH = 100;
	public final static int MAX_NAME_AREA_LENGTH = 250;
	
	public static String REPEATE_TYPE_NONE = "01";
	public static String REPEATE_TYPE_DAY = "02";
	public static String REPEATE_TYPE_WEEK = "03";
	public static String REPEATE_TYPE_MONTH = "04";
	public static String REPEATE_TYPE_YEAR = "05";
	
	public static String REPEATE_DAY_SUN = "01";
	public static String REPEATE_DAY_SUN_VALUE = "SUNDAY";
	public static String REPEATE_DAY_MON = "02";
	public static String REPEATE_DAY_MON_VALUE = "MONDAY";
	public static String REPEATE_DAY_TUE = "03";
	public static String REPEATE_DAY_TUE_VALUE = "TUESDAY";
	public static String REPEATE_DAY_WED = "04";
	public static String REPEATE_DAY_WED_VALUE = "WEDNESDAY";
	public static String REPEATE_DAY_THU = "05";
	public static String REPEATE_DAY_THU_VALUE = "THURSDAY";
	public static String REPEATE_DAY_FRI = "06";
	public static String REPEATE_DAY_FRI_VALUE = "FRITHDAY";
	public static String REPEATE_DAY_SAT = "07";
	public static String REPEATE_DAY_SAT_VALUE = "SATURDAY";
	
	public static String REPEATE_TYPE_OF_MONTH_01 = "01";
	public static String REPEATE_TYPE_OF_MONTH_02 = "02";
	public static String REPEATE_TYPE_OF_MONTH_03 = "03";
}