package com.room302.shop.dao;

import com.room302.shop.model.SysPermission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysPermissionMapper {
    int deleteByPrimaryKey(Integer permissionid);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(Integer permissionid);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);
}