package com.example.user.myapplication;

import com.example.user.myapplication.POJO.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Nathasa Gresy on 6/3/17.
 */
public interface RetrofitMaps {

    /*
     * Retrofit get annotation with our URL
     * And our method that will return us details of student.
     */
    @GET("api/place/nearbysearch/json?sensor=true")
    Call<Example> getNearbyPlaces(@Query("type") String type, @Query("location") String location, @Query("radius") int radius, @Query("key") String key);

    @GET("api/v2.1/search")
    Call<com.example.user.myapplication.ZOMATO.Example> getNearbyPlacesViaZomato(@Query("q") String q,
                                           @Query("start") int start,
                                           @Query("count") int count,
                                           @Query("lat") double lat,
                                           @Query("lon") double lon,
                                           @Query("radius") double radius,
                                           @Query("cuisines") String cuisines,
                                           @Query("apikey") String apikey);

}
