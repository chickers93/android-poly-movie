package com.example.polymovie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapter.MovieAdapter;
import adapter.PeopleAdapter;
import adapter.TVAdapter;
import api.API;
import api.ApiKey;
import api.RetrofitClient;
import model.Movie;
import model.MovieRespone;
import model.People;
import model.PeopleRespone;
import model.TV;
import model.TVRespone;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchScreen extends AppCompatActivity {
    SearchView searchView;
    RecyclerView rvSearch;
    Intent intent;
    String key, query;
    RetrofitClient retrofit = new RetrofitClient();
    API api = retrofit.getClient().create(API.class);
    List<Movie> movieList = new ArrayList<>();
    List<TV> tvList = new ArrayList<>();
    List<People> peopleList = new ArrayList<>();
    MovieAdapter movieAdapter;
    TVAdapter tvAdapter;
    PeopleAdapter peopleAdapter;
    ImageView imgback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
        transparentStatusAndNavigation();

        intent = getIntent();
        key = intent.getStringExtra("key");
        query = intent.getStringExtra("query");

        //anh xa
        searchView = findViewById(R.id.searchView);
        rvSearch = findViewById(R.id.rvSearch);
        imgback = findViewById(R.id.imgback);

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //search
        searchView.setIconified(false);
        searchView.setIconifiedByDefault(false);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getData(ApiKey.THEMOVIE_APP_KEY, query, key);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() == 0) {
                    //hide keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                }
                return false;
            }
        });

        getData(ApiKey.THEMOVIE_APP_KEY, query, key);

    }

    public void getData(String apiKey, String query, String key) {
        if (key.equals("movie")) {
            api.getSearchMovie(apiKey, query).enqueue(new Callback<MovieRespone>() {
                @Override
                public void onResponse(Call<MovieRespone> call, Response<MovieRespone> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        movieList = response.body().getResults();
                        //đổ dữ liệu lên recyclerview
                        movieAdapter = new MovieAdapter(SearchScreen.this, movieList);
                        //layout grid
                        GridLayoutManager manager = new GridLayoutManager(SearchScreen.this, 3);
                        rvSearch.setLayoutManager(manager);
                        rvSearch.setAdapter(movieAdapter);
                    }

                }

                @Override
                public void onFailure(Call<MovieRespone> call, Throwable t) {

                }
            });
        } else if (key.equals("tv")) {
            api.getSearchTV(apiKey, query).enqueue(new Callback<TVRespone>() {
                @Override
                public void onResponse(Call<TVRespone> call, Response<TVRespone> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        tvList = response.body().getResults();
                        //đổ dữ liệu lên recyclerview
                        tvAdapter = new TVAdapter(SearchScreen.this, tvList);
                        //layout grid
                        GridLayoutManager manager = new GridLayoutManager(SearchScreen.this, 1);
                        rvSearch.setLayoutManager(manager);
                        rvSearch.setAdapter(tvAdapter);
                    }

                }

                @Override
                public void onFailure(Call<TVRespone> call, Throwable t) {

                }
            });
        } else {
            api.getSearchPerson(apiKey, query).enqueue(new Callback<PeopleRespone>() {
                @Override
                public void onResponse(Call<PeopleRespone> call, Response<PeopleRespone> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        peopleList = response.body().getResults();
                        //đổ dữ liệu lên recyclerview
                        peopleAdapter = new PeopleAdapter(SearchScreen.this, peopleList);
                        //layout grid
                        GridLayoutManager manager = new GridLayoutManager(SearchScreen.this, 2);
                        rvSearch.setLayoutManager(manager);
                        rvSearch.setAdapter(peopleAdapter);
                    }

                }

                @Override
                public void onFailure(Call<PeopleRespone> call, Throwable t) {

                }
            });
        }

    }

    private void transparentStatusAndNavigation() {
        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            );
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    private void setWindowFlag(final int bits, boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
