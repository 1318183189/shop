package com.room302.shop.service.impl;

import com.room302.shop.dao.CfCommodityTypeMapper;
import com.room302.shop.model.CfCommodityType;
import com.room302.shop.service.CfCommodityTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CfCommodityTypeServiceImpl implements CfCommodityTypeService {

    @Autowired
    private CfCommodityTypeMapper cfCommodityTypeMapper;

    @Override
    public int setCfCommodityType(CfCommodityType cfCommodityType) {
        return cfCommodityTypeMapper.insert(cfCommodityType);
    }

    @Override
    public List<CfCommodityType> getCommodityType() {
        return cfCommodityTypeMapper.selectCommodityTypes();
    }

    @Override
    public int detCommodityType(String id) {
        return cfCommodityTypeMapper.deleteCommodityType(id);
    }
}
