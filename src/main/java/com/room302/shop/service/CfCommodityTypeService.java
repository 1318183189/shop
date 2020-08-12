package com.room302.shop.service;

import com.alibaba.fastjson.JSON;
import com.room302.shop.model.CfCommodityType;

import java.util.List;

public interface CfCommodityTypeService {
    /**
     * 插入商品分类
     * @param cfCommodityType
     * @return
     */
    int setCfCommodityType(CfCommodityType cfCommodityType);

    /**
     * 获取分类列表
     * @return
     */
    List<CfCommodityType> getCommodityType();

    /**
     * 删除分类
     * @param id
     * @return
     */
    int detCommodityType(String id);
}
