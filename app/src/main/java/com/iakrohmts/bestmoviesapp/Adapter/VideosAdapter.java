package com.iakrohmts.bestmoviesapp.Adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.iakrohmts.bestmoviesapp.Model.Video;
import com.iakrohmts.bestmoviesapp.R;
import java.util.List;

/**
 * Created by Rohmts on 5/21/2017.
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.MyViewHolder> {

  private Context context;
  private List<Video> videoList;

  public VideosAdapter(Context context, List<Video> videoList) {
    this.context = context;
    this.videoList = videoList;
  }

  @Override
  public VideosAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int pos) {
    View view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.model_video_trailer_card, viewGroup, false);
    return new MyViewHolder(view);
  }

  @Override
  public void onBindViewHolder(VideosAdapter.MyViewHolder holder, int position) {
    Glide.with(context)
        .load(videoList.get(position).getBaseThumbnailUrl())
        .into(holder.thumbnail_trailer);
  }

  @Override
  public int getItemCount() {
    return videoList.size();
  }

  public class MyViewHolder extends RecyclerView.ViewHolder {

    public ImageView thumbnail_trailer;

    public MyViewHolder(View itemView) {
      super(itemView);
      thumbnail_trailer = (ImageView) itemView.findViewById(R.id.thumbnail_trailer);
      itemView.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          int pos = getAdapterPosition();
          if (pos != RecyclerView.NO_POSITION) {
            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube.com:"+videoList.get(pos).getKey()));
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://youtube.com/watch?v="+videoList.get(pos).getKey()));
            try {
              context.startActivity(appIntent);
            } catch (ActivityNotFoundException ex) {
                context.startActivity(webIntent);
            }
          }
        }
      });
    }
  }
}
