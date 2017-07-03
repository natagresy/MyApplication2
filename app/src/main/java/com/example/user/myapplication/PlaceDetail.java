package com.example.user.myapplication;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.myapplication.PLACEDETAIL.Example;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.user.myapplication.ListLocation.API_KEY;

/**
 * Created by User on 6/28/2017.
 */

public class PlaceDetail extends AppCompatActivity implements View.OnClickListener {
    private ImageLoader mImageLoader;
    ImageLoader imageLoader;
    double lat;
    double lon;
    String id, rating,opening_hours, name, alamat, telepon, refrence;
    //String rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("ccc", "onCreatePlaceDetail");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_detail);
        id = getIntent().getExtras().getString("id");
        findViewById(R.id.btn_route).setOnClickListener(this);

        final TextView detil_nama = (TextView)findViewById(R.id.title_detail);
        final TextView detil_rating = (TextView)findViewById(R.id.rating_detail);
        final TextView detil_cuisine = (TextView)findViewById(R.id.cuisine_detail);
        final TextView detail_open = (TextView)findViewById(R.id.open_hour);
        final TextView detil_alamat = (TextView)findViewById(R.id.detail_address);
        final ImageView detil_foto = (ImageView)findViewById(R.id.foto_detail);



        RetrofitMaps apiService = ApiClient.getClient().create(RetrofitMaps.class);
        Call<Example> call = apiService.getDetailPlaces(id, API_KEY);

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                name = response.body().getResult().getName();
                alamat = response.body().getResult().getFormattedAddress();
                lat = response.body().getResult().getGeometry().getLocation().getLat();
                lon = response.body().getResult().getGeometry().getLocation().getLng();
                try {
                    rating= response.body().getResult().getRating().toString();
                }
                catch (Exception e){
                    rating = "unrated";
                }
                try{
                    telepon = response.body().getResult().getFormattedPhoneNumber();
                }
                catch (Exception e){
                    telepon = "phone not available";
                }
                try{
                    refrence = response.body().getResult().getPhotos().get(0).getPhotoReference();
                }
                catch (Exception e){
                    refrence="";
                }

                opening_hours="";
                try{
                    int size = response.body().getResult().getOpeningHours().getWeekdayText().size();
                    for(int i =0;i<size;i++){
                        opening_hours = opening_hours + response.body().getResult().getOpeningHours().getWeekdayText().get(i) +"\n";
                    }
                }
                catch (Exception e){
                    opening_hours="tutup";
                }



                detil_nama.setText(name);
                detil_alamat.setText(alamat);
                detil_cuisine.setText(telepon);
                detail_open.setText(opening_hours);
                detil_rating.setText(rating);


                String image_url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=1600&photoreference="+refrence+"&key="+API_KEY;
                ImageView image = (ImageView) findViewById(R.id.foto_detail);
                // Get singletone instance of ImageLoader
                ImageLoader imageLoader = ImageLoader.getInstance();
                // Initialize ImageLoader with configuration. Do it once.
                //imageLoader.init(ImageLoaderConfiguration.createDefault());
                // Load and display image asynchronously

                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showStubImage(R.drawable.loadinganimasi) //this is the image that will be displayed if download fails
                        .cacheInMemory()
                        .cacheOnDisc()
                        .build();

                imageLoader.displayImage(image_url, image, options);



        }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
    });

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_route:
                Intent i = new Intent(PlaceDetail.this, MapsActivity.class);
                i.putExtra("lat", lat);
                i.putExtra("lon", lon);
                i.putExtra("id1", id);
                i.putExtra("name", name);
                startActivity(i);
                break;
        }
    }
}
