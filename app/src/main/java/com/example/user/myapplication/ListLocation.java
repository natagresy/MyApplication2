package com.example.user.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myapplication.ZOMATO.Example;
import com.example.user.myapplication.ZOMATO.Restaurant;
import com.example.user.myapplication.ZOMATO.Restaurant_;
import com.google.android.gms.location.LocationRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by bukalapak on 5/25/17.
 */

public class ListLocation extends AppCompatActivity implements LocationListener {
    private static final String TAG = ListLocation.class.getSimpleName();
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    String lat;
    String provider;
    //protected String latitude,longitude;
    protected boolean gps_enabled, network_enabled;
    double latitude;
    double longitude;
    Location mLastLocation;
    LocationRequest mLocationRequest;


    // TODO - insert your themoviedb.org API KEY here
    //public final static String API_KEY = "AIzaSyDiC5xJIZObEXA6T8eiM6MBBoELDVVGZSU";
    public final static String API_KEY = "96ad755290420b10169661fd24185541";
    @Override
    public void onLocationChanged(Location location) {
        Log.d("ccc", "onLocationChanged");
        mLastLocation = location;
        latitude = location.getLatitude();
        longitude = location.getLongitude();


        Log.d("aaa", String.valueOf(latitude));
        Log.d("aaa", String.valueOf(longitude));
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RetrofitMaps apiService =
                ApiClient.getClient().create(RetrofitMaps.class);



        //Call<Example> call = apiService.getNearbyPlaces("restaurant", -6.174465 + "," + 106.8227433, 3000, API_KEY);
        //Call<Example> call = apiService.getNearbyPlaces("restaurant", location.getLatitude() + "," + location.getLongitude(), 3000, API_KEY);
        Call<Example> call = apiService.getNearbyPlacesViaZomato("", 1, 100, location.getLatitude(), location.getLongitude(), 3000, API_KEY);
        //Call<MoviesResponse> call = apiService.getTopRatedMovies(API_KEY, "1");
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                int statusCode = response.code();
                List<Restaurant> location = response.body().getRestaurants();
                recyclerView.setAdapter(new LocationAdapter(location, R.layout.list_item_movie, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("ccc", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1000, this);

//        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        RetrofitMaps apiService =
//                ApiClient.getClient().create(RetrofitMaps.class);
//
//
//        //Call<Example> call = apiService.getNearbyPlaces("restaurant", -6.174465 + "," + 106.8227433, 3000, API_KEY);
//        Call<Example> call = apiService.getNearbyPlaces("restaurant", latitude + "," + longitude, 3000, API_KEY);
//        //Call<MoviesResponse> call = apiService.getTopRatedMovies(API_KEY, "1");
//        call.enqueue(new Callback<Example>() {
//            @Override
//            public void onResponse(Call<Example> call, Response<Example> response) {
//                  int statusCode = response.code();
//                  List<Result> location = response.body().getResults();
//                  recyclerView.setAdapter(new LocationAdapter(location, R.layout.list_item_movie, getApplicationContext()));
//            }
//
//            @Override
//            public void onFailure(Call<Example> call, Throwable t) {
//                // Log error here since request failed
//                Log.e(TAG, t.toString());
//            }
//        });
    }


    public void startMaps(View view)
    {
        startActivity(new Intent(this, MapsActivity.class));
    }



    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
