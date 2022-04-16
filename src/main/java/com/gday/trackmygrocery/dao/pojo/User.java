package com.gday.trackmygrocery.dao.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    private Integer uId;

    private String name;

    private String email;

    private String pwd;

    private Integer gender;

    private Integer alert;

    private String address;

    private Date birthday;

    private String avatar;

    private String token;
    
    private String verification_code;

    private Integer verification_code_status;// 0:未验证, 1:已验证 03/28/2022: -1 正在改密码 改好-》变成1

    private String uuid;

    private Date ranking_start_date;

    private Integer ranking_days;
}
