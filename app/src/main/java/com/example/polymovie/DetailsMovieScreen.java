package com.example.polymovie;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import adapter.CastAdapter;
import adapter.YoutubeRecyclerAdapter;
import api.API;
import api.ApiKey;
import api.RetrofitClient;
import butterknife.ButterKnife;
import model.Cast;
import model.CastRespone;
import model.Genres;
import model.Movie;
import model.Video;
import model.VideoKey;
import model.VideoRespone;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsMovieScreen extends AppCompatActivity {
    ImageView img_backdrop, img_poster;
    TextView tv_title, tv_overview, tv_cast, overview, tv_genres, tv_release_date, tv_runtime, tv_budget, tv_vote_average, trailer, number;
    Intent intent;
    String id, backdrop_path, poster_path;
    RetrofitClient retrofit = new RetrofitClient();
    API api = retrofit.getClient().create(API.class);
    Movie movie;
    List<Cast> castList = new ArrayList<>();
    List<VideoKey> videoList = new ArrayList<>();
    List<Video> trailerList = new ArrayList<>();
    RecyclerView rvCast, rvTrailer;
    CastAdapter castAdapter;
    LinearLayout btn_back;
    //String language = "vi";
    String language = "en";
    List<Genres> genresList = new ArrayList<>();
    String str = "";
    DecimalFormat formatter = new DecimalFormat("#,###,###");
    CircularProgressBar circularProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie_screen);
        ButterKnife.bind(this);

        transparentStatusAndNavigation();

        intent = getIntent();
        id = intent.getStringExtra("id");
        //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        backdrop_path = intent.getStringExtra("backdrop_path");
        poster_path = intent.getStringExtra("poster_path");

        //anh xa
        img_backdrop = findViewById(R.id.img_backdrop);
        img_poster = findViewById(R.id.img_poster);
        tv_title = findViewById(R.id.tv_title);
        tv_overview = findViewById(R.id.tv_overview);
        tv_genres = findViewById(R.id.tv_genres);
        tv_release_date = findViewById(R.id.tv_release_date);
        tv_runtime = findViewById(R.id.tv_runtime);
        tv_vote_average = findViewById(R.id.tv_vote_average);
        tv_budget = findViewById(R.id.tv_budget);
        tv_cast = findViewById(R.id.tv_cast);
        overview = findViewById(R.id.overview);
        trailer = findViewById(R.id.trailer);
        number = findViewById(R.id.number);
        rvCast = findViewById(R.id.rvCast);
        rvTrailer = findViewById(R.id.rv_trailer);
        circularProgressBar = findViewById(R.id.circularProgressBar);
        circularProgressBar.setProgressBarColorStart(getResources().getColor(R.color.orange));
        circularProgressBar.setProgressBarColorEnd(getResources().getColor(R.color.pink));
        circularProgressBar.setProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                supportFinishAfterTransition();

            }
        });

        //LinearGradient textview
        Shader myShader = new LinearGradient(
                0, 0, 0, 100,
                Color.parseColor("#DF7401"), Color.parseColor("#FF0040"),
                Shader.TileMode.CLAMP);
        tv_cast.getPaint().setShader(myShader);
        overview.getPaint().setShader(myShader);
        trailer.getPaint().setShader(myShader);

        Picasso.with(DetailsMovieScreen.this).load("https://image.tmdb.org/t/p/w500" + backdrop_path).into(img_backdrop);
        Picasso.with(DetailsMovieScreen.this).load("https://image.tmdb.org/t/p/w500" + poster_path).into(img_poster);

        try {
            getData(id, ApiKey.THEMOVIE_APP_KEY, language);
        } catch (Exception e) {

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

    public void getData(String id, String apiKey, String language) {
        api.getInforByMovie(id, apiKey, "videos", language).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful() && response.body() != null) {
                    movie = response.body();
                    tv_title.setText(movie.getTitle());
                    tv_overview.setText(movie.getOverview());
                    try {
                        tv_release_date.setText(movie.getRelease_date());
                        int hour = (int) movie.getRuntime() / 60;
                        int minute = (int) movie.getRuntime() % 60;
                        tv_runtime.setText(hour + "h " + minute + "m");
                        genresList = movie.getGenres();
                        for (Genres genres : genresList) {
                            str += genres.getName() + ", ";
                        }
                        tv_genres.setText(str.substring(0, str.length() - 2));
                        tv_budget.setText(formatter.format(movie.getBudget()) + "$");
                        tv_vote_average.setText((int) (movie.getVote_average() * 10) + "%");
                        //circularProgressBar.setProgress(movie.getVote_average() * 10);
                        circularProgressBar.setProgressWithAnimation(movie.getVote_average() * 10, (long) 1000); // =1s

                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.d("error", t.toString());
            }
        });

        //get credits
        api.getCredits(id, apiKey).enqueue(new Callback<CastRespone>() {
            @Override
            public void onResponse(Call<CastRespone> call, Response<CastRespone> response) {
                if (response.isSuccessful() && response.body() != null) {
                    castList = response.body().getCast();

                    try {
                        castAdapter = new CastAdapter(DetailsMovieScreen.this, castList);
                        rvCast.setLayoutManager(new LinearLayoutManager(DetailsMovieScreen.this, LinearLayoutManager.HORIZONTAL, false));
                        rvCast.setAdapter(castAdapter);
                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<CastRespone> call, Throwable t) {

            }
        });

        //get videos
        api.getVideos(id, apiKey).enqueue(new Callback<VideoRespone>() {
            @Override
            public void onResponse(Call<VideoRespone> call, Response<VideoRespone> response) {
                if (response.isSuccessful() && response.body() != null) {
                    videoList = response.body().getResults();
                    for (int i = 0; i < videoList.size(); i++) {
                        if (videoList.get(i).getType().equals("Trailer")) {
                            Video video = new Video();
                            video.setUrlVideo(videoList.get(i).getKey());
                            video.setThumnail("https://img.youtube.com/vi/" + videoList.get(i).getKey() + "/maxresdefault.jpg");
                            trailerList.add(video);
                        }
                    }

                    number.setText("(" + trailerList.size() + ")");

                    if (trailerList.size() > 0) {
                        YoutubeRecyclerAdapter adapter = new YoutubeRecyclerAdapter(trailerList);
                        rvTrailer.setLayoutManager(new LinearLayoutManager(DetailsMovieScreen.this, LinearLayoutManager.HORIZONTAL, false));
                        //rvTrailer.setLayoutManager(new LinearLayoutManager(DetailsMovieScreen.this));
                        rvTrailer.setItemAnimator(new DefaultItemAnimator());
                        rvTrailer.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<VideoRespone> call, Throwable t) {

            }
        });
    }

}
