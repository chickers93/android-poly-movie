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

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polymovie.DetailsMovieScreen;
import com.example.polymovie.DetailsTVScreen;
import com.example.polymovie.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.KnownFor;


public class KnownForAdapter extends RecyclerView.Adapter<KnownForAdapter.RecyclerViewHolder> {
    Activity context;
    List<KnownFor> data;
    private int lastPosition = -1;

    public KnownForAdapter(Activity context, List<KnownFor> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public KnownForAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_knownfor, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (parent.getWidth() * 1 / 3);
        view.setLayoutParams(layoutParams);
        return new KnownForAdapter.RecyclerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final KnownForAdapter.RecyclerViewHolder holder, final int position) {
        final KnownFor knownFor = this.data.get(position);

        try {
            if (knownFor.getPoster_path() != null) {
                Picasso.with(context)
                        .load("https://image.tmdb.org/t/p/w500" + knownFor.getPoster_path())
                        .into(holder.imgPoster);
            } else {
                Picasso.with(context)
                        .load("https://image.tmdb.org/t/p/w500" + knownFor.getBackdrop_path())
                        .into(holder.imgPoster);
            }

        } catch (Exception e) {

        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (knownFor.getMedia_type().equals("movie")) {
                    Intent intent = new Intent(context, DetailsMovieScreen.class);
                    intent.putExtra("id", knownFor.getId());
                    intent.putExtra("backdrop_path", knownFor.getBackdrop_path());
                    intent.putExtra("poster_path", knownFor.getPoster_path());

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(context, (View) holder.imgPoster, "poster");

                    context.startActivity(intent, options.toBundle());

                } else {
                    Intent intent = new Intent(context, DetailsTVScreen.class);
                    intent.putExtra("id", knownFor.getId());
                    if (knownFor.getBackdrop_path() != null) {
                        intent.putExtra("backdrop_path", knownFor.getBackdrop_path());
                        intent.putExtra("poster_path", knownFor.getPoster_path());
                    } else {
                        intent.putExtra("backdrop_path", knownFor.getPoster_path());
                        intent.putExtra("poster_path", knownFor.getPoster_path());
                    }

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(context, (View) holder.imgPoster, "banner");

                    context.startActivity(intent, options.toBundle());

                }
            }
        });


        //animation
        setAnimation(holder.itemView, position);

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
        ImageView imgPoster;
        CardView item;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            imgPoster = itemView.findViewById(R.id.imgPoster);
        }
    }
}

