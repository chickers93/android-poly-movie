package com.example.polymovie;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import adapter.KnownForAdapter;
import api.API;
import api.ApiKey;
import api.RetrofitClient;
import model.KnownFor;
import model.PeopleDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsPeopleScreen extends AppCompatActivity {
    LinearLayout btn_back;
    Intent intent;
    String id, profile_path, name;
    TextView tv_name, tv_information, tv_biography, tv_bio, tv_place, tv_birthday, tv_gender, tv_knownfor;
    ImageView img_profile_path;
    String language = "en";
    RetrofitClient retrofit = new RetrofitClient();
    List<KnownFor> list = new ArrayList<>();
    API api = retrofit.getClient().create(API.class);
    PeopleDetails peopleDetails;
    KnownForAdapter adapter;
    RecyclerView rvVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_people_screen);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(DetailsPeopleScreen.this, R.color.white));// set status background white

        transparentStatusAndNavigation();

        intent = getIntent();
        id = intent.getStringExtra("id");
        profile_path = intent.getStringExtra("profile_path");
        name = intent.getStringExtra("name");
        Bundle bundle = getIntent().getExtras();
        list = bundle.getParcelableArrayList("list");
        //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

        //anh xa
        img_profile_path = findViewById(R.id.img_profile_path);
        tv_name = findViewById(R.id.tv_name);
        tv_information = findViewById(R.id.tv_information);
        tv_biography = findViewById(R.id.tv_biography);
        tv_bio = findViewById(R.id.tv_bio);
        tv_gender = findViewById(R.id.tv_gender);
        tv_birthday = findViewById(R.id.tv_birthday);
        tv_place = findViewById(R.id.tv_place);
        tv_knownfor = findViewById(R.id.tv_knownfor);
        rvVideos = findViewById(R.id.rvVideos);
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                supportFinishAfterTransition();

            }
        });

        Picasso.with(DetailsPeopleScreen.this).load("https://image.tmdb.org/t/p/w500" + profile_path).into(img_profile_path);
        tv_name.setText(name);

        //LinearGradient textview
        Shader myShader = new LinearGradient(
                0, 0, 0, 100,
                Color.parseColor("#DF7401"), Color.parseColor("#FF0040"),
                Shader.TileMode.CLAMP);
        tv_information.getPaint().setShader(myShader);
        tv_knownfor.getPaint().setShader(myShader);

        getData(id, ApiKey.THEMOVIE_APP_KEY, language);

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
        api.getPeopleDetails(id, apiKey, language).enqueue(new Callback<PeopleDetails>() {
            @Override
            public void onResponse(Call<PeopleDetails> call, Response<PeopleDetails> response) {
                if (response.isSuccessful() && response.body() != null) {
                    peopleDetails = response.body();
                    if (peopleDetails.getBiography().length() == 0) {
                        tv_bio.setText("-");
                    } else {
                        tv_bio.setText(peopleDetails.getBiography());
                    }

                    if (peopleDetails.getBirthday() == null) {
                        tv_birthday.setText("-");
                    } else {
                        tv_birthday.setText(peopleDetails.getBirthday());
                    }

                    if (peopleDetails.getPlace_of_birth() == null) {
                        tv_place.setText("-");
                    } else {
                        tv_place.setText(peopleDetails.getPlace_of_birth());
                    }

                    tv_gender.setText(getGender(peopleDetails.getGender()));


                }

            }

            @Override
            public void onFailure(Call<PeopleDetails> call, Throwable t) {

            }
        });

        if (list != null) {
            adapter = new KnownForAdapter(DetailsPeopleScreen.this, list);
            //layout grid
            //GridLayoutManager manager = new GridLayoutManager(DetailsPeopleScreen.this, 1);
            //rvVideos.setLayoutManager(manager);
            //layout ngang
            rvVideos.setLayoutManager(new LinearLayoutManager(DetailsPeopleScreen.this, LinearLayoutManager.HORIZONTAL, false));
            rvVideos.setAdapter(adapter);
        }

    }

    public String getGender(float i) {
        String gender;
        if (i == 2) {
            gender = "Male";
        } else {
            gender = "Female";
        }

        return gender;

    }
}
