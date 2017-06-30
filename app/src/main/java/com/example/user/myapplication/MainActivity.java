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
    public static ArrayList<String> keyword = new ArrayList<String >();
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
}
