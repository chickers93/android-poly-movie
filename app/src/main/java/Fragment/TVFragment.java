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
import com.example.polymovie.ViewAllTVScreen;

import java.util.ArrayList;
import java.util.List;

import adapter.TVAdapter;
import api.API;
import api.ApiKey;
import api.RetrofitClient;
import model.TV;
import model.TVRespone;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVFragment extends Fragment {
    RetrofitClient retrofit = new RetrofitClient();
    API api = retrofit.getClient().create(API.class);
    List<TV> popularList = new ArrayList<>();
    List<TV> airingList = new ArrayList<>();
    List<TV> ontvList = new ArrayList<>();
    List<TV> topratedList = new ArrayList<>();
    TVAdapter tvAdapter;
    RecyclerView rvPopular, rvAiring, rvOnTV, rvTopRated;
    TextView tvPopular, tvNowPlaying, tvComing, tvTopRated, btn_all_1, btn_all_2, btn_all_3, btn_all_4;
    FrameLayout spin_kit1, spin_kit2, spin_kit3, spin_kit4;
    SearchView searchView;
    ImageView logo;

    public TVFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        transparentStatusAndNavigation();

        //anh xa
        logo = view.findViewById(R.id.logo);
        searchView = view.findViewById(R.id.searchView);
        rvPopular = view.findViewById(R.id.rvPopular);
        rvAiring = view.findViewById(R.id.rvAiring);
        rvOnTV = view.findViewById(R.id.rvOnTV);
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
                Intent intent = new Intent(getActivity(), ViewAllTVScreen.class);
                intent.putExtra("id", "popular");
                startActivity(intent);

            }
        });

        btn_all_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewAllTVScreen.class);
                intent.putExtra("id", "airing");
                startActivity(intent);

            }
        });

        btn_all_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewAllTVScreen.class);
                intent.putExtra("id", "ontv");
                startActivity(intent);

            }
        });

        btn_all_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewAllTVScreen.class);
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
                intent.putExtra("key", "tv");
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
        api.getPopularTV(apiKey, page).enqueue(new Callback<TVRespone>() {
            @Override
            public void onResponse(Call<TVRespone> call, Response<TVRespone> response) {
                if (response.isSuccessful() && response.body() != null) {
                    popularList = response.body().getResults();
                    spin_kit1.setVisibility(View.INVISIBLE);

                    //đổ dữ liệu lên recyclerview
                    tvAdapter = new TVAdapter(getActivity(), popularList);
                    //layout ngang
                    rvPopular.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    rvPopular.setAdapter(tvAdapter);
                }
            }

            @Override
            public void onFailure(Call<TVRespone> call, Throwable t) {

            }
        });

        //airing
        api.getAiringTV(apiKey, page).enqueue(new Callback<TVRespone>() {
            @Override
            public void onResponse(Call<TVRespone> call, Response<TVRespone> response) {
                if (response.isSuccessful() && response.body() != null) {
                    airingList = response.body().getResults();
                    spin_kit2.setVisibility(View.INVISIBLE);

                    //đổ dữ liệu lên recyclerview
                    tvAdapter = new TVAdapter(getActivity(), airingList);
                    //layout ngang
                    rvAiring.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    rvAiring.setAdapter(tvAdapter);
                }
            }

            @Override
            public void onFailure(Call<TVRespone> call, Throwable t) {

            }
        });

        //ontv
        api.getOnTV(apiKey, page).enqueue(new Callback<TVRespone>() {
            @Override
            public void onResponse(Call<TVRespone> call, Response<TVRespone> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ontvList = response.body().getResults();
                    spin_kit3.setVisibility(View.INVISIBLE);

                    //đổ dữ liệu lên recyclerview
                    tvAdapter = new TVAdapter(getActivity(), ontvList);
                    //layout ngang
                    rvOnTV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    rvOnTV.setAdapter(tvAdapter);
                }
            }

            @Override
            public void onFailure(Call<TVRespone> call, Throwable t) {

            }
        });

        //top rated
        api.getTopTV(apiKey, page).enqueue(new Callback<TVRespone>() {
            @Override
            public void onResponse(Call<TVRespone> call, Response<TVRespone> response) {
                if (response.isSuccessful() && response.body() != null) {
                    topratedList = response.body().getResults();
                    spin_kit4.setVisibility(View.INVISIBLE);

                    //đổ dữ liệu lên recyclerview
                    tvAdapter = new TVAdapter(getActivity(), topratedList);
                    //layout ngang
                    rvTopRated.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    rvTopRated.setAdapter(tvAdapter);
                }
            }

            @Override
            public void onFailure(Call<TVRespone> call, Throwable t) {

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
