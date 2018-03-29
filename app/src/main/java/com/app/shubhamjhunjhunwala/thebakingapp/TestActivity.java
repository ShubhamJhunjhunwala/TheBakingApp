package com.app.shubhamjhunjhunwala.thebakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

/**
 * Created by shubham on 24/03/18.
 */

@VisibleForTesting
public class TestActivity extends AppCompatActivity {

    public int CONTAINER_ID = 1;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(CONTAINER_ID);
        setContentView(frameLayout);
    }
}