package com.app.shubhamjhunjhunwala.thebakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.app.shubhamjhunjhunwala.thebakingapp.Objects.Dish;
import com.app.shubhamjhunjhunwala.thebakingapp.Widgets.IngredientsWidget;
import com.google.gson.Gson;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by shubham on 20/03/18.
 */

public class SettingsActivity extends AppCompatActivity {

    public RadioButton nutellaPieRadioButton;
    public RadioButton brownniesRadioButton;
    public RadioButton yellowCakeRadioButton;
    public RadioButton cheesacakeRadioButton;

    public SharedPreferences sharedPref;
    public SharedPreferences.Editor sharedPrefEditor;

    public ArrayList<Dish> dishes;

    public int dishID = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (savedInstanceState == null) {
            dishes = Parcels.unwrap(getIntent().getParcelableExtra("Dishes"));
        } else {
            dishes = Parcels.unwrap(savedInstanceState.getParcelable("Dishes"));
        }

        nutellaPieRadioButton = (RadioButton) findViewById(R.id.nutella_pie_radio_button);
        brownniesRadioButton = (RadioButton) findViewById(R.id.brownies_radio_button);
        yellowCakeRadioButton = (RadioButton) findViewById(R.id.yellow_cake_radio_button);
        cheesacakeRadioButton = (RadioButton) findViewById(R.id.cheesecake_radio_button);

        sharedPref = getSharedPreferences("Widget Dish Ingredients", Context.MODE_PRIVATE);
        sharedPrefEditor = sharedPref.edit();

        dishID = sharedPref.getInt("Dish ID", 1);

        if (dishID != -1 && dishID == 1) {
            nutellaPieRadioButton.setChecked(true);
        } else if (dishID != -1 && dishID == 2) {
            brownniesRadioButton.setChecked(true);
        } else if (dishID != -1 && dishID == 3) {
            yellowCakeRadioButton.setChecked(true);
        } else if (dishID != -1 && dishID == 4) {
            cheesacakeRadioButton.setChecked(true);
        } else {
            Log.e("Settings Activity", "No Data in Shared Preferences");
            sharedPrefEditor.putInt("Dish ID", 1).apply();
            sharedPrefEditor.putString("Dish Name", "Nutella Pie").apply();
        }

        nutellaPieRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    sharedPrefEditor.putInt("Dish ID", 1).apply();
                    sharedPrefEditor.putString("Dish Name", "Nutella Pie").apply();
                    updateWidget();
                }
            }
        });

        brownniesRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    sharedPrefEditor.putInt("Dish ID", 2).apply();
                    sharedPrefEditor.putString("Dish Name", "Brownies").apply();
                    updateWidget();
                }
            }
        });

        yellowCakeRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    sharedPrefEditor.putInt("Dish ID", 3).apply();
                    sharedPrefEditor.putString("Dish Name", "Yellow Cake").apply();
                    updateWidget();
                }
            }
        });

        cheesacakeRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    sharedPrefEditor.putInt("Dish ID", 4).apply();
                    sharedPrefEditor.putString("Dish Name", "Cheesecake").apply();
                    updateWidget();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("Dishes", Parcels.wrap(dishes));
    }

    public void updateWidget() {
        Intent intent = new Intent(this, IngredientsWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), IngredientsWidget.class));

        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);

        sendBroadcast(intent);
    }
}
