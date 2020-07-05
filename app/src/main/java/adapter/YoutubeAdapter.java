package adapter;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.polymovie.DialogYoutube;
import com.example.polymovie.R;

import java.util.List;

import model.Video;

public class YoutubeAdapter extends RecyclerView.Adapter<YoutubeAdapter.VideoHolder> {
    private Activity activity;
    private List<Video> listVideo;

    public YoutubeAdapter(Activity activity, List<Video> listVideo) {
        this.activity = activity;
        this.listVideo = listVideo;
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        final Video video = listVideo.get(position);
        Glide.with(activity)
                .load(video.getThumnail())
                .centerCrop()
                .into(holder.imgThumnail);
        holder.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("Video", video);
                Intent intent = new Intent(activity, DialogYoutube.class);
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listVideo.size();
    }

    class VideoHolder extends RecyclerView.ViewHolder {
        ImageView imgThumnail, btnPlay;

        public VideoHolder(View itemView) {
            super(itemView);
            imgThumnail = itemView.findViewById(R.id.imageViewItem);
            btnPlay = itemView.findViewById(R.id.btnPlay);
        }
    }
}
