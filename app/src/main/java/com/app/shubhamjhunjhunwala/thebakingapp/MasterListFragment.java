package com.app.shubhamjhunjhunwala.thebakingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.shubhamjhunjhunwala.thebakingapp.Objects.Dish;
import com.app.shubhamjhunjhunwala.thebakingapp.Utils.JSONUtils;

import org.json.JSONException;
import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by shubham on 16/03/18.
 */

public class MasterListFragment extends Fragment implements MasterListAdapter.OnItemClickListener {

    public MasterListFragment() {}

    public ArrayList<Dish> dishes;

    public MasterListAdapter masterListAdapter;

    public RelativeLayout loadingRelativeLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.master_list_fragment, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.dishes_recycler_view);

        loadingRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.loading_layout);

        masterListAdapter = null;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        if (savedInstanceState == null) {

            AsyncTask asyncTask = new AsyncTask() {
                public String data;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    loadingRelativeLayout.setVisibility(View.VISIBLE);
                }

                @Override
                protected Object doInBackground(Object[] objects) {
                    try {
                        data = JSONUtils.getResponseFromHTTPUrl(getContext());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);

                    if (data != null) {
                        try {
                            dishes = new JSONUtils().getJSONResponceFromURL(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    if (dishes != null) {
                        loadingRelativeLayout.setVisibility(View.GONE);
                        masterListAdapter = new MasterListAdapter(dishes, getContext(), MasterListFragment.this);
                        recyclerView.setAdapter(masterListAdapter);
                    } else {
                        Toast.makeText(getContext(), "No Dishes", Toast.LENGTH_SHORT).show();
                    }
                }
            };

            asyncTask.execute();

        } else {
            dishes = Parcels.unwrap(savedInstanceState.getParcelable("Dishes"));

            loadingRelativeLayout.setVisibility(View.GONE);

            masterListAdapter = new MasterListAdapter(dishes, getContext(), this);
            recyclerView.setAdapter(masterListAdapter);
        }

        return rootView;
    }

    @Override
    public void onItemCLicked(int position) {
        Intent intent = new Intent(getContext(), DetailsActivity.class);
        intent.putExtra("Dish", Parcels.wrap(dishes.get(position)));
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("Dishes", Parcels.wrap(dishes));
    }
}
