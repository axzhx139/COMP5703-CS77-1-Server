package com.gday.trackmygrocery.dao.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Recipe {
    int id;
    String image;
    String imageType;
    int likes;
    int missedIngredientCount;
    List<Ingredient> missedIngredients;
    String title;
    List<Ingredient> unusedIngredients;
    int usedIngredientCount;
    List<Ingredient> usedIngredients;
}
