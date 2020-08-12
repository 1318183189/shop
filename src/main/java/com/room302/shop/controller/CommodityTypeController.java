package com.room302.shop.controller;

import com.alibaba.fastjson.JSON;
import com.room302.shop.model.CfCommodityType;
import com.room302.shop.model.Code;
import com.room302.shop.service.CfCommodityTypeService;
import com.room302.shop.utils.Constants;
import com.room302.shop.utils.FastDFS;
import com.room302.shop.utils.FileUtil;
import com.room302.shop.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/", produces = {"application/json;charset=UTF-8"})
public class CommodityTypeController {

    @Autowired
    private CfCommodityTypeService cfCommodityTypeService;

    /**
     * 创建分类
     * @param commodityTypeName
     * @param file
     * @return
     */
    @PostMapping("setCommodityType/{commodityTypeName}")
    @RequiresPermissions("admin")
    public JSON setCommodityType(@PathVariable String commodityTypeName, @PathVariable MultipartFile file) {
        Code code = new Code();
        if (commodityTypeName == null && commodityTypeName.isEmpty()) {
            code.setMsg(Constants.CODE_400_ERROR);
            code.setCode(Constants.CODE_400);
            return (JSON) JSON.toJSON(code);
        }
        String path = null;
        if (!StringUtils.isEmpty(file)) {//判断非空
            FileUtil fileUtil = new FileUtil();
            if (!fileUtil.isImage(file)) {
                code.setMsg(Constants.CODE_405_ERROR);
                code.setCode(Constants.CODE_405);
                log.error(code.toString());
                return (JSON) JSON.toJSON(code);
            } else {
                FastDFS FastDFS = null;
                try {
                    FastDFS = new FastDFS();
                    path = FastDFS.uploadByFastDFS(file);
                    path = "http://47.92.137.1:8088/" + path;
                } catch (IOException e) {
                    log.error(e.getMessage());
                } catch (MyException e) {
                    log.error(e.getMessage());
                }
            }
        }
        CfCommodityType cfCommodityType = new CfCommodityType();
        cfCommodityType.setImg(path);
        cfCommodityType.setTypeName(commodityTypeName);
        cfCommodityType.setId(UUIDUtils.getUUID());

        int flag = cfCommodityTypeService.setCfCommodityType(cfCommodityType);
        if (flag != 1) {
            code.setMsg(Constants.CODE_444_ERROR);
            code.setCode(Constants.CODE_444);
            return (JSON) JSON.toJSON(code);
        }
        code.setMsg(Constants.CODE_200_MSG);
        code.setCode(Constants.CODE_200);
        code.setInfo(cfCommodityType);
        return (JSON) JSON.toJSON(code);
    }

    /**
     * 获取分类
     * @return
     */
    @GetMapping("getCommodityType")
    public JSON setCommodityType() {

        List<CfCommodityType> cfCommodityTypes = cfCommodityTypeService.getCommodityType();

        return (JSON) JSON.toJSON(cfCommodityTypes);
    }

    @PostMapping("detCommodityType/{id}")
    @RequiresPermissions("admin")
    public JSON detCommodityType(@PathVariable String id) {
        boolean flag = false;

        if (id.length() == 32){
            int count = cfCommodityTypeService.detCommodityType(id);
            if (count == 1){
                flag =true;
            }
        }
        return (JSON) JSON.toJSON(flag);
    }
}
