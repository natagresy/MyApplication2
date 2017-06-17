package com.example.user.myapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.myapplication.ZOMATO.Restaurant;

import com.example.user.myapplication.ZOMATO.Photo;
import com.example.user.myapplication.ZOMATO.Restaurant_;
import com.example.user.myapplication.ZOMATO.UserRating;


import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Nathasa Gresy on 5/25/17.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MovieViewHolder>{
    private List<Restaurant> location;
    private int rowLayout;
    private Context context;



    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView item_image;
        TextView namaResto;
        TextView address;
        TextView cuisine;
        TextView rating;


        public MovieViewHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.cardView);
            item_image = (ImageView) v.findViewById(R.id.item_image);
            namaResto = (TextView) v.findViewById(R.id.title);
            address = (TextView) v.findViewById(R.id.address);
            cuisine = (TextView) v.findViewById(R.id.description);
            rating = (TextView) v.findViewById(R.id.rating);
        }
    }

    public LocationAdapter(List<Restaurant> movies, int rowLayout, Context context) {
        this.location = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        holder.namaResto.setText(location.get(position).getRestaurant().getName());
        holder.cuisine.setText(location.get(position).getRestaurant().getCuisines());
        holder.address.setText(location.get(position).getRestaurant().getLocation().getAddress());
        holder.rating.setText(location.get(position).getRestaurant().getUserRating().getAggregateRating());
       // holder.item_image.setImageDrawable(Drawable.createFromPath(location.get(position).getRestaurant().getFeaturedImage()));
        Picasso.with(context).load(location.get(position).getRestaurant().getFeaturedImage()).resize(60,60).into(holder.item_image);
    }


    @Override
    public int getItemCount() {
        return location.size();
    }
}
