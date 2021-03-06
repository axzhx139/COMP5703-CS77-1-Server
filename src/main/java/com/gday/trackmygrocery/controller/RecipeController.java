package com.gday.trackmygrocery.controller;

import com.gday.trackmygrocery.dao.pojo.Recipe;
import com.gday.trackmygrocery.service.RecipeService;
import com.gday.trackmygrocery.utils.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("recipe")
public class RecipeController {

    final Logger logger = LoggerFactory.getLogger(getClass());
    final LogUtils logUtils = LogUtils.getInstance();

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/{id}")
    synchronized public List<Recipe> getRecipeById(@PathVariable("id") int id) {
        logger.info("getRecipeById<<<(id: int): " + id);
        List<Recipe> res = null;
        try {
            res = recipeService.getRecipeById(id);
        } catch (Exception e) {
            logger.error("Something wrong when getting recipes, check the API status please!");
        }
        logger.info("getRecipeById>>>" + logUtils.printListAsLog(res));
        return res;
    }

    @GetMapping("/expire/{id}")
    synchronized public List<Recipe> getRecipeByIdAndExpire(@PathVariable("id") int id) {
        logger.info("getRecipeByIdAndExpire<<<(id: int): " + id);
        List<Recipe> res = recipeService.getRecipeByIdAndExpire(id);
        logger.info("getRecipeByIdAndExpire>>>" + logUtils.printListAsLog(res));
        return res;
    }

    @GetMapping("/random")
    synchronized public List<Recipe> getRandomRecipe() {
        logger.info("getRandomRecipe<<<[Void]");
        List<Recipe> res = recipeService.getRandomRecipe();
        logger.info("getRandomRecipe>>>" + logUtils.printListAsLog(res));
        return res;
    }
}
