package com.vr.active.mapper;

import com.vr.active.model.DeviceInfo;

public interface DeviceInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(DeviceInfo record);
   
    int deleteAllDeviceInfo();
}