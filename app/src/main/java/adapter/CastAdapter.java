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

import model.Cast;


public class CastAdapter extends RecyclerView.Adapter<CastAdapter.RecyclerViewHolder> {
    Activity context;
    List<Cast> data;

    public CastAdapter(Activity context, List<Cast> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public CastAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_cast, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (parent.getWidth() * 1 / 3);
        view.setLayoutParams(layoutParams);
        return new CastAdapter.RecyclerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final CastAdapter.RecyclerViewHolder holder, final int position) {
        final Cast cast = this.data.get(position);

        try {
            Picasso.with(context)
                    .load("https://image.tmdb.org/t/p/w500" + cast.getProfile_path())
                    .into(holder.avatar);
        } catch (Exception e) {

        }

        if (cast.getName().length() > 12) {
            holder.name.setText(cast.getName().substring(0, 12) + "...");
        } else {
            holder.name.setText(cast.getName());
        }

        if (cast.getCharacter().length() > 12) {
            holder.character.setText(cast.getCharacter().substring(0, 12) + "...");
        } else {
            holder.character.setText(cast.getCharacter());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView name, character;
        CardView item;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            avatar = itemView.findViewById(R.id.avatar);
            name = itemView.findViewById(R.id.name);
            character = itemView.findViewById(R.id.character);
        }
    }
}

