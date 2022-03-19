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

    private int verification_code_status;// 0:未验证, 1:已验证

    private String uuid;
}
