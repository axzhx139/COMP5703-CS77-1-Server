package com.gday.trackmygrocery.service;

import com.gday.trackmygrocery.dao.pojo.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> getRecipeById(int id);

    List<Recipe> getRandomRecipe();

    List<Recipe> getRecipeByIdAndExpire(int id);

}
