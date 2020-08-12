package com.room302.shop.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Code implements Serializable {
    private static final long serialVersionUID = 1206060251389630692L;

    private Integer code;

    private String msg;

    private Object info;

}
