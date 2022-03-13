package com.gday.trackmygrocery.dao.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Ingredient {
    String aisle;
    double amount;
    int id;
    String image;
    List<String> meta;
    String name;
    String original;
    String originalName;
    String unit;
    String unitLong;
    String unitShort;
}
