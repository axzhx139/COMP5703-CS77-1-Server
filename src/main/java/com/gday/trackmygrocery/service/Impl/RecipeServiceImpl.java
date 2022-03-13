package com.gday.trackmygrocery.service.Impl;

import com.alibaba.fastjson.JSON;
import com.gday.trackmygrocery.dao.pojo.Item;
import com.gday.trackmygrocery.dao.pojo.Recipe;
import com.gday.trackmygrocery.service.ItemService;
import com.gday.trackmygrocery.service.RecipeService;
import com.gday.trackmygrocery.utils.UrlUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class RecipeServiceImpl implements RecipeService {

    final OkHttpClient client = new OkHttpClient();
    private static final String recipeURL = "https://api.spoonacular.com/recipes/findByIngredients";
    private static final String randomRecipeURL = "https://api.spoonacular.com/recipes/random";
    private static final String apiKey = "f2e928acc90e458fadc5bcba6c45e251";
    private static final String RECIPE_NUM = "4";
    private static final int CHECK_EXPIRE_NUM = 5;

    @Autowired
    private ItemService itemService;

    @Override
    public List<Recipe> getRecipeById(int id) {
        List<Item> items = itemService.getInStockItemById(id);
        Map<String,String> map = new HashMap<>();
        map.put("apiKey",apiKey);
        map.put("ingredients",getItemList(items));
        map.put("number",RECIPE_NUM);
        String finalUrl = UrlUtils.appendParams(recipeURL, map);
//        System.out.println(finalUrl);

        try {
            return JSON.parseArray(run(finalUrl), Recipe.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Recipe> getRandomRecipe() {
        Map<String,String> map = new HashMap<>();
        map.put("apiKey",apiKey);
        map.put("number",RECIPE_NUM);
        String finalUrl = UrlUtils.appendParams(randomRecipeURL, map);
        try {
            return (List<Recipe>) JSON.parseObject(run(finalUrl)).get("recipes");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public List<Recipe> getRecipeByIdAndExpire(int id) {
        List<Item> items = checkExpire(itemService.getInStockItemById(id));
        if(items.size()==0) return new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("apiKey",apiKey);
        map.put("ingredients",getItemList(items));
        map.put("number",RECIPE_NUM);
        String finalUrl = UrlUtils.appendParams(recipeURL, map);
//        System.out.println(finalUrl);

        try {
            return JSON.parseArray(run(finalUrl), Recipe.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Item> checkExpire(List<Item> inStockItemById) {
        int count = CHECK_EXPIRE_NUM;
        List<Item> result = new ArrayList<>();
        for(Item item : inStockItemById){
            Date expireDate = item.getExpDate();
            Date curDate = new Date();
            if(calculateTimeGapDay(expireDate,curDate) >= 10){
                result.add(item);
                count--;
            }
            if(count==0) break;
        }
        return result;
    }

    private int calculateTimeGapDay(Date time1, Date time2) {
        int day = 0;
        try {
            long millisecond = time2.getTime() - time1.getTime();
            day = (int) (millisecond / (24 * 60 * 60 * 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (day);
    }


    private String getItemList(List<Item> items){
        StringBuilder sb = new StringBuilder();
        for(Item item : items){
                sb.append(item.getName()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return Objects.requireNonNull(response.body()).string();
        }
    }

}
