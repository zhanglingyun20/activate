package com.vr.active.mapper;

import java.util.List;

import com.vr.active.model.SysConfig;

public interface SysConfigMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(SysConfig record);

	int insertSelective(SysConfig record);

	SysConfig selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(SysConfig record);

	int updateByPrimaryKey(SysConfig record);
	
	List<SysConfig> getAllConfig();
}