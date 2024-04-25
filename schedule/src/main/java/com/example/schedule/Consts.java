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
	public static String REPEATE_TYPE_MONTH = "03";
	public static String REPEATE_TYPE_YEAR = "04";
}