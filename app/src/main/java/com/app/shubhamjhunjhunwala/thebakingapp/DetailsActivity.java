package com.app.shubhamjhunjhunwala.thebakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.shubhamjhunjhunwala.thebakingapp.Objects.Dish;

import org.parceler.Parcels;

/**
 * Created by shubham on 17/03/18.
 */

public class DetailsActivity extends AppCompatActivity {

    public Dish dish;

    public boolean twoPaneLayout = false;

    public boolean extraDetailsShowing = false;

    public TextView previousTextView;
    public TextView nextTextView;

    public int stepID;

    public static DetailsFragment detailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        if (savedInstanceState != null) {
            dish =  Parcels.unwrap(savedInstanceState.getParcelable("Dish"));
            twoPaneLayout = savedInstanceState.getBoolean("TwoPane");
            extraDetailsShowing = savedInstanceState.getBoolean("ExtraDetailsShowing");
            stepID = savedInstanceState.getInt("StepID");
        }

        if (findViewById(R.id.relative_layout) != null) {
            twoPaneLayout = true;
            previousTextView = findViewById(R.id.previous_button);
            nextTextView = findViewById(R.id.next_button);

            if (!extraDetailsShowing) {
                previousTextView.setVisibility(View.GONE);
                nextTextView.setVisibility(View.GONE);
            } else {
                detailsFragment.changeAdapterItemSelection(stepID);

                previousTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        stepID--;

                        if (stepID >= 0) {

                            ExtraDetailsFragment extraDetailsFragment = new ExtraDetailsFragment();
                            extraDetailsFragment.dish = dish;
                            extraDetailsFragment.stepID = stepID;
                            extraDetailsFragment.twoPaneLayout = twoPaneLayout;

                            FragmentManager fragmentManager = getSupportFragmentManager();

                            fragmentManager.beginTransaction()
                                    .replace(R.id.extra_details_frame_layout, extraDetailsFragment)
                                    .commit();

                            detailsFragment.changeAdapterItemSelection(stepID);
                        } else {
                            stepID++;
                        }
                    }
                });

                nextTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        stepID++;

                        if (stepID < dish.getSteps().length) {

                            ExtraDetailsFragment extraDetailsFragment = new ExtraDetailsFragment();
                            extraDetailsFragment.dish = dish;
                            extraDetailsFragment.stepID = stepID;
                            extraDetailsFragment.twoPaneLayout = twoPaneLayout;

                            FragmentManager fragmentManager = getSupportFragmentManager();

                            fragmentManager.beginTransaction()
                                    .replace(R.id.extra_details_frame_layout, extraDetailsFragment)
                                    .commit();

                            detailsFragment.changeAdapterItemSelection(stepID);
                        } else {
                            stepID--;
                        }
                    }
                });
            }
        } else {
            twoPaneLayout = false;
        }

        if (savedInstanceState == null) {
            detailsFragment = new DetailsFragment();
            dish = Parcels.unwrap(getIntent().getParcelableExtra("Dish"));
            detailsFragment.dish = dish;
            detailsFragment.twoPaneLayout = twoPaneLayout;

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                            .add(R.id.frame_layout, detailsFragment)
                            .commit();
        }

        setTitle(dish.getName());
    }

    public void showExtraDetails(final int id) {
        stepID = id;

        previousTextView.setVisibility(View.VISIBLE);
        nextTextView.setVisibility(View.VISIBLE);

        ExtraDetailsFragment extraDetailsFragment = new ExtraDetailsFragment();
        extraDetailsFragment.dish = dish;
        extraDetailsFragment.stepID = id;
        extraDetailsFragment.twoPaneLayout = twoPaneLayout;

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (!extraDetailsShowing) {
            fragmentManager.beginTransaction()
                    .add(R.id.extra_details_frame_layout, extraDetailsFragment)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.extra_details_frame_layout, extraDetailsFragment)
                    .commit();
        }

        extraDetailsShowing = true;

        previousTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stepID--;

                if (stepID >= 0) {

                    ExtraDetailsFragment extraDetailsFragment = new ExtraDetailsFragment();
                    extraDetailsFragment.dish = dish;
                    extraDetailsFragment.stepID = stepID;
                    extraDetailsFragment.twoPaneLayout = twoPaneLayout;

                    FragmentManager fragmentManager = getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.extra_details_frame_layout, extraDetailsFragment)
                            .commit();

                    detailsFragment.changeAdapterItemSelection(stepID);
                } else {
                    stepID++;
                }
            }
        });

        nextTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stepID++;

                if (stepID < dish.getSteps().length) {

                    ExtraDetailsFragment extraDetailsFragment = new ExtraDetailsFragment();
                    extraDetailsFragment.dish = dish;
                    extraDetailsFragment.stepID = stepID;
                    extraDetailsFragment.twoPaneLayout = twoPaneLayout;

                    FragmentManager fragmentManager = getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.extra_details_frame_layout, extraDetailsFragment)
                            .commit();

                    detailsFragment.changeAdapterItemSelection(stepID);
                } else {
                    stepID--;
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("Dish", Parcels.wrap(dish));
        outState.putBoolean("TwoPane", twoPaneLayout);
        outState.putBoolean("ExtraDetailsShowing", extraDetailsShowing);
        outState.putInt("StepID", stepID);
    }
}
