package adapter;

import android.app.Activity;
import android.content.Intent;
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

import com.example.polymovie.DetailsTVScreen;
import com.example.polymovie.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.TV;


public class TVAdapter extends RecyclerView.Adapter<TVAdapter.RecyclerViewHolder> {
    Activity context;
    List<TV> data;
    private int lastPosition = -1;

    public TVAdapter(Activity context, List<TV> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public TVAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_tv, parent, false);
//        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//        layoutParams.width = (parent.getWidth() * 1 / 3);
//        view.setLayoutParams(layoutParams);
        return new TVAdapter.RecyclerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final TVAdapter.RecyclerViewHolder holder, final int position) {
        final TV tv = this.data.get(position);

        try {
            if (tv.getBackdrop_path() != null) {
                Picasso.with(context)
                        .load("https://image.tmdb.org/t/p/w500" + tv.getBackdrop_path())
                        .into(holder.imgBanner);
            } else {
                Picasso.with(context)
                        .load("https://image.tmdb.org/t/p/w500" + tv.getPoster_path())
                        .into(holder.imgBanner);
            }

        } catch (Exception e) {

        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsTVScreen.class);
                intent.putExtra("id", tv.getId());
                if (tv.getBackdrop_path() != null) {
                    intent.putExtra("backdrop_path", tv.getBackdrop_path());
                    intent.putExtra("poster_path", tv.getPoster_path());
                } else {
                    intent.putExtra("backdrop_path", tv.getPoster_path());
                    intent.putExtra("poster_path", tv.getPoster_path());
                }

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(context, (View) holder.imgBanner, "banner");

                context.startActivity(intent, options.toBundle());

            }
        });

        holder.tvTitle.setText(tv.getName());
        //holder.tvDate.setText(movie.getRelease_date());

        //animation
        //setAnimation(holder.itemView, position);
        setFadeAnimation(holder.itemView);
        //setScaleAnimation(holder.itemView);

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
        ImageView imgBanner;
        TextView tvTitle, tvDate;
        CardView item;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            imgBanner = itemView.findViewById(R.id.imgBanner);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            //tvDate  =itemView.findViewById(R.id.tvDate);
        }
    }
}

