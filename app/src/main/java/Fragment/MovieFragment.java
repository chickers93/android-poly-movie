package Fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polymovie.R;
import com.example.polymovie.SearchScreen;
import com.example.polymovie.ViewAllMovieScreen;

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

public class MovieFragment extends Fragment {
    RetrofitClient retrofit = new RetrofitClient();
    API api = retrofit.getClient().create(API.class);
    List<Movie> popularList = new ArrayList<>();
    List<Movie> nowplayingList = new ArrayList<>();
    List<Movie> comingList = new ArrayList<>();
    List<Movie> topratedList = new ArrayList<>();
    MovieAdapter movieAdapter;
    RecyclerView rvPopular, rvNowPlaying, rvComing, rvTopRated;
    TextView tvPopular, tvNowPlaying, tvComing, tvTopRated, btn_all_1, btn_all_2, btn_all_3, btn_all_4;
    FrameLayout spin_kit1, spin_kit2, spin_kit3, spin_kit4;
    SearchView searchView;
    ImageView logo;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        transparentStatusAndNavigation();

        //anh xa
        logo = view.findViewById(R.id.logo);
        searchView = view.findViewById(R.id.searchView);
        rvPopular = view.findViewById(R.id.rvPopular);
        rvNowPlaying = view.findViewById(R.id.rvNowPlaying);
        rvComing = view.findViewById(R.id.rvComing);
        rvTopRated = view.findViewById(R.id.rvTopRated);
        tvPopular = view.findViewById(R.id.tvPopular);
        tvNowPlaying = view.findViewById(R.id.tvNowPlaying);
        tvComing = view.findViewById(R.id.tvComing);
        tvTopRated = view.findViewById(R.id.tvTopRated);
        btn_all_1 = view.findViewById(R.id.btn_all_1);
        btn_all_2 = view.findViewById(R.id.btn_all_2);
        btn_all_3 = view.findViewById(R.id.btn_all_3);
        btn_all_4 = view.findViewById(R.id.btn_all_4);
        spin_kit1 = view.findViewById(R.id.spin_kit1);
        spin_kit2 = view.findViewById(R.id.spin_kit2);
        spin_kit3 = view.findViewById(R.id.spin_kit3);
        spin_kit4 = view.findViewById(R.id.spin_kit4);

        spin_kit1.setVisibility(View.VISIBLE);
        spin_kit2.setVisibility(View.VISIBLE);
        spin_kit3.setVisibility(View.VISIBLE);
        spin_kit4.setVisibility(View.VISIBLE);

        //LinearGradient textview
        Shader myShader = new LinearGradient(
                0, 0, 0, 100,
                Color.parseColor("#DF7401"), Color.parseColor("#FF0040"),
                Shader.TileMode.CLAMP);
        tvPopular.getPaint().setShader(myShader);
        tvNowPlaying.getPaint().setShader(myShader);
        tvComing.getPaint().setShader(myShader);
        tvTopRated.getPaint().setShader(myShader);

        getData(ApiKey.THEMOVIE_APP_KEY, 1);

        //view all
        btn_all_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewAllMovieScreen.class);
                intent.putExtra("id", "popular");
                startActivity(intent);

            }
        });

        btn_all_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewAllMovieScreen.class);
                intent.putExtra("id", "nowplaying");
                startActivity(intent);

            }
        });

        btn_all_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewAllMovieScreen.class);
                intent.putExtra("id", "upcoming");
                startActivity(intent);

            }
        });

        btn_all_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewAllMovieScreen.class);
                intent.putExtra("id", "toprated");
                startActivity(intent);

            }
        });

        //search
        searchView.setIconified(false);
        searchView.setIconifiedByDefault(false);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getActivity(), SearchScreen.class);
                intent.putExtra("key", "movie");
                intent.putExtra("query", query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() == 0) {
                    //hide keyboard
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                }
                return false;
            }
        });

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide keyboard
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
            }
        });
    }

    void getData(String apiKey, int page) {
        //popular
        api.getPopularMovie(apiKey, page).enqueue(new Callback<MovieRespone>() {
            @Override
            public void onResponse(Call<MovieRespone> call, Response<MovieRespone> response) {
                if (response.isSuccessful() && response.body() != null) {
                    popularList = response.body().getResults();
                    spin_kit1.setVisibility(View.INVISIBLE);

                    //đổ dữ liệu lên recyclerview
                    movieAdapter = new MovieAdapter(getActivity(), popularList);
                    //layout ngang
                    rvPopular.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    rvPopular.setAdapter(movieAdapter);
                }
            }

            @Override
            public void onFailure(Call<MovieRespone> call, Throwable t) {

            }
        });

        //now playing
        api.getNowPlayingMovie(apiKey, page).enqueue(new Callback<MovieRespone>() {
            @Override
            public void onResponse(Call<MovieRespone> call, Response<MovieRespone> response) {
                if (response.isSuccessful() && response.body() != null) {
                    nowplayingList = response.body().getResults();
                    spin_kit2.setVisibility(View.INVISIBLE);

                    //đổ dữ liệu lên recyclerview
                    movieAdapter = new MovieAdapter(getActivity(), nowplayingList);
                    //layout ngang
                    rvNowPlaying.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    rvNowPlaying.setAdapter(movieAdapter);
                }
            }

            @Override
            public void onFailure(Call<MovieRespone> call, Throwable t) {

            }
        });

        //coming
        api.getComingMovie(apiKey, page).enqueue(new Callback<MovieRespone>() {
            @Override
            public void onResponse(Call<MovieRespone> call, Response<MovieRespone> response) {
                if (response.isSuccessful() && response.body() != null) {
                    comingList = response.body().getResults();
                    spin_kit3.setVisibility(View.INVISIBLE);

                    //đổ dữ liệu lên recyclerview
                    movieAdapter = new MovieAdapter(getActivity(), comingList);
                    //layout ngang
                    rvComing.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    rvComing.setAdapter(movieAdapter);
                }

            }

            @Override
            public void onFailure(Call<MovieRespone> call, Throwable t) {

            }
        });

        //top rated
        api.getTopMovie(apiKey, page).enqueue(new Callback<MovieRespone>() {
            @Override
            public void onResponse(Call<MovieRespone> call, Response<MovieRespone> response) {
                if (response.isSuccessful() && response.body() != null) {
                    topratedList = response.body().getResults();
                    spin_kit4.setVisibility(View.INVISIBLE);

                    //đổ dữ liệu lên recyclerview
                    movieAdapter = new MovieAdapter(getActivity(), topratedList);
                    //layout ngang
                    rvTopRated.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    rvTopRated.setAdapter(movieAdapter);
                }

            }

            @Override
            public void onFailure(Call<MovieRespone> call, Throwable t) {

            }
        });
    }

    private void transparentStatusAndNavigation() {
        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            );
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
            getActivity().getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    private void setWindowFlag(final int bits, boolean on) {
        Window win = getActivity().getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

}
