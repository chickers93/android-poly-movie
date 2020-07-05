package Fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polymovie.R;
import com.example.polymovie.SearchScreen;

import java.util.ArrayList;
import java.util.List;

import adapter.PeopleAdapter;
import api.API;
import api.ApiKey;
import api.RetrofitClient;
import model.People;
import model.PeopleRespone;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PeopleFragment extends Fragment {
    List<People> peopleList = new ArrayList<>();
    PeopleAdapter peopleAdapter;
    RecyclerView rvPeople;
    RetrofitClient retrofit = new RetrofitClient();
    API api = retrofit.getClient().create(API.class);
    int page = 1;
    int total_pages;
    TextView tv_total_pages;
    EditText edt_page;
    ImageView img_search, img_back_1, img_back_2, img_next_1, img_next_2;
    ProgressBar progressBar;
    Handler handler = new Handler();
    SearchView searchView;
    ImageView logo;


    public PeopleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_people, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        transparentStatusAndNavigation();

        //anh xa
        logo = view.findViewById(R.id.logo);
        searchView = view.findViewById(R.id.searchView);
        rvPeople = view.findViewById(R.id.rvPeople);
        edt_page = view.findViewById(R.id.edt_page);
        edt_page.setText(String.valueOf(page));
        tv_total_pages = view.findViewById(R.id.tv_total_pages);
        img_search = view.findViewById(R.id.img_search);
        img_back_1 = view.findViewById(R.id.img_back_1);
        img_back_2 = view.findViewById(R.id.img_back_2);
        img_next_1 = view.findViewById(R.id.img_next_1);
        img_next_2 = view.findViewById(R.id.img_next_2);
        progressBar = view.findViewById(R.id.spin_kit);

        checkPage(Integer.parseInt(edt_page.getText().toString()));

        getData(ApiKey.THEMOVIE_APP_KEY, page);

        //su kien
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peopleList = new ArrayList<>();
                peopleAdapter = new PeopleAdapter(getActivity(), peopleList);
                rvPeople.setAdapter(peopleAdapter);
                progressBar.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData(ApiKey.THEMOVIE_APP_KEY, Integer.parseInt(edt_page.getText().toString()));
                        page = Integer.parseInt(edt_page.getText().toString());
                        //clear focus
                        edt_page.clearFocus();
                        //hide keyboard
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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
                peopleList = new ArrayList<>();
                peopleAdapter = new PeopleAdapter(getActivity(), peopleList);
                rvPeople.setAdapter(peopleAdapter);
                progressBar.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData(ApiKey.THEMOVIE_APP_KEY, --page);
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
                peopleList = new ArrayList<>();
                peopleAdapter = new PeopleAdapter(getActivity(), peopleList);
                rvPeople.setAdapter(peopleAdapter);
                progressBar.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData(ApiKey.THEMOVIE_APP_KEY, ++page);
                        edt_page.setText(String.valueOf(page));
                        checkPage(Integer.parseInt(edt_page.getText().toString()));
                    }
                }, 500);
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
                intent.putExtra("key", "person");
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
        api.getPopularPeople(apiKey, page).enqueue(new Callback<PeopleRespone>() {
            @Override
            public void onResponse(Call<PeopleRespone> call, Response<PeopleRespone> response) {
                if (response.isSuccessful() && response.body() != null) {
                    peopleList = response.body().getResults();
                    progressBar.setVisibility(View.INVISIBLE);
                    total_pages = response.body().getTotal_pages();
                    tv_total_pages.setText("/  " + total_pages);
                    setRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<PeopleRespone> call, Throwable t) {
            }
        });

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
        peopleAdapter = new PeopleAdapter(getActivity(), peopleList);
        //layout grid
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        rvPeople.setLayoutManager(manager);
        rvPeople.setAdapter(peopleAdapter);
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
