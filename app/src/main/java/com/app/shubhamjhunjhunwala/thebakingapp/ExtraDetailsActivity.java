package com.app.shubhamjhunjhunwala.thebakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.shubhamjhunjhunwala.thebakingapp.Objects.Dish;

import org.parceler.Parcels;

import java.text.ParseException;

/**
 * Created by shubham on 17/03/18.
 */

public class ExtraDetailsActivity extends AppCompatActivity {

    public Dish dish;
    public int stepID = -1;

    public TextView previousTextView;
    public TextView nextTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extra_details_activity);

        if (savedInstanceState == null) {
            dish = Parcels.unwrap(getIntent().getParcelableExtra("Dish"));
        } else {
            dish = Parcels.unwrap(savedInstanceState.getParcelable("Dish"));
            stepID = savedInstanceState.getInt("StepID");
        }

        previousTextView = findViewById(R.id.previous_button);
        nextTextView = findViewById(R.id.next_button);

        ExtraDetailsFragment detailsFragment = new ExtraDetailsFragment();
        detailsFragment.dish = dish;
        if (stepID == -1) {
            stepID = getIntent().getIntExtra("ID", 0);
        }
        detailsFragment.stepID = stepID;

        setTitle("Step " + (stepID + 1));

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.extra_details_frame_layout, detailsFragment)
                .commit();

        previousTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stepID > 0) {
                    stepID--;

                    ExtraDetailsFragment detailsFragment = new ExtraDetailsFragment();
                    detailsFragment.dish = dish;
                    detailsFragment.stepID = stepID;

                    FragmentManager fragmentManager = getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.extra_details_frame_layout, detailsFragment)
                            .commit();

                    setTitle("Step " + (stepID + 1));
                }
            }
        });

        nextTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stepID < dish.getSteps().length) {
                    stepID++;

                    ExtraDetailsFragment detailsFragment = new ExtraDetailsFragment();
                    detailsFragment.dish = dish;
                    detailsFragment.stepID = stepID;

                    FragmentManager fragmentManager = getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.extra_details_frame_layout, detailsFragment)
                            .commit();

                    setTitle("Step " + (stepID + 1));
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("Dish", Parcels.wrap(dish));
        outState.putInt("StepID", stepID);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.e("Extra Details Activity", dish.toString());
                Intent intent = new Intent(this, DetailsActivity.class);
                intent.putExtra("Dish", Parcels.wrap(dish));
                startActivity(intent);
                finish();
                return true;
        }
        return false;
    }

}
