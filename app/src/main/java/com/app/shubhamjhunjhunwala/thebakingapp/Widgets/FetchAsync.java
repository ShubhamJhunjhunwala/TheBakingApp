package com.app.shubhamjhunjhunwala.thebakingapp.Widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.os.AsyncTask;

import com.app.shubhamjhunjhunwala.thebakingapp.R;
import com.app.shubhamjhunjhunwala.thebakingapp.Utils.JSONUtils;

import java.io.IOException;

class FetchAsync extends AsyncTask<Void,Void,String> {
    Context context;
    String data;
    AppWidgetManager appWidgetManager;
    int[] appWidgetIds;

    public FetchAsync(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        this.context = context;
        this.appWidgetManager = appWidgetManager;
        this.appWidgetIds = appWidgetIds;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            data = JSONUtils.getResponseFromHTTPUrl(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view);
    }
}