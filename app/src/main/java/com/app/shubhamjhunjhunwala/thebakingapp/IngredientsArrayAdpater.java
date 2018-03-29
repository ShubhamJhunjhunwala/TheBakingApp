package com.app.shubhamjhunjhunwala.thebakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.shubhamjhunjhunwala.thebakingapp.Objects.Dish;
import com.app.shubhamjhunjhunwala.thebakingapp.Objects.Ingredient;

import java.util.List;

/**
 * Created by shubham on 17/03/18.
 */

public class IngredientsArrayAdpater extends RecyclerView.Adapter<IngredientsArrayAdpater.ViewHolder> {

    public Context context;
    public Ingredient[] ingredients;

    public IngredientsArrayAdpater(@NonNull Context context, @NonNull Ingredient[] ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.ingredients_list_item, parent, false);

        return new IngredientsArrayAdpater.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(ingredients[position].getQuantity() + " " + ingredients[position].getMeasurement() + " " + ingredients[position].getIngredient());
    }

    @Override
    public int getItemCount() {
        return ingredients.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.text_view);
        }
    }
}
