package com.hkkj.oa.common.utils;

import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;

public class RandomUtil {
	
	public static Random random = new Random();

	public static String getRandom(int length) {
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < length; i++) {
			boolean isChar = (random.nextInt(2) % 2 == 0);// 输出字母还是数字
			if (isChar) { // 字符串
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
				ret.append((char) (choice + random.nextInt(26)));
			} else { // 数字
				ret.append(Integer.toString(random.nextInt(10)));
			}
		}
		return ret.toString();
	}
	
	public static String getRandomNum(int length) {
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < length; i++) {
			ret.append(Integer.toString(random.nextInt(10)));
		}
		return ret.toString();
	}
	
	/**
	 * 获取时间戳+3位随机数
	 * 
	 * @return
	 */
	public static String getTime3Random() {
		return System.currentTimeMillis() + RandomStringUtils.randomNumeric(3);
	}
	
	public static String getTimeRandom(int num) {
		return DateUtil.formatDate(DateUtil.NOW(), "yyyyMMddHHmmssSSS")+RandomUtil.getRandomNum(num);
	}
	
	/** 获取UUID*/
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}