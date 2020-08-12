package com.room302.shop.controller;


import com.alibaba.fastjson.JSON;
import com.room302.shop.model.Code;
import com.room302.shop.model.SysUser;
import com.room302.shop.service.SysUserService;
import com.room302.shop.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.csource.common.MyException;
import org.csource.fastdfs.StorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "/", produces = {"application/json;charset=UTF-8"})
public class UserRgisterController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private RedisUtils redisUtils;


    @PostMapping("getEmailCode/{email}")
    public JSON getEmailCode(@PathVariable String email, HttpServletRequest request) {
        String ip = GetIPUtil.getIP(request);

        Code code = new Code();

        if (email != null || !email.isEmpty()) {
            Long keyTime = redisUtils.getTime(email);
            if (keyTime > 0) {
                code.setMsg(Constants.CODE_301_ERROR);
                code.setCode(Constants.CODE_301);
                return (JSON) JSON.toJSON(code);
            } else {
                Integer emailCode = (int) ((Math.random() * 9 + 1) * 100000);
                try {
                    redisUtils.set(email, emailCode.toString(), 120);
                    mailUtil.sendSimpleMail(email, "商城注册码", "您的注册验证码为：" + emailCode + "(2分钟有效)");
                    code.setMsg(Constants.CODE_200_MSG);
                    code.setCode(Constants.CODE_200);
                    code.setInfo(emailCode);
                } catch (Exception e) {
                    code.setMsg(Constants.CODE_400_ERROR);
                    code.setCode(Constants.CODE_400);
                    log.error("邮件发送失败！email" + email, e);
                }

            }
        }

        return (JSON) JSON.toJSON(code);
    }

    @PostMapping("rgister/{email}/{pwd}/{uname}/{emailCode}")
    public JSON rgister(@PathVariable String email, @PathVariable String pwd, @PathVariable String uname, @PathVariable String emailCode, MultipartFile file) {
        Map<String, Object> map = new HashedMap();
        Code code = new Code();
        String path = null;
        String redisEmailCode = null;
       String name =  sysUserService.getUserNmae(uname);
       if (name != null){
           code.setMsg(Constants.CODE_445_ERROR);
           code.setCode(Constants.CODE_445);
           log.error(code.toString());
           return (JSON) JSON.toJSON(code);
       }
        //判断邮箱是否为空
        if (null == email || email.isEmpty()) {
            code.setMsg(Constants.CODE_401_ERROR);
            code.setCode(Constants.CODE_401);
            log.error(code.toString());
            return (JSON) JSON.toJSON(code);
        }
        //判断密码是否为空
        if (pwd == null || pwd.isEmpty() || pwd.length() != 32) {
            code.setMsg(Constants.CODE_402_ERROR);
            code.setCode(Constants.CODE_402);
            log.error(code.toString());
            return (JSON) JSON.toJSON(code);
        }
        //判断用户是否为空
        if (null == uname || uname.isEmpty()) {
            code.setMsg(Constants.CODE_403_ERROR);
            code.setCode(Constants.CODE_403);
            log.error(code.toString());
            return (JSON) JSON.toJSON(code);
        }
        //判断邮箱验证码是否为空
        if (null == emailCode || emailCode.isEmpty()) {
            code.setMsg(Constants.CODE_404_ERROR);
            code.setCode(Constants.CODE_404);
            log.error(code.toString());
            return (JSON) JSON.toJSON(code);
        }

        //判断邮箱验证码是否于redis一致
        Object redusCodeObj = redisUtils.get(email);
        if (StringUtils.isEmpty(redusCodeObj)) {
            code.setMsg(Constants.CODE_407_ERROR);
            code.setCode(Constants.CODE_407);
            log.error(code.toString());
            return (JSON) JSON.toJSON(code);
        }else {
            redisEmailCode = redusCodeObj.toString();
            if (!emailCode.equals(redisEmailCode)){
                code.setMsg(Constants.CODE_407_ERROR);
                code.setCode(Constants.CODE_407);
                log.error(code.toString());
                return (JSON) JSON.toJSON(code);
            }
        }


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
                    path  =  FastDFS.uploadByFastDFS(file);
                    path = "http://47.92.137.1:8088/" +path;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (MyException e) {
                    e.printStackTrace();
                }


            }
        }
        //执行插入操作
        SysUser sysUser = new SysUser();
        sysUser.setUname(uname);
        sysUser.setPwd(pwd);
        sysUser.setUserid((int) ((Math.random() * 9 + 1) * 10000000));
        sysUser.setEmail(email);
        sysUser.setCreatetime(MyDateUtils.getDate("yyyy-MM-dd HH:mm:ss.SSS"));
        sysUser.setImgs(path);

        int flag = sysUserService.inst(sysUser);
        if (flag != 1) {
            code.setMsg(Constants.CODE_444_ERROR);
            code.setCode(Constants.CODE_444);
            log.error(code.toString());
        }

        code.setMsg(Constants.CODE_200_MSG);
        code.setCode(Constants.CODE_200);
        code.setInfo(sysUser);

        return (JSON) JSON.toJSON(code);
    }

    /**
     * 用户重复判断
     * @param name
     * @return
     */
    @PostMapping("getUserNmae/{name}")
    public JSON getUserNmae(@PathVariable String name){
        Code code = new Code();

        String nameStr = sysUserService.getUserNmae(name);
        if (nameStr == null){
            code.setCode(Constants.CODE_200);
        }
        code.setCode(Constants.CODE_445);
        code.setMsg(Constants.CODE_445_ERROR);
        return (JSON) JSON.toJSON(code);
    }


}
