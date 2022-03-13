package com.gday.trackmygrocery.vo;

import lombok.Data;

@Data
public class Profile {
    private String name;
    private Integer gender;
    private String address;
    private Integer totalItemCount;
    private Integer consumedItemCount;
    private Integer expiredItemCount;
    private Integer instockItemCount;
}

