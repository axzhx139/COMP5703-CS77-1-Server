package com.gday.trackmygrocery.vo.params;

import lombok.Data;

@Data
public class LoginParam {
    private String email;
    private String pwd;

    public LoginParam() {

    }

    public LoginParam(String email, String pwd) {
        this.email = email;
        this.pwd = pwd;
    }
}

