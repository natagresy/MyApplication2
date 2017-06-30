package com.example.user.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.user.myapplication.POJO.Example;
import com.example.user.myapplication.POJO.Result;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Nathasa Gresy on 5/25/17.
 */

public class ListLocation extends AppCompatActivity implements LocationListener {
    private static final String TAG = ListLocation.class.getSimpleName();
    MainActivity mainActivity = new MainActivity();
    protected LocationManager locationManager;
    double latitude;
    double longitude;
    String typeCodeResponse ="";
    int count =1;
    String temp;
    Location mLastLocation;
    private ProgressBar spinner;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;


    public final static String API_KEY = "AIzaSyDiC5xJIZObEXA6T8eiM6MBBoELDVVGZSU";
    //public final static String API_KEY = "96ad755290420b10169661fd24185541";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("ccc", "onCreate");
        Log.d("checkList", String.valueOf(mainActivity.type_code.size()));
        Log.d("checkList", String.valueOf(mainActivity.type_code));
        for(String data : mainActivity.keyword) {
            //Log.d("datanya", data.toString());
            //typeCodeResponse = data.toString();
            typeCodeResponse = typeCodeResponse + data+"%20";
        }
        Log.d("typeCodeResponse", typeCodeResponse);

        mainActivity.type_code.size();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY first!", Toast.LENGTH_LONG).show();
            return;
        }
        try{
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1000, this);
        }
        catch (Exception e){
            Log.d("catch", "test");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkLocationPermission();
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1000, this);
            }
        }


    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("ccc", "onLocationChanged");
        mLastLocation = location;
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Log.d("myloc", String.valueOf(latitude));
        Log.d("myloc", String.valueOf(longitude));
        RetrofitMaps apiService = ApiClient.getClient().create(RetrofitMaps.class);
        //Call<Example> call = apiService.getNearbyPlacesViaZomato("", count, 100, location.getLatitude(), location.getLongitude(),300000, typeCodeResponse, API_KEY);
        Call<Example> call = apiService.getNearbyPlaces("restaurant", typeCodeResponse, location.getLatitude() + "," + location.getLongitude(), 300000, API_KEY);


        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

     //   recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                int statusCode = response.code();

                final List<Result> location = response.body().getResults();

                //loading spinner
                spinner=(ProgressBar)findViewById(R.id.progressBar);
                spinner.setVisibility(View.GONE);

                recyclerView.setHasFixedSize(true);
                recyclerView.setNestedScrollingEnabled(false);
                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(new LocationAdapter(location, R.layout.row_layout, getApplicationContext()));
                recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {

                    @Override
                    public void onClick(View view, int position) {
                        Log.d("catch", "test");
                        Result result = location.get(position);
                        // Toast.makeText(getApplicationContext(), resaurant.getRestaurant().getName() + " is selected!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ListLocation.this,PlaceDetail.class);
//                        i.putExtra("detil_nama", resaurant.getRestaurant().getName());
//                        i.putExtra("detil_rating", resaurant.getRestaurant().getUserRating().getAggregateRating());
//                        i.putExtra("detil_cuisine", resaurant.getRestaurant().getCuisines());
//                        i.putExtra("detil_alamat", resaurant.getRestaurant().getLocation().getAddress());
//                        i.putExtra("detil_foto", resaurant.getRestaurant().getFeaturedImage());
                        view.getContext().startActivity(i);
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));

                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
                {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy)
                    {
                        if(dy > 0) //check for scroll down
                        {
                            visibleItemCount = mLayoutManager.getChildCount();
                            totalItemCount = mLayoutManager.getItemCount();
                            pastVisiblesItems = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                            Log.d("paging", "visibleItemCount"+String.valueOf(visibleItemCount));
                            Log.d("paging", "totalItemCount"+String.valueOf(totalItemCount));
                            Log.d("paging", "positionView"+String.valueOf(pastVisiblesItems));
                            if (loading)
                            {
                                if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                                {
                                    loading = false;
                                    Log.d("paging", "Last Item Wow !");
                                    count = count + 20;
                                    //Do pagination.. i.e. fetch new data
                                }
                            }
                        }
                    }
                });
            }




            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

    }




    /*public void startMaps(View view)
    {
        startActivity(new Intent(this, MapsActivity.class));
    }
*/


    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
}
