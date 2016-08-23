package com.vr.active.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.vr.active.SpringContext;
import com.vr.active.mapper.SysConfigMapper;
import com.vr.active.model.SysConfig;
/**
 * 
 * @author sawyer
 * @date 2016年8月10日
 */
public class SystemCache
{

	private static Logger logger  =  Logger.getLogger(SystemCache.class );
	
	private static Map<String,Object> systemCache = new HashMap<String, Object>();
	
	public static boolean  initCache(){
		try {
			initSysConfig();
		} catch (Exception e) {
			logger.error("加载系统缓存失败", e);
			return false;
		}
		return true;
	}
	
	public static <T> T getChacheByClass(Class<?> clz) {
		if (systemCache.containsKey(clz.toString())) {
			return (T) systemCache.get(clz.toString());
		}
		return null;
	}
	

	
	public static void initSysConfig(){
		SysConfigMapper sysConfigMapper = SpringContext.getBean(SysConfigMapper.class);
		systemCache.put(SysConfig.class.toString(), sysConfigMapper.getAllConfig());
	}
	

	
	public static String getSysConfigVauleByKey(String key){
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		List<SysConfig> sysConfigs = (List<SysConfig>) systemCache.get(SysConfig.class.toString());
		if (sysConfigs!=null&&!sysConfigs.isEmpty()) {
			for (SysConfig sysConfig : sysConfigs) {
				if (key.equalsIgnoreCase(sysConfig.getKeyName())) {
					return  sysConfig.getKeyValue();
				}
			}
		}
		return null;
	}
}
