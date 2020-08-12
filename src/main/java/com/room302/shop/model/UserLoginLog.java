package com.room302.shop.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class UserLoginLog {
    private Integer uuid;

    private Integer userId;

    private String loginIp;

    private Date loginStartTime;

    private Date exitTime;


}