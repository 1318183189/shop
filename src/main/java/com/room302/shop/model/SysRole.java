package com.room302.shop.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SysRole {
    private Integer roleid;

    private Boolean available;

    private String description;

    private String role;


}