package com.gday.trackmygrocery.dao.pojo;

import lombok.Data;

import java.util.List;

@Data
public class RecipeCache {

    private Integer itemCount;
    private Integer inStockCount;
    private List<Recipe> recipes;

}
