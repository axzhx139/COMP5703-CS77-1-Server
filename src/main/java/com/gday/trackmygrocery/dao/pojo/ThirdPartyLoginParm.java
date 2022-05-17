package com.gday.trackmygrocery.dao.pojo;

import lombok.Data;

@Data
public class ThirdPartyLoginParm {
    private String email;
    private String nickname;
    private Boolean isNewUser;
}
