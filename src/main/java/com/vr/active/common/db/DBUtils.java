package com.vr.active.common.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.vr.active.model.DeviceInfo;

public class DBUtils {

	public static String url = "";
	public static String driver = "";
	public static String username = "";
	public static String password = "";

	static {
		Properties prop = new Properties();
		InputStream in = Object.class.getResourceAsStream("/jdbc.properties");
		try {
			prop.load(in);
			url = prop.getProperty("jdbc.url").trim();
			driver = prop.getProperty("jdbc.driverClassName").trim();
			username = prop.getProperty("jdbc.username").trim();
			password = prop.getProperty("jdbc.password").trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Connection getConn() {
		Connection conn = null;
		try {
			Class.forName(driver); // classLoader,加载对应驱动
			conn = (Connection) DriverManager.getConnection(url, username,
					password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static boolean test() {
		Connection conn = getConn();
		String sql = "select 1";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				return StringUtils.isNoneBlank(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		System.out.println(getSysConfigs());
		DeviceInfo deviceInfo = new DeviceInfo(1, "deviceMac", username,
				password, "deviceMac", "deviceCode", "deviceName", "siteName",
				"provinceCode", "cityCode", "siteCode", "yes", null);
		insert(deviceInfo);
		deleteAll();
	}

	public static Map<String, String> getSysConfigs() {
		Connection conn = getConn();
		String sql = "select * from sys_config ";
		PreparedStatement pstmt;
		Map<String, String> configMap = new HashMap<String, String>();
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				configMap.put(rs.getString("key_name"),
						rs.getString("key_value"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return configMap;
	}

	public static int insert(DeviceInfo deviceInfo) {
		Connection conn = getConn();
		int i = 0;
		String sql = "insert into device_info (account,username,password,device_mac,device_code,device_name,is_sync,create_time) values(?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, deviceInfo.getAccount());
			pstmt.setString(2, deviceInfo.getUsername());
			pstmt.setString(3, deviceInfo.getPassword());
			pstmt.setString(4, deviceInfo.getDeviceMac());
			pstmt.setString(5, deviceInfo.getDeviceCode());
			pstmt.setString(6, deviceInfo.getDeviceName());
			pstmt.setString(7, DeviceInfo.Sync.YES.getValue());
			pstmt.setDate(8, new java.sql.Date(new Date().getTime()));
			i = pstmt.executeUpdate();
			System.out.println(i);
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public static int deleteAll() {
		Connection conn = getConn();
		int i = 0;
		String sql = "delete from device_info";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			i = pstmt.executeUpdate();
			System.out.println("resutl: " + i);
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

}