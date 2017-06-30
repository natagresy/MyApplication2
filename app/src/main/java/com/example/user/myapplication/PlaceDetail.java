package com.example.user.myapplication;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * Created by User on 6/28/2017.
 */

public class PlaceDetail extends AppCompatActivity {
    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("ccc", "onCreatePlaceDetail");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_detail);

        TextView detil_nama = (TextView)findViewById(R.id.title_detail);
        TextView detil_rating = (TextView)findViewById(R.id.rating_detail);
        TextView detil_cuisine = (TextView)findViewById(R.id.cuisine_detail);
        TextView detil_alamat = (TextView)findViewById(R.id.detail_address);
        ImageView detil_foto = (ImageView)findViewById(R.id.foto_detail);

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
}
