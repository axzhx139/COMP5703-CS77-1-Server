package com.gday.trackmygrocery.vo;

import lombok.Data;

@Data
public class UserVo {

    private long u_id;
    private String name;
    private String email;
    private String pwd;
    private int gender;

}
