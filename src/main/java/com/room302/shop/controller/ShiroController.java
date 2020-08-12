package com.room302.shop.controller;


import com.room302.shop.model.SysUser;
import com.room302.shop.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @created Create Time: 2019/2/16
 */

@Slf4j
@RestController
@RequestMapping(value = "/", produces = {"application/json;charset=UTF-8"})
public class ShiroController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 验证用户信息，用于登陆
     *
     * @param sysUser
     * @return
     */
    @PostMapping("login")
    public SysUser verificationUser(SysUser sysUser) {
        SysUser sysUserToken = null;
        if (sysUser.getPwd() == null && sysUser.getUname() == null){

        }
        //验证用户信息
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(sysUser.getUname(),sysUser.getPwd());
        Subject subject = SecurityUtils.getSubject();
        try {
            //完成登录
            subject.login(usernamePasswordToken);
             sysUserToken = new SysUser();
            sysUserToken.setToken(subject.getSession().getId().toString());
            //用户名获取头像
            String userImg =  sysUserService.getImgsbyName(sysUser.getUname());
            sysUserToken.setImgs(userImg);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return sysUserToken;
    }

    /**
     * 手动退出
     * @param httpServletRequest
     * @return
     */
    @GetMapping("logout")
    public String logout(HttpServletRequest httpServletRequest) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return  "退出成功";
    }

    /**
     * 自动退出 退出后会重定向到跟目录
     * @return
     */
    @GetMapping("logout2")
    public String logout2() {
        return  "退出成功";
    }
}

