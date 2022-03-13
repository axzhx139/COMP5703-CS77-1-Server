package com.gday.trackmygrocery.vo.params;

import lombok.Data;

@Data
public class ResetPwdParam {
    int id;
    String prePwd;
    String newPwd;
}
