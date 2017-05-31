package com.example.user.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.user.myapplication.POJO.Example;
import com.example.user.myapplication.POJO.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by bukalapak on 5/25/17.
 */

public class ListLocation extends AppCompatActivity {
    private static final String TAG = ListLocation.class.getSimpleName();


    // TODO - insert your themoviedb.org API KEY here
    public final static String API_KEY = "AIzaSyDN7RJFmImYAca96elyZlE5s_fhX-MMuhk";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RetrofitMaps apiService =
                ApiClient.getClient().create(RetrofitMaps.class);

        Call<Example> call = apiService.getNearbyPlaces("restaurant", -6.174465 + "," + 106.8227433, 3000, API_KEY);
        //Call<MoviesResponse> call = apiService.getTopRatedMovies(API_KEY, "1");
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                  int statusCode = response.code();
                  List<Result> location = response.body().getResults();
                  recyclerView.setAdapter(new LocationAdapter(location, R.layout.list_item_movie, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
    public void startMaps(View view)
    {
        startActivity(new Intent(this, MapsActivity.class));
    }
}
