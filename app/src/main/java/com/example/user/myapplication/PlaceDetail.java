package com.example.user.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.user.myapplication.PLACEDETAIL.Example;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

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
    private Button dialBtn;
    private Button dialBtn1;
    double lat;
    double lon;
    String id;
    String rating;
    String opening_hours;
    String name;
    String alamat;
    String telepon;
    String refrence;

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
        dialBtn = (Button) findViewById(R.id.btn_call);



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
                    rating = "0.0";
                }
                try{
                    Log.d("telponini", "try");
                    telepon = response.body().getResult().getFormattedPhoneNumber();
                    if (telepon==null){
                        Log.d("telponini", "masukif");
                        telepon = "";
                        dialBtn1 = (Button) findViewById(R.id.btn_call);
                        dialBtn1.setEnabled(false);
                        //dialBtn1.setVisibility(View.INVISIBLE);
                    }
                }
                catch (Exception e){
                    Log.d("telponini", "catch");
                    telepon = "";
                    dialBtn1 = (Button) findViewById(R.id.btn_call);
                    dialBtn1.setEnabled(false);
                    //dialBtn1.setVisibility(View.INVISIBLE);
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
                        Log.d("lalala", opening_hours);
                    }
                    //if(opening_hours.contains("Monday")){
                    opening_hours = opening_hours.replaceAll("Monday:", " Senin"+"\t"+"\t"+":"+"\t");
                    opening_hours = opening_hours.replaceAll("Tuesday:", " Selasa"+"\t"+":"+"\t");
                    opening_hours = opening_hours.replaceAll("Wednesday:", " Rabu"+"\t"+"\t"+":"+"\t");
                    opening_hours = opening_hours.replaceAll("Thursday:", " Kamis"+"\t"+":"+"\t");
                    opening_hours = opening_hours.replaceAll("Friday:", " Jumat"+"\t"+":"+"\t");
                    opening_hours = opening_hours.replaceAll("Saturday:", " Sabtu"+"\t"+"\t"+":"+"\t");
                    opening_hours = opening_hours.replaceAll("Sunday:", " Minggu"+"\t"+":"+"\t");
                    opening_hours = opening_hours.replaceAll("Closed", "Tutup");


                    //}
                }
                catch (Exception e){
                    opening_hours="unknown detail";
                }

                detil_nama.setText(name);
                detil_alamat.setText(alamat);
                detil_cuisine.setText(telepon);
                detail_open.setText(opening_hours);
                detil_rating.setText(rating);

                float d= Float.parseFloat((rating));
                RatingBar rb = (RatingBar) findViewById(R.id.rating_bar);
                rb.setRating(d);


                String image_url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=1600&photoreference="+refrence+"&key="+API_KEY;
                ImageView image = (ImageView) findViewById(R.id.foto_detail);
                // Get singletone instance of ImageLoader
                ImageLoader imageLoader = ImageLoader.getInstance();
                // Initialize ImageLoader with configuration. Do it once.
                //imageLoader.init(ImageLoaderConfiguration.createDefault());
                // Load and display image asynchronously

                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showStubImage(R.drawable.loaddd) //this is the image that will be displayed if download fails
                        .cacheInMemory()
                        .cacheOnDisc()
                        .build();

                imageLoader.displayImage(image_url, image, options);

                dialBtn.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {
                        String toDial="tel:"+telepon;
                        startActivity(new Intent(Intent.ACTION_DIAL,Uri.parse(toDial)));
                    }
                });
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
