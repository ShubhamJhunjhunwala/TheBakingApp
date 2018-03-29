package com.app.shubhamjhunjhunwala.thebakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.shubhamjhunjhunwala.thebakingapp.Objects.Dish;
import com.app.shubhamjhunjhunwala.thebakingapp.Objects.Ingredient;
import com.app.shubhamjhunjhunwala.thebakingapp.Objects.Step;

import java.util.List;

/**
 * Created by shubham on 17/03/18.
 */

public class StepsArrayAdapter extends RecyclerView.Adapter<StepsArrayAdapter.ViewHolder> {

    public Context context;
    public Step[] steps;
    public boolean itemSelected = false;
    public int selectionID;

    public interface OnStepClickListener {
        void onStepCLicked(int position);
    }

    public OnStepClickListener mOnStepClickListener;

    public StepsArrayAdapter(Context context, Step[] steps, boolean itemSelected, int selectionID, OnStepClickListener onStepClickListener) {
        this.context = context;
        this.steps = steps;
        this.itemSelected = itemSelected;
        this.selectionID = selectionID;
        mOnStepClickListener = onStepClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.steps_list_item, parent, false);

        return new StepsArrayAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        holder.textView.setTextColor(context.getResources().getColor(R.color.colorBlack));

        if (itemSelected && selectionID == position) {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            holder.textView.setTextColor(context.getResources().getColor(R.color.colorWhite));
        }

        holder.textView.setText(steps[position].getShortDescription());
    }

    @Override
    public int getItemCount() {
        return steps.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textView;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.card_view);
            textView = (TextView) itemView.findViewById(R.id.text_view);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnStepClickListener.onStepCLicked(getAdapterPosition());
        }
    }
}
