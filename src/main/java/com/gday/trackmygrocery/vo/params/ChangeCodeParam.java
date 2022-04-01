package com.gday.trackmygrocery.vo.params;

import lombok.Data;

@Data
public class ChangeCodeParam extends LoginParam{
    private String verification_code;
}
