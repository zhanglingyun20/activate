package com.vr.active;

import com.vr.active.common.SystemCache;
import com.vr.active.common.db.DBUtils;
/**
 * 程序入口
 * @author sawyer
 * @date 2016年8月11日
 */
public class ActivateMain {

	public static void main(String[] args) {
		if(DBUtils.test()&&SystemCache.initCache()) {
			new MainUI(true);
		}else{
			System.out.println("加载数据库失败");
		}
	}
}
