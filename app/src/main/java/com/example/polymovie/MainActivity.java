package com.example.polymovie;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import Fragment.MovieFragment;
import Fragment.PeopleFragment;
import Fragment.TVFragment;

public class MainActivity extends AppCompatActivity {
    final Handler handler = new Handler();
    FrameLayout container;
    ImageView btn_movie1, btn_movie2, btn_tv1, btn_tv2, btn_people1, btn_people2;
    Intent intent;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (hasNavBar(this)) {
            LinearLayout main = findViewById(R.id.main);
            main.setPadding(0, 0, 0, 135);
        }

        //anh xa
        container = findViewById(R.id.container);
        btn_movie1 = findViewById(R.id.btn_movie1);
        btn_movie2 = findViewById(R.id.btn_movie2);
        btn_tv1 = findViewById(R.id.btn_tv1);
        btn_tv2 = findViewById(R.id.btn_tv2);
        btn_people1 = findViewById(R.id.btn_people1);
        btn_people2 = findViewById(R.id.btn_people2);

        btn_movie1.setVisibility(View.INVISIBLE);
        btn_movie2.setVisibility(View.VISIBLE);
        btn_tv1.setVisibility(View.VISIBLE);
        btn_tv2.setVisibility(View.INVISIBLE);
        btn_people1.setVisibility(View.VISIBLE);
        btn_people2.setVisibility(View.INVISIBLE);

        //animation
        final Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.down_to_up);

        //default fragment
        intent = getIntent();
        key = intent.getStringExtra("key");
        if (key == null || key.equals("MOVIES")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new MovieFragment()).commit();
            btn_movie2.startAnimation(animation);
        } else if (key.equals("TV")) {
            btn_movie1.setVisibility(View.VISIBLE);
            btn_movie2.setVisibility(View.INVISIBLE);
            btn_tv1.setVisibility(View.INVISIBLE);
            btn_tv2.setVisibility(View.VISIBLE);
            btn_people1.setVisibility(View.VISIBLE);
            btn_people2.setVisibility(View.INVISIBLE);
            btn_tv2.startAnimation(animation);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new TVFragment()).commit();
        } else {
            btn_movie1.setVisibility(View.VISIBLE);
            btn_movie2.setVisibility(View.INVISIBLE);
            btn_tv1.setVisibility(View.VISIBLE);
            btn_tv2.setVisibility(View.INVISIBLE);
            btn_people1.setVisibility(View.INVISIBLE);
            btn_people2.setVisibility(View.VISIBLE);
            btn_people2.startAnimation(animation);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new PeopleFragment()).commit();
        }

        //su kien
        btn_movie1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new MovieFragment()).commit();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn_movie1.setVisibility(View.INVISIBLE);
                    }
                }, 250);
                btn_movie2.setVisibility(View.VISIBLE);
                btn_movie2.startAnimation(animation);
                btn_tv1.setVisibility(View.VISIBLE);
                btn_tv2.setVisibility(View.INVISIBLE);
                btn_people1.setVisibility(View.VISIBLE);
                btn_people2.setVisibility(View.INVISIBLE);

            }
        });

        btn_tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new TVFragment()).commit();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn_tv1.setVisibility(View.INVISIBLE);
                    }
                }, 250);
                btn_tv2.setVisibility(View.VISIBLE);
                btn_tv2.startAnimation(animation);
                btn_movie1.setVisibility(View.VISIBLE);
                btn_movie2.setVisibility(View.INVISIBLE);
                btn_people1.setVisibility(View.VISIBLE);
                btn_people2.setVisibility(View.INVISIBLE);

            }
        });

        btn_people1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new PeopleFragment()).commit();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn_people1.setVisibility(View.INVISIBLE);
                    }
                }, 250);
                btn_people2.setVisibility(View.VISIBLE);
                btn_people2.startAnimation(animation);
                btn_movie1.setVisibility(View.VISIBLE);
                btn_movie2.setVisibility(View.INVISIBLE);
                btn_tv1.setVisibility(View.VISIBLE);
                btn_tv2.setVisibility(View.INVISIBLE);


            }
        });

    }

    //check navigation bar exsist?
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public boolean hasNavBar(Context context) {
        Point realSize = new Point();
        Point screenSize = new Point();
        boolean hasNavBar = false;
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        realSize.x = metrics.widthPixels;
        realSize.y = metrics.heightPixels;
        getWindowManager().getDefaultDisplay().getSize(screenSize);
        if (realSize.y != screenSize.y) {
            int difference = realSize.y - screenSize.y;
            int navBarHeight = 0;
            Resources resources = context.getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                navBarHeight = resources.getDimensionPixelSize(resourceId);
            }
            if (navBarHeight != 0) {
                if (difference == navBarHeight) {
                    hasNavBar = true;
                }
            }

        }
        return hasNavBar;

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (hasNavBar(this)) {
            LinearLayout main = findViewById(R.id.main);
            main.setPadding(0, 0, 0, 135);
        }
    }
}
