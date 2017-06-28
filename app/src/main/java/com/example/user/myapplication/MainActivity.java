package com.example.user.myapplication;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {
    private static ViewPager mPager;
    public static ArrayList<Integer> type_code = new ArrayList<Integer>();
    public String query;

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
                    type_code.clear();
                    type_code.add(5);
                    Log.d("tesList", type_code.toString());
                }
                else if(position==1){
                    type_code.clear();
                    type_code.add(261);
                    type_code.add(249);
                }
                else if(position==2){
                    type_code.clear();
                    type_code.add(25);
                }
                else if(position==3){
                    type_code.clear();
                    type_code.add(100);
                    type_code.add(233);
                }
                else if(position==4){
                    type_code.clear();
                    type_code.add(168);
                    type_code.add(193);
                    type_code.add(40);
                    type_code.add(177);
                }
                else if(position==5){
                    type_code.clear();
                    type_code.add(114);
                    type_code.add(237);
                    type_code.add(239);
                    type_code.add(240);
                    type_code.add(235);
                    type_code.add(260);
                    type_code.add(234);
                }
                else if(position==6){
                    type_code.clear();
                    type_code.add(161);
                    type_code.add(270);
                    type_code.add(247);
                    type_code.add(268);
                    type_code.add(164);
                }
                else if(position==7){
                    type_code.clear();
                    type_code.add(83);
                }
                else if(position==8){
                    type_code.clear();
                    type_code.add(141);
                }
                Intent i = new Intent(MainActivity.this,ListLocation.class);
                MainActivity.this.startActivity(i);

                }

        });

    }
}
