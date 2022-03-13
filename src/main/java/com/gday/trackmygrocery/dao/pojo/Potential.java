package com.gday.trackmygrocery.dao.pojo;

import lombok.Data;

@Data
public class Potential {
    private Integer pId;
    private Integer uId;
    private Integer itemId;
    private String name;
    private String picture;
    private String status;
}
