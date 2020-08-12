package com.room302.shop.dao;

import com.room302.shop.model.CfCommodityType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CfCommodityTypeMapper {
    int insert(CfCommodityType record);

    int insertSelective(CfCommodityType record);

    /**
     * 获取分类列表
     * @return
     */
    List<CfCommodityType> selectCommodityTypes();

    /**
     * 删除分类
     * @param id
     * @return
     */
    int deleteCommodityType(String id);
}