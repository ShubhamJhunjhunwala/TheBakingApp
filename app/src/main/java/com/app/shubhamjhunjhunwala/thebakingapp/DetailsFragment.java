package com.app.shubhamjhunjhunwala.thebakingapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.shubhamjhunjhunwala.thebakingapp.Objects.Dish;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.google.android.exoplayer2.LoadControl;

import org.parceler.Parcels;

import java.util.List;

/**
 * Created by shubham on 17/03/18.
 */

public class DetailsFragment extends Fragment implements StepsArrayAdapter.OnStepClickListener {

    public DetailsFragment() {}

    public Dish dish;

    public RecyclerView stepsListView;

    public boolean twoPaneLayout = false;

    public static StepsArrayAdapter stepsArrayAdapter;
    public static LinearLayoutManager stepsLinearLayoutManager;
    public Boolean itemSelected = false;
    public int selectionID;

    public Parcelable detailsListState;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View rootView;

        rootView = inflater.inflate(R.layout.details_fragment, container, false);

        RecyclerView ingredientsListView = (RecyclerView) rootView.findViewById(R.id.ingredients_list_view);
        stepsListView = (RecyclerView) rootView.findViewById(R.id.steps_list_view);

        if (savedInstanceState != null) {
            dish =  Parcels.unwrap(savedInstanceState.getParcelable("Dish"));
            twoPaneLayout = savedInstanceState.getBoolean("TwoPane");
            itemSelected = savedInstanceState.getBoolean("ItemSelected");
            selectionID = savedInstanceState.getInt("SelectionID");
            detailsListState = savedInstanceState.getParcelable("List State");
        }

        Log.d("Details Fragment", dish.toString());

        IngredientsArrayAdpater ingredientsArrayAdpater = new IngredientsArrayAdpater(getContext(), dish.getIngredients());
        LinearLayoutManager ingredientsLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        ingredientsListView.setLayoutManager(ingredientsLinearLayoutManager);
        ingredientsListView.setAdapter(ingredientsArrayAdpater);

        stepsArrayAdapter = new StepsArrayAdapter(getContext(), dish.getSteps(), itemSelected, selectionID, this);
        stepsLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        stepsListView.setLayoutManager(stepsLinearLayoutManager);
        stepsListView.setAdapter(stepsArrayAdapter);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d("onSaveInstanceState", "Saving State");

        outState.putParcelable("Dish", Parcels.wrap(dish));
        outState.putBoolean("TwoPane", twoPaneLayout);
        outState.putBoolean("ItemSelected", itemSelected);
        outState.putInt("SelectionID", selectionID);
        outState.putParcelable("List State", stepsLinearLayoutManager.onSaveInstanceState());
    }

    public void changeAdapterItemSelection(int id) {
        stepsArrayAdapter.selectionID = id;
        stepsArrayAdapter.notifyDataSetChanged();
        Log.d("Details Fragment", "changeAdapterSelection at Position" + Integer.toString(id));
    }

    @Override
    public void onStepCLicked(int position) {
        if (!twoPaneLayout) {
            Intent intent = new Intent(getContext(), ExtraDetailsActivity.class);
            intent.putExtra("Dish", Parcels.wrap(dish));
            intent.putExtra("ID", position);

            Bundle outState = new Bundle();
            outState.putParcelable("Dish", Parcels.wrap(dish));
            onSaveInstanceState(outState);

            Log.d("Details Fragment", "Starting New Activity");

            startActivity(intent);
        } else {
            ((DetailsActivity) getActivity()).showExtraDetails(position);

            stepsArrayAdapter.itemSelected = true;
            stepsArrayAdapter.selectionID = position;
            stepsArrayAdapter.notifyDataSetChanged();

            itemSelected = true;
            selectionID = position;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (detailsListState != null) {
            stepsLinearLayoutManager.onRestoreInstanceState(detailsListState);
        }
    }
}
