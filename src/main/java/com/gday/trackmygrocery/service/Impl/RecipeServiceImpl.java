package com.gday.trackmygrocery.service.Impl;

import com.alibaba.fastjson.JSON;
import com.gday.trackmygrocery.dao.pojo.Item;
import com.gday.trackmygrocery.dao.pojo.Recipe;
import com.gday.trackmygrocery.dao.pojo.RecipeCache;
import com.gday.trackmygrocery.mapper.ItemMapper;
import com.gday.trackmygrocery.mapper.UserMapper;
import com.gday.trackmygrocery.service.ItemService;
import com.gday.trackmygrocery.service.RecipeService;
import com.gday.trackmygrocery.utils.LogUtils;
import com.gday.trackmygrocery.utils.UrlUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class RecipeServiceImpl implements RecipeService {

    final OkHttpClient client = new OkHttpClient();
    private static final String recipeURL = "https://api.spoonacular.com/recipes/findByIngredients";
    private static final String randomRecipeURL = "https://api.spoonacular.com/recipes/random";
    private static final String apiKey1 = "f2e928acc90e458fadc5bcba6c45e251";
    private static final String apiKey2 = "d366b3791d9048f5ab9c741361b0e126";
    private static final String RECIPE_NUM = "12";
    private static final int CHECK_EXPIRE_NUM = 5;
    final Logger logger = LoggerFactory.getLogger(getClass());
    final LogUtils logUtils = LogUtils.getInstance();
    private static Map<Integer, RecipeCache> recipeCacheMap = new HashMap<>();

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public List<Recipe> getRecipeById(int id) {
        if (recipeCacheMap.containsKey(id) && compareCache(recipeCacheMap.get(id), id)) {
            logger.info("getRecipeById---Using Cache!");
            return recipeCacheMap.get(id).getRecipes();
        }
        List<Item> items = itemService.getInStockItemById(id);
        Map<String,String> map = new HashMap<>();
        map.put("apiKey",apiKey1);
        map.put("ingredients",getItemList(items));
        map.put("number",RECIPE_NUM);
        String finalUrl = UrlUtils.appendParams(recipeURL, map);
//        System.out.println(finalUrl);
        List<Recipe> res;
        RecipeCache recipeCache = new RecipeCache();
        recipeCache.setInStockCount(itemMapper.selectInStockItemCountByUserId(id));
        recipeCache.setItemCount(itemMapper.selectItemCountByUserId(id));
        try {
            res = JSON.parseArray(run(finalUrl), Recipe.class);
            recipeCache.setRecipes(res);
            recipeCacheMap.put(id, recipeCache);
            return res;
        } catch (IOException e) {
            try {
                map.put("apiKey",apiKey2);
                finalUrl = UrlUtils.appendParams(recipeURL, map);
                res = JSON.parseArray(run(finalUrl), Recipe.class);
                recipeCache.setRecipes(res);
                recipeCacheMap.put(id, recipeCache);
                return res;
            }catch (Exception e1){
                e.printStackTrace();
            }
        }
        return null;
    }

    private boolean compareCache(RecipeCache recipeCache, int userID) {
        int inStock = itemMapper.selectInStockItemCountByUserId(userID);
        int item = itemMapper.selectItemCountByUserId(userID);
        if (inStock == recipeCache.getInStockCount() && item == recipeCache.getItemCount()) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public List<Recipe> getRandomRecipe() {
        Map<String,String> map = new HashMap<>();
        map.put("apiKey",apiKey1);
        map.put("number",RECIPE_NUM);
        String finalUrl = UrlUtils.appendParams(randomRecipeURL, map);
        try {
            return (List<Recipe>) JSON.parseObject(run(finalUrl)).get("recipes");
        } catch (IOException e) {
            try {
                map.put("apiKey",apiKey2);
                finalUrl = UrlUtils.appendParams(randomRecipeURL, map);
                return (List<Recipe>) JSON.parseObject(run(finalUrl)).get("recipes");
            }catch (Exception e1){
                e.printStackTrace();
            }

        }
        return null;

    }

    @Override
    public List<Recipe> getRecipeByIdAndExpire(int id) {
        List<Item> items = checkExpire(itemService.getInStockItemById(id));
        if(items.size()==0) return new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("apiKey",apiKey1);
        map.put("ingredients",getItemList(items));
        map.put("number",RECIPE_NUM);
        String finalUrl = UrlUtils.appendParams(recipeURL, map);
//        System.out.println(finalUrl);
        try {
            return JSON.parseArray(run(finalUrl), Recipe.class);
        } catch (IOException e) {
            try {
                map.put("apiKey",apiKey2);
                finalUrl = UrlUtils.appendParams(recipeURL, map);
                return JSON.parseArray(run(finalUrl), Recipe.class);
            }catch (Exception e1){
                e.printStackTrace();
            }
//            e.printStackTrace();
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
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
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
