package com.room302.shop.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class SysUser {
    private Integer userid;

    private Date createtime;

    private String email;

    private Date updatedate;

    private String pwd;

    private String salt;

    private Integer states;

    private String uname;

    private String imgs;

    private String token;


}