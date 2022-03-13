package com.gday.trackmygrocery.vo.params;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class RemindParam {
    private Integer userId;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date remindDate;
}
