package adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polymovie.DetailsMovieScreen;
import com.example.polymovie.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.Movie;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.RecyclerViewHolder> {
    Activity context;
    List<Movie> data;
    private int lastPosition = -1;

    public MovieAdapter(Activity context, List<Movie> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MovieAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_movie, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (parent.getWidth() * 1 / 3);
        view.setLayoutParams(layoutParams);
        return new MovieAdapter.RecyclerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MovieAdapter.RecyclerViewHolder holder, final int position) {
        final Movie movie = this.data.get(position);

        try {
            Picasso.with(context)
                    .load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path())
                    .into(holder.imgPoster);
        } catch (Exception e) {

        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsMovieScreen.class);
                intent.putExtra("id", movie.getId());
                intent.putExtra("backdrop_path", movie.getBackdrop_path());
                intent.putExtra("poster_path", movie.getPoster_path());

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(context, (View) holder.imgPoster, "poster");

                context.startActivity(intent, options.toBundle());

            }
        });

        //holder.tvTitle.setText(movie.getTitle());
        //holder.tvDate.setText(movie.getRelease_date());

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

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvDate;
        CardView item;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            //tvTitle = itemView.findViewById(R.id.tvTitle);
            //tvDate  =itemView.findViewById(R.id.tvDate);
        }
    }
}

