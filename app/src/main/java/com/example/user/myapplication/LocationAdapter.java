package com.example.user.myapplication;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.user.myapplication.POJO.Result;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.List;

/**
 * Created by Nathasa Gresy on 5/25/17.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MovieViewHolder>{
    private List<Result> location;
    private int rowLayout;
    private Context context;
    ImageLoader imageLoader;
    String refrence;



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

    public LocationAdapter(List<Result> location, int rowLayout, Context context) {
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
        holder.namaResto.setText(location.get(position).getName());
        try {
            holder.rating.setText(location.get(position).getRating().toString());
        }
        catch (Exception e){
            holder.rating.setText("unrated");
        }
        try{
            if(location.get(position).getOpeningHours().getOpenNow()==true){
                holder.cuisine.setText("BUKA");
            }
            else{
                holder.cuisine.setText("TUTUP");
            }
        }
        catch (Exception e){
            holder.cuisine.setText("");
        }

        try{
            refrence = location.get(position).getPhotos().get(0).getPhotoReference();
            imageLoader.displayImage("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+refrence+"&key=AIzaSyDiC5xJIZObEXA6T8eiM6MBBoELDVVGZSU", holder.item_image);
            Log.d("blabla", refrence);
        }
        catch(Exception e){
            refrence = "aaaa";
            imageLoader.displayImage("http://www.sitechecker.eu/img/not-available.png", holder.item_image);
            Log.d("blabla", refrence);
        }






        //for(int i = 0; i <location.size(); i++){
//            try{
//                refrence = location.get(position).getPhotos().get(position).getPhotoReference();
//                Log.d("abcdefghijk", refrence);
//                imageLoader.displayImage("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+location.get(position).getPhotos().get(position).getPhotoReference()+"&key=AIzaSyDiC5xJIZObEXA6T8eiM6MBBoELDVVGZSU", holder.item_image);
//            }
//            catch (Exception e){
//                refrence = "http://www.sitechecker.eu/img/not-available.png";
//                imageLoader.displayImage("http://www.sitechecker.eu/img/not-available.png", holder.item_image);
//            }
        //}






//        holder.cuisine.setText(location.get(position).getRestaurant().getCuisines());
//        holder.address.setText(location.get(position).getRestaurant().getLocation().getAddress());
//        holder.rating.setText(location.get(position).getRestaurant().getUserRating().getAggregateRating());
//        imageLoader.displayImage(location.get(position).getRestaurant().getFeaturedImage(),holder.item_image);
//        try{
//            imageLoader.displayImage(location.get(position).getRestaurant().getFeaturedImage(),holder.item_image);
//        }
//        catch (Exception e){
//            Log.d("catch", "test");
//                imageLoader.displayImage(location.get(position).getRestaurant().getFeaturedImage(),holder.item_image);
//            }
//
//        holder.item_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("catch", "test1");
//                Intent intent = new Intent(context, PlaceDetail.class);
//                intent.putExtra("detil_foto", location.get(position).getRestaurant().getFeaturedImage());
//                context.startActivity(intent);
//            }
//        });

        }


    @Override
    public int getItemCount() {
        return location.size();
    }
}
