package com.gday.trackmygrocery.controller;

import com.gday.trackmygrocery.dao.pojo.Recipe;
import com.gday.trackmygrocery.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/{id}")
    public List<Recipe> getRecipeById(@PathVariable("id") int id){return recipeService.getRecipeById(id);}

    @GetMapping("/expire/{id}")
    public List<Recipe> getRecipeByIdAndExpire(@PathVariable("id") int id){return recipeService.getRecipeByIdAndExpire(id);}

    @GetMapping("/random")
    public List<Recipe> getRandomRecipe(){return recipeService.getRandomRecipe();}
}
