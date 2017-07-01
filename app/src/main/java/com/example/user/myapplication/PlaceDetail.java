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
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("ccc", "onCreatePlaceDetail");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_detail);

        final TextView detil_nama = (TextView)findViewById(R.id.title_detail);
        TextView detil_rating = (TextView)findViewById(R.id.rating_detail);
        final TextView detil_cuisine = (TextView)findViewById(R.id.cuisine_detail);
        final TextView detail_open = (TextView)findViewById(R.id.open_hour);
        final TextView detil_alamat = (TextView)findViewById(R.id.detail_address);
        final ImageView detil_foto = (ImageView)findViewById(R.id.foto_detail);
        id = getIntent().getExtras().getString("id");
        RetrofitMaps apiService = ApiClient.getClient().create(RetrofitMaps.class);
        Call<Example> call = apiService.getDetailPlaces(id, API_KEY);
        findViewById(R.id.btn_route).setOnClickListener(this);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                String name = response.body().getResult().getName();
                String alamat = response.body().getResult().getFormattedAddress();
                String telepon = response.body().getResult().getFormattedPhoneNumber();
                String refrence = response.body().getResult().getPhotos().get(0).getPhotoReference();
                String opening_hours="";
                lat = response.body().getResult().getGeometry().getLocation().getLat();
                lon = response.body().getResult().getGeometry().getLocation().getLng();

                int size = response.body().getResult().getOpeningHours().getWeekdayText().size();

                for(int i =0;i<size;i++){
                    opening_hours = opening_hours + response.body().getResult().getOpeningHours().getWeekdayText().get(i) +"\n";
                }
                detil_nama.setText(name);
                detil_alamat.setText(alamat);
                detil_cuisine.setText(telepon);
                detail_open.setText(opening_hours);

                String image_url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=1600&photoreference="+refrence+"&key=AIzaSyAUF3gvrbu8V0_-RPPe44Xl_2Gwyw6bVlw";
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
//        String nama = getIntent().getExtras().getString("detil_nama");
//        String rating = getIntent().getExtras().getString("detil_rating");
//        String cuisine = getIntent().getExtras().getString("detil_cuisine");
//        String alamat = getIntent().getExtras().getString("detil_alamat");
//        String artUrl = getIntent().getStringExtra("detil_foto");

     //   imageLoader.displayImage(getIntent().getStringExtra("detil_foto"));

//        detil_nama.setText(nama);
//        detil_rating.setText(rating);
//        detil_cuisine.setText(cuisine);
//        detil_alamat.setText(alamat);
      // detil_foto.setImageBitmap(artUrl);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_route:
                Intent i = new Intent(PlaceDetail.this, MapsActivity.class);
                i.putExtra("lat", lat);
                i.putExtra("lon", lon);
                i.putExtra("id1", id);
                startActivity(i);
                break;
        }
    }
}
