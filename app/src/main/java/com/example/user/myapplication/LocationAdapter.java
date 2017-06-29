package com.example.user.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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


import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;


import java.util.List;

import static com.example.user.myapplication.R.id.item_image;

/**
 * Created by Nathasa Gresy on 5/25/17.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MovieViewHolder>{
    private List<Restaurant> location;
    private int rowLayout;
    private Context context;
    ImageLoader imageLoader;



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

    public LocationAdapter(List<Restaurant> location, int rowLayout, Context context) {
        this.location = location;
        this.rowLayout = rowLayout;
        this.context = context;

        //Getting instance of Universal Image Loader
        imageLoader = ((App)context.getApplicationContext()).getImageLoader();
    }

    @Override
    public LocationAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {
        holder.namaResto.setText(location.get(position).getRestaurant().getName());
        holder.cuisine.setText(location.get(position).getRestaurant().getCuisines());
        holder.address.setText(location.get(position).getRestaurant().getLocation().getAddress());
        holder.rating.setText(location.get(position).getRestaurant().getUserRating().getAggregateRating());
        imageLoader.displayImage(location.get(position).getRestaurant().getFeaturedImage(),holder.item_image);
        try{
            imageLoader.displayImage(location.get(position).getRestaurant().getFeaturedImage(),holder.item_image);
        }
        catch (Exception e){
            Log.d("catch", "test");
                imageLoader.displayImage(location.get(position).getRestaurant().getFeaturedImage(),holder.item_image);
            }

        holder.item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("catch", "test1");
                Intent intent = new Intent(context, PlaceDetail.class);
                intent.putExtra("detil_foto", location.get(position).getRestaurant().getFeaturedImage());
                context.startActivity(intent);
            }
        });

        }


    @Override
    public int getItemCount() {
        return location.size();
    }
}
