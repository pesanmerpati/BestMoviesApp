package com.iakrohmts.bestmoviesapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.iakrohmts.bestmoviesapp.Activities.DetailActivity;
import com.iakrohmts.bestmoviesapp.Model.Movie;
import com.iakrohmts.bestmoviesapp.R;
import java.util.List;

/**
 * Created by Rohmts on 5/20/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

  private Context context;
  private List<Movie> movieList;

  public MoviesAdapter(Context context, List<Movie> movieList) {
    this.context = context;
    this.movieList = movieList;
  }

  @Override
  public MoviesAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int pos) {
    View view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.model_movie_card, viewGroup, false);
    return new MyViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final MoviesAdapter.MyViewHolder viewHolder, int pos) {
    Glide.with(context)
        .load("https://image.tmdb.org/t/p/w342" + movieList.get(pos).getPosterPath())
        .placeholder(R.drawable.placeholder_default)
        .into(viewHolder.thumbnail);
    viewHolder.tvId_moview.setText(movieList.get(pos).getId());
    viewHolder.tvOriginaltitle.setText(movieList.get(pos).getOriginalTitle());
    viewHolder.tvOverview.setText(movieList.get(pos).getOverview());
    viewHolder.tvVoteAverage.setText(String.valueOf(movieList.get(pos).getVoteAverage()));
    viewHolder.tvReleaseDate.setText(movieList.get(pos).getReleaseDate());
  }

  @Override
  public int getItemCount() {
    return movieList.size();
  }

  public class MyViewHolder extends RecyclerView.ViewHolder {

    public ImageView thumbnail;
    public TextView tvId_increment, tvId_moview, tvOriginaltitle, tvOverview, tvVoteAverage, tvReleaseDate;

    public MyViewHolder(View view) {
      super(view);
      thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
      tvId_increment = (TextView) itemView.findViewById(R.id.id_increment);
      tvId_moview = (TextView) itemView.findViewById(R.id.id_movie);
      tvOriginaltitle = (TextView) itemView.findViewById(R.id.originaltitle);
      tvOverview = (TextView) view.findViewById(R.id.overview);
      tvVoteAverage = (TextView) view.findViewById(R.id.voteAverage);
      tvReleaseDate = (TextView) view.findViewById(R.id.releaseDate);

      view.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          int pos = getAdapterPosition();
          if (pos != RecyclerView.NO_POSITION) {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("id_increment", movieList.get(pos).getId_increment());
            intent.putExtra("id", movieList.get(pos).getId());
            intent.putExtra("original_title", movieList.get(pos).getOriginalTitle());
            intent.putExtra("poster_path", movieList.get(pos).getPosterPath());
            intent.putExtra("overview", movieList.get(pos).getOverview());
            intent.putExtra("vote_average", Double.toString(movieList.get(pos).getVoteAverage()));
            intent.putExtra("release_date", movieList.get(pos).getReleaseDate());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
          }
        }
      });
    }
  }


}
