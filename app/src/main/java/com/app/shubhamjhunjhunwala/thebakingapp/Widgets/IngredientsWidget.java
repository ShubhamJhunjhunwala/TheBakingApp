package com.app.shubhamjhunjhunwala.thebakingapp.Widgets;

import android.app.PendingIntent;

import android.appwidget.AppWidgetManager;

import android.appwidget.AppWidgetProvider;

import android.content.ComponentName;

import android.content.Context;

import android.content.Intent;

import android.content.SharedPreferences;
import android.net.Uri;

import android.os.AsyncTask;
import android.util.Log;

import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.app.shubhamjhunjhunwala.thebakingapp.MainActivity;
import com.app.shubhamjhunjhunwala.thebakingapp.MasterListAdapter;
import com.app.shubhamjhunjhunwala.thebakingapp.MasterListFragment;
import com.app.shubhamjhunjhunwala.thebakingapp.Objects.Dish;
import com.app.shubhamjhunjhunwala.thebakingapp.R;
import com.app.shubhamjhunjhunwala.thebakingapp.Utils.JSONUtils;

import org.json.JSONException;
import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

public class IngredientsWidget extends AppWidgetProvider {

    public static final String UPDATE_MEETING_ACTION = "android.appwidget.action.APPWIDGET_UPDATE";

    public static final String EXTRA_ITEM = "com.example.edockh.EXTRA_ITEM";

    public ArrayList<Dish> dishes;

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        FetchAsync fetchAsync = new FetchAsync(context, appWidgetManager, appWidgetIds);

        String data = null;

        try {
            data = fetchAsync.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < appWidgetIds.length; ++i) {

            Intent intent = new Intent(context, IngredientsWidgetService.class);

            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);

            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            intent.putExtra("FetchedData", data);

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);

            rv.setRemoteAdapter(appWidgetIds[i], R.id.list_view, intent);

            Intent startActivityIntent = new Intent(context, MainActivity.class);

            PendingIntent startActivityPendingIntent = PendingIntent.getActivity(context, 0, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            rv.setPendingIntentTemplate(R.id.list_view, startActivityPendingIntent);

            SharedPreferences sharedPreferences = context.getSharedPreferences("Widget Dish Ingredients", MODE_PRIVATE);

            String dishName = sharedPreferences.getString("Dish Name", "Nutella Pie");

            rv.setTextViewText(R.id.dish_name,dishName);

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view);

            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);

        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);

    }

}
