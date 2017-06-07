package com.example.user.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
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
        CustomGrid adapter = new CustomGrid(MainActivity.this, web, imageId);
        grid = (GridView) findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();

            }
        });

    }
}
