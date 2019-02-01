package com.hkkj.oa.common.wechat;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.hkkj.oa.common.utils.DateUtil;


/**
 * 微信数据缓存到内存，实现类
 * 
 * @author doubao
 *
 */
public class WeixinDataCache {

	private static WeixinDataCache instance = null;

	public static WeixinDataCache getInstance() {
		if (null == instance) {
			instance = new WeixinDataCache();
		}
		return instance;
	}

	private Map<String, Map<String, Object>> cache = new HashMap<String, Map<String, Object>>();

	/**
	 * 设置缓存数据
	 * 
	 * @param name
	 *            缓存名称
	 * @param value
	 *            要缓存的数据
	 * @param expires_in
	 *            过期时间：秒
	 */
	public void setData(String name, Object value, int expires_in) {
		Map<String, Object> map = new HashMap<String, Object>();
		Date now = new Date(System.currentTimeMillis());
		Date expiresTime = DateUtil.addDate(now, Calendar.SECOND, expires_in);
		map.put("expiresTime", expiresTime);
		map.put("value", value);
		cache.put(name, map);
	}

	/**
	 * 获取缓存数据，没有返回null
	 * 
	 * @param name
	 *            缓存名称
	 * @return
	 */
	public Object getData(String name) {
		Map<String, Object> map = cache.get(name);
		if (null != map) {
			Date expiresTime = (Date) map.get("expiresTime");
			Date now = new Date(System.currentTimeMillis());
			if (null != expiresTime) {
				// 到期时间比当前时间大（晚）
				if (expiresTime.compareTo(now) > 0) {
					return map.get("value");
				}
			}
		}
		return null;
	}

	/**
	 * 清理过期的数据
	 */
	public void clearExpiresData() {
		Date now = new Date(System.currentTimeMillis());
		Set<String> keySet = cache.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			Map<String, Object> map = cache.get(key);
			if (null != map) {
				Date expiresTime = (Date) map.get("expiresTime");
				if (null != expiresTime) {
					// 到期时间比当前时间早或者等于当前时间
					if (expiresTime.compareTo(now) <= 0) {
						cache.remove(key);
					}
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		WeixinDataCache wdc = WeixinDataCache.getInstance();
		wdc.setData("a", 1, 5);
		System.out.println(wdc.getData("a"));
		TimeUnit.SECONDS.sleep(2);
		System.out.println(wdc.getData("a"));
		// wdc.clearExpiresData();
		// System.out.println(wdc.getData("a"));
		TimeUnit.SECONDS.sleep(3);
		System.out.println(wdc.getData("a"));
	}
}
