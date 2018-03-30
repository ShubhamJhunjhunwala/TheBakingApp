package com.app.shubhamjhunjhunwala.thebakingapp.Widgets;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.app.shubhamjhunjhunwala.thebakingapp.Objects.Dish;
import com.app.shubhamjhunjhunwala.thebakingapp.R;
import com.app.shubhamjhunjhunwala.thebakingapp.Utils.JSONUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by shubham on 19/03/18.
 */

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    public Intent intent;
    private ArrayList<Dish> dishes;
    private Dish dish;
    private int dishID;

    public WidgetDataProvider(Context context,Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    @Override
    public void onCreate() {
        dishes=new ArrayList<>();
    }

    @Override
    public void onDataSetChanged() {
        try {
            dishes = new JSONUtils().getJSONResponceFromURL(intent.getStringExtra("FetchedData"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Ingredients Widget PRVD", new Gson().toJson(dishes));

        SharedPreferences sharedPreferences = context.getSharedPreferences("Widget Dish Ingredients", MODE_PRIVATE);

        dishID = sharedPreferences.getInt("Dish ID", 1);

        dish = dishes.get(dishID - 1);
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return dish.getIngredients().length;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_ingredients_list_item);

        String data = dish.getIngredients()[i].getQuantity()
                + " "
                + dish.getIngredients()[i].getMeasurement()
                + " "
                + dish.getIngredients()[i].getIngredient();

        remoteViews.setTextViewText(R.id.item, data);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}