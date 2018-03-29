package com.app.shubhamjhunjhunwala.thebakingapp.Utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.app.shubhamjhunjhunwala.thebakingapp.Objects.Dish;
import com.app.shubhamjhunjhunwala.thebakingapp.Objects.Ingredient;
import com.app.shubhamjhunjhunwala.thebakingapp.Objects.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by shubham on 13/03/18.
 */

public class JSONUtils {
    public String JSON;

    public ArrayList<Dish> dishes = new ArrayList<>();

    public static String SOURCE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static String getResponseFromHTTPUrl(Context context) throws IOException {
        URL url = new URL(SOURCE_URL);

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream inputStream = httpURLConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                String data = scanner.next();
                return data;
            } else {
                return null;
            }
        } finally {
            httpURLConnection.disconnect();
        }
    }

    public ArrayList<Dish> getJSONResponce(Context context) throws JSONException {

        try {
            InputStream inputStream = context.getAssets().open("data.json");
            int size = inputStream.available();

            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            JSON = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        JSONArray dishesJSONArray = new JSONArray(JSON);

        for (int i = 0, n = dishesJSONArray.length(); i < n; i++) {

            JSONObject dishJSONObject = dishesJSONArray.getJSONObject(i);

            Dish dish = new Dish(dishJSONObject.getString("id"),
                                    dishJSONObject.getString("name"),
                                    getIngredientsFromJSONObject(dishJSONObject),
                                    getStepsFromJSONObject(dishJSONObject),
                                    Integer.parseInt(dishJSONObject.getString("servings")),
                                    dishJSONObject.getString("image"));


            dishes.add(i, dish);
        }

        return dishes;
    }

    public ArrayList<Dish> getJSONResponceFromURL(String responce) throws JSONException {

        JSON = responce;

        JSONArray dishesJSONArray = new JSONArray(JSON);

        for (int i = 0, n = dishesJSONArray.length(); i < n; i++) {

            JSONObject dishJSONObject = dishesJSONArray.getJSONObject(i);

            Dish dish = new Dish(dishJSONObject.getString("id"),
                    dishJSONObject.getString("name"),
                    getIngredientsFromJSONObject(dishJSONObject),
                    getStepsFromJSONObject(dishJSONObject),
                    Integer.parseInt(dishJSONObject.getString("servings")),
                    dishJSONObject.getString("image"));

            Log.d("Dish", dish.toString());

            dishes.add(i, dish);
        }

        return dishes;
    }

    public Ingredient[] getIngredientsFromJSONObject (JSONObject dishJSONObject) throws JSONException {
        JSONArray ingredientsJSONArray = dishJSONObject.getJSONArray("ingredients");

        Ingredient[] ingredients = new Ingredient[ingredientsJSONArray.length()];

        for (int i = 0, n = ingredients.length; i < n; i++) {
            JSONObject ingredientJSONObject = ingredientsJSONArray.getJSONObject(i);

            Ingredient ingredient = new Ingredient(ingredientJSONObject.getString("quantity"),
                                                    ingredientJSONObject.getString("measure"),
                                                    ingredientJSONObject.getString("ingredient"));

            ingredients[i] = ingredient;
        }

        return ingredients;
    }

    public Step[] getStepsFromJSONObject (JSONObject dishJSONObject) throws JSONException {
        JSONArray stepsJSONArray = dishJSONObject.getJSONArray("steps");

        Step[] steps = new Step[stepsJSONArray.length()];

        for (int i = 0, n = steps.length; i < n; i++) {
            JSONObject stepJSONObject = stepsJSONArray.getJSONObject(i);

            Step step = new Step(stepJSONObject.getString("id"),
                                    stepJSONObject.getString("shortDescription"),
                                    stepJSONObject.getString("description"),
                                    stepJSONObject.getString("thumbnailURL"),
                                    stepJSONObject.getString("videoURL"));

            steps[i] = step;
        }

        return steps;
    }
}
