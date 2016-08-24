package com.vr.active.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vr.active.common.db.DBUtils;

/**
 * 
 * @author sawyer
 * @date 2016年8月10日
 */
public class SystemCache {

	private static Map<String, String> configCache = new HashMap<String, String>();

	public static boolean initCache() {
		try {
			configCache = DBUtils.getSysConfigs();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String getSysConfigByKey(String key) {
		return configCache.get(key);
	}

}
