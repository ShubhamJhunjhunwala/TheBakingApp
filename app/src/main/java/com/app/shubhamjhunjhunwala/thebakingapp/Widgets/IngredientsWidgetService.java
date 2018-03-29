package com.app.shubhamjhunjhunwala.thebakingapp.Widgets;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

/**
 * Created by shubham on 19/03/18.
 */

public class IngredientsWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d("Widget Service", "Running");
        return new WidgetDataProvider(this.getApplicationContext(), intent);
    }
}
