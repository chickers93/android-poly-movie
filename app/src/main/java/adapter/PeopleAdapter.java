package adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polymovie.DetailsPeopleScreen;
import com.example.polymovie.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import model.People;


public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.RecyclerViewHolder> {
    Activity context;
    List<People> data;
    private int lastPosition = -1;

    public PeopleAdapter(Activity context, List<People> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public PeopleAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_people, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (parent.getWidth() * 1 / 2);
        view.setLayoutParams(layoutParams);
        return new PeopleAdapter.RecyclerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final PeopleAdapter.RecyclerViewHolder holder, final int position) {
        final People people = this.data.get(position);

        try {

            Picasso.with(context)
                    .load("https://image.tmdb.org/t/p/w500" + people.getProfile_path())
                    .into(holder.imgAvatar);

        } catch (Exception e) {

        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsPeopleScreen.class);
                intent.putExtra("id", people.getId());
                intent.putExtra("profile_path", people.getProfile_path());
                intent.putExtra("name", people.getName());
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) people.getKnown_for());
                intent.putExtras(bundle);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(context, (View) holder.imgAvatar, "poster");

                context.startActivity(intent, options.toBundle());

            }
        });

        holder.tvName.setText(people.getName());
        //holder.PeopleDate.setText(movie.getRelease_date());

        //animation
        //setAnimation(holder.itemView, position);
        //setFadeAnimation(holder.itemView);
        setScaleAnimation(holder.itemView);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000);
        view.startAnimation(anim);
    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(700);
        view.startAnimation(anim);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvName;
        CardView item;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}

