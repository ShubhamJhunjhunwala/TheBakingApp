package com.app.shubhamjhunjhunwala.thebakingapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.shubhamjhunjhunwala.thebakingapp.Objects.Dish;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by shubham on 17/03/18.
 */

public class MasterListAdapter extends RecyclerView.Adapter<MasterListAdapter.ViewHolder> {

    public ArrayList<Dish> dishes;
    public Context context;

    public interface OnItemClickListener {
        void onItemCLicked(int position);
    }

    public OnItemClickListener mOnItemClickListener;

    public MasterListAdapter(ArrayList<Dish> dishes, Context context, OnItemClickListener onItemClickListener) {
        this.dishes = dishes;
        this.context = context;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.master_list_item, parent, false);

        Log.d("MasterListAdapter", "OnCreateViewHolder Running");

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(dishes.get(position).getName());

        AsyncTask asyncTask = new AsyncTask() {
            Bitmap bitmap;

            @Override
            protected Object doInBackground(Object[] objects) {
                String imageURL = dishes.get(position).getImageURL();
                Log.d("Image URL", imageURL);

                if (imageURL != null && !imageURL.equals("")) {
                    try {
                        bitmap = Picasso.with(context).load(imageURL).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);

                if (bitmap != null) {
                    holder.imageView.setVisibility(View.VISIBLE);
                    holder.imageView.setImageBitmap(bitmap);
                } else {
                    holder.imageView.setVisibility(View.GONE);
                }
            }
        };

        asyncTask.execute();
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textView;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.image_view);
            textView = (TextView) itemView.findViewById(R.id.dish_name_text_view);

            imageView.setVisibility(View.GONE);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnItemClickListener.onItemCLicked(getAdapterPosition());
        }
    }
}
