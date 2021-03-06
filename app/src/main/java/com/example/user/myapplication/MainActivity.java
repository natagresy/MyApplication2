package com.example.user.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.os.Handler;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity implements LocationListener, View.OnClickListener {
    private static ViewPager mPager;
    public static ArrayList<Integer> type_code = new ArrayList<Integer>();
    public static String keyword;

    protected LocationManager locationManager;
    public static double latitude;
    public static double longitude;
    Location mLastLocation;

    private static int currentPage = 0;
    private static final Integer[] SLIDER= {R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3,
            R.drawable.banner4,
            R.drawable.banner5};
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
                try{
                    mPager.setCurrentItem(currentPage++, true);
                }
                catch (Exception e){
                    Log.d("donothing","donothing");
                }

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
            "Martabak",
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
            R.drawable.martabak,
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
        getGPSInfo();
        findViewById(R.id.gotosearch).setOnClickListener(this);
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

                if(position==0){
                   keyword="martabak";
                }
                else if(position==1){
                    keyword="bakso|bakmi|mi ayam|mie ayam";
                }
                else if(position==2){
                    keyword="chinese";
                }
                else if(position==3){
                    keyword="dessert|roti|ice";
                }
                else if(position==4){
                    keyword="fastfood|burger|pizza|kfc|cfc|mcd";
                }
                else if(position==5){
                    keyword="nasi|sunda|padang|jawa";
                }
                else if(position==6){
                    keyword="beverages|minuman|juice|tea|";
                }
                else if(position==7){
                    keyword="seafood";
                }
                else if(position==8){
                    keyword="steak";
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

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.gotosearch:
                keyword="10101010101";
                Intent i = new Intent(MainActivity.this, ListLocation.class);
                startActivity(i);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to quit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void getGPSInfo() {
        Criteria criteria = new Criteria();
        String provider;
        Location location;
        LocationManager locationmanager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);

        if (locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            provider = locationmanager.getBestProvider(criteria, false);
            location = locationmanager.getLastKnownLocation(provider);

        } else {
            showGPSDisabledAlertToUser();
        }
    }


    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder
                .setTitle("Location required")
                .setMessage(
                        "GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Go to Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);

                            }
                        });

        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
