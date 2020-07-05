package com.example.polymovie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapter.MovieAdapter;
import api.API;
import api.ApiKey;
import api.RetrofitClient;
import model.Movie;
import model.MovieRespone;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllMovieScreen extends AppCompatActivity {
    List<Movie> movieList = new ArrayList<>();
    MovieAdapter movieAdapter;
    RecyclerView rvMovie;
    RetrofitClient retrofit = new RetrofitClient();
    API api = retrofit.getClient().create(API.class);
    int page = 1;
    int total_pages;
    TextView tv_total_pages;
    EditText edt_page;
    ImageView img_search, img_back_1, img_back_2, img_next_1, img_next_2, img_home;
    ProgressBar progressBar;
    Intent intent;
    String id;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_movie_screen);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(ViewAllMovieScreen.this, R.color.white));// set status background white

        //anh xa
        rvMovie = findViewById(R.id.rvMovie);
        edt_page = findViewById(R.id.edt_page);
        edt_page.setText(String.valueOf(page));
        tv_total_pages = findViewById(R.id.tv_total_pages);
        img_search = findViewById(R.id.img_search);
        img_back_1 = findViewById(R.id.img_back_1);
        img_back_2 = findViewById(R.id.img_back_2);
        img_next_1 = findViewById(R.id.img_next_1);
        img_next_2 = findViewById(R.id.img_next_2);
        img_home = findViewById(R.id.img_home);
        progressBar = findViewById(R.id.spin_kit);

        intent = getIntent();
        id = intent.getStringExtra("id");

        checkPage(Integer.parseInt(edt_page.getText().toString()));

        getData(ApiKey.THEMOVIE_APP_KEY, page, id);

        //su kien
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieList = new ArrayList<>();
                movieAdapter = new MovieAdapter(ViewAllMovieScreen.this, movieList);
                rvMovie.setAdapter(movieAdapter);
                progressBar.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData(ApiKey.THEMOVIE_APP_KEY, Integer.parseInt(edt_page.getText().toString()), id);
                        page = Integer.parseInt(edt_page.getText().toString());
                        //clear focus
                        edt_page.clearFocus();
                        //hide keyboard
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(edt_page.getWindowToken(), 0);
                        checkPage(Integer.parseInt(edt_page.getText().toString()));

                    }
                }, 500);
            }
        });

        //back
        img_back_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieList = new ArrayList<>();
                movieAdapter = new MovieAdapter(ViewAllMovieScreen.this, movieList);
                rvMovie.setAdapter(movieAdapter);
                progressBar.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData(ApiKey.THEMOVIE_APP_KEY, --page, id);
                        edt_page.setText(String.valueOf(page));
                        checkPage(Integer.parseInt(edt_page.getText().toString()));
                    }
                }, 500);

            }
        });

        //next
        img_next_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieList = new ArrayList<>();
                movieAdapter = new MovieAdapter(ViewAllMovieScreen.this, movieList);
                rvMovie.setAdapter(movieAdapter);
                progressBar.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData(ApiKey.THEMOVIE_APP_KEY, ++page, id);
                        edt_page.setText(String.valueOf(page));
                        checkPage(Integer.parseInt(edt_page.getText().toString()));
                    }
                }, 500);
            }
        });

        //home
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAllMovieScreen.this, MainActivity.class);
                intent.putExtra("key", "MOVIES");
                startActivity(intent);
            }
        });

    }

    void getData(String apiKey, int page, String id) {
        if (id.equals("popular")) {
            api.getPopularMovie(apiKey, page).enqueue(new Callback<MovieRespone>() {
                @Override
                public void onResponse(Call<MovieRespone> call, Response<MovieRespone> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        movieList = response.body().getResults();
                        progressBar.setVisibility(View.INVISIBLE);
                        total_pages = response.body().getTotal_pages();
                        tv_total_pages.setText("/  " + total_pages);
                        setRecyclerView();
                    }
                }

                @Override
                public void onFailure(Call<MovieRespone> call, Throwable t) {
                }
            });
        } else if (id.equals("nowplaying")) {
            api.getNowPlayingMovie(apiKey, page).enqueue(new Callback<MovieRespone>() {
                @Override
                public void onResponse(Call<MovieRespone> call, Response<MovieRespone> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        movieList = response.body().getResults();
                        progressBar.setVisibility(View.INVISIBLE);
                        total_pages = response.body().getTotal_pages();
                        tv_total_pages.setText("/  " + total_pages);
                        setRecyclerView();
                    }
                }

                @Override
                public void onFailure(Call<MovieRespone> call, Throwable t) {
                }
            });

        } else if (id.equals("upcoming")) {
            api.getComingMovie(apiKey, page).enqueue(new Callback<MovieRespone>() {
                @Override
                public void onResponse(Call<MovieRespone> call, Response<MovieRespone> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        movieList = response.body().getResults();
                        progressBar.setVisibility(View.INVISIBLE);
                        total_pages = response.body().getTotal_pages();
                        tv_total_pages.setText("/  " + total_pages);
                        setRecyclerView();
                    }
                }

                @Override
                public void onFailure(Call<MovieRespone> call, Throwable t) {
                }
            });
        } else {
            api.getTopMovie(apiKey, page).enqueue(new Callback<MovieRespone>() {
                @Override
                public void onResponse(Call<MovieRespone> call, Response<MovieRespone> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        movieList = response.body().getResults();
                        progressBar.setVisibility(View.INVISIBLE);
                        total_pages = response.body().getTotal_pages();
                        tv_total_pages.setText("/  " + total_pages);
                        setRecyclerView();
                    }
                }

                @Override
                public void onFailure(Call<MovieRespone> call, Throwable t) {
                }
            });

        }

    }

    public void checkPage(int page) {
        if (page == 1) {
            img_back_1.setVisibility(View.VISIBLE);
            img_back_2.setVisibility(View.INVISIBLE);
            img_next_1.setVisibility(View.INVISIBLE);
            img_next_2.setVisibility(View.VISIBLE);
            return;
        }

        if (page == 500) {
            img_back_1.setVisibility(View.INVISIBLE);
            img_back_2.setVisibility(View.VISIBLE);
            img_next_1.setVisibility(View.VISIBLE);
            img_next_2.setVisibility(View.INVISIBLE);
            return;
        }

        if (page > 1 && page < 500) {
            img_back_1.setVisibility(View.INVISIBLE);
            img_back_2.setVisibility(View.VISIBLE);
            img_next_1.setVisibility(View.INVISIBLE);
            img_next_2.setVisibility(View.VISIBLE);
        }
    }

    private void setRecyclerView() {
        //đổ dữ liệu lên recyclerview
        movieAdapter = new MovieAdapter(ViewAllMovieScreen.this, movieList);
        //layout grid
        GridLayoutManager manager = new GridLayoutManager(ViewAllMovieScreen.this, 3);
        rvMovie.setLayoutManager(manager);
        rvMovie.setAdapter(movieAdapter);
    }
}
