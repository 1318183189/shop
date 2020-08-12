package com.room302.shop.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SysPermission {
    private Integer permissionid;

    private Boolean available;

    private Long parentid;

    private String parentids;

    private String permission;

    private String permissionname;

    private String resourcetype;

    private String url;
}