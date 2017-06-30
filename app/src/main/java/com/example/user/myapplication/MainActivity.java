package com.example.user.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.example.user.myapplication.Utils.Utils;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private static ViewPager mPager;
    public static ArrayList<Integer> type_code = new ArrayList<Integer>();
    public static ArrayList<String> keyword = new ArrayList<String >();
    protected LocationManager locationManager;
    public static double latitude;
    public static double longitude;
    Location mLastLocation;

    private static int currentPage = 0;
    private static final Integer[] SLIDER= {R.drawable.bakery,
            R.drawable.bakmibaso,
            R.drawable.chinese,
            R.drawable.dessert,
            R.drawable.indonesian};
    private ArrayList<Integer> SLIDERArray = new ArrayList<Integer>();

    private void init() {
        for(int i=0;i<SLIDER.length;i++)
            SLIDERArray.add(SLIDER[i]);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlideAdapter(MainActivity.this,SLIDERArray));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == SLIDER.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);
    }



    //gridview buttons
    GridView grid;
    String[] web = {
            "Bakery",
            "Bakso dan Bakmi",
            "Chinese",
            "Dessert",
            "Fastfood",
            "Indonesian",
            "Minuman",
            "Seafood",
            "Steak"

    };

    int[] imageId = {
            R.drawable.bakery,
            R.drawable.bakmibaso,
            R.drawable.chinese,
            R.drawable.dessert,
            R.drawable.fastfood1,
            R.drawable.indonesian,
            R.drawable.minuman,
            R.drawable.seafood,
            R.drawable.steak

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
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
        init();
        CustomGrid adapter = new CustomGrid(MainActivity.this, web, imageId);
        grid = (GridView) findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setTextFilterEnabled(true);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
           //Toast.makeText(MainActivity.this, "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();
                if(position==0){
                    keyword.clear();
                    keyword.add("Bakery");
                }
                else if(position==1){
                    keyword.clear();
                    keyword.add("Bakso");
                }
                else if(position==2){
                    keyword.clear();
                    keyword.add("chinese");
                }
                else if(position==3){
                    keyword.clear();
                    keyword.add("ice");
                }
                else if(position==4){
                    keyword.clear();
                    keyword.add("burger");
                }
                else if(position==5){
                    keyword.clear();
                    keyword.add("martabak");
                }
                else if(position==6){
                    keyword.clear();
                    keyword.add("kopi");
                }
                else if(position==7){
                    keyword.clear();
                    keyword.add("seafood");
                }
                else if(position==8){
                    keyword.clear();
                    keyword.add("steak");
                }
                Intent i = new Intent(MainActivity.this,ListLocation.class);
                MainActivity.this.startActivity(i);

                }

        });

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Log.d("abcd", String.valueOf(latitude));
        Log.d("abcd", String.valueOf(longitude));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

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
