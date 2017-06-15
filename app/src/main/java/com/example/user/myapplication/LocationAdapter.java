package com.example.user.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.myapplication.ZOMATO.Restaurant;
import com.example.user.myapplication.ZOMATO.Restaurant_;
import com.example.user.myapplication.ZOMATO.UserRating;

import java.util.List;

/**
 * Created by Nathasa Gresy on 5/25/17.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MovieViewHolder>{
    private List<Restaurant> location;
    private int rowLayout;
    private Context context;



    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;


        public MovieViewHolder(View v) {
            super(v);
            moviesLayout = (LinearLayout) v.findViewById(R.id.movies_layout);
            movieTitle = (TextView) v.findViewById(R.id.title);
            data = (TextView) v.findViewById(R.id.subtitle);
            movieDescription = (TextView) v.findViewById(R.id.description);
            rating = (TextView) v.findViewById(R.id.rating);
        }
    }

    public LocationAdapter(List<Restaurant> movies, int rowLayout, Context context) {
        this.location = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        holder.movieTitle.setText(location.get(position).getRestaurant().getName());
        //holder.data.setText(movies.get(position).getReleaseDate());
        holder.movieDescription.setText(location.get(position).getRestaurant().getLocation().getAddress());
        holder.rating.setText(location.get(position).getRestaurant().getUserRating().getAggregateRating());
    }


    @Override
    public int getItemCount() {
        return location.size();
    }
}
