package adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polymovie.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.Season;


public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.RecyclerViewHolder> {
    Activity context;
    List<Season> data;

    public SeasonAdapter(Activity context, List<Season> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public SeasonAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_season, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (parent.getWidth() * 1 / 3);
        view.setLayoutParams(layoutParams);
        return new SeasonAdapter.RecyclerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final SeasonAdapter.RecyclerViewHolder holder, final int position) {
        final Season season = this.data.get(position);

        try {
            Picasso.with(context)
                    .load("https://image.tmdb.org/t/p/w500" + season.getPoster_path())
                    .into(holder.imgPoster);
        } catch (Exception e) {

        }

        holder.tvTitle.setText(season.getName());
        holder.tvEp.setText(String.valueOf((int) season.getEpisode_count()) + " Episodes");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvEp;
        CardView item;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvEp = itemView.findViewById(R.id.tvEp);
        }
    }
}

