package com.gday.trackmygrocery.vo.params;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ProfileParam {
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date birthday;

    private Integer id;
    private String address;
    private String name;
    private Integer gender;
}
