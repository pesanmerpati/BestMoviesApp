package com.iakrohmts.bestmoviesapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.OnOffsetChangedListener;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.iakrohmts.bestmoviesapp.Adapter.VideosAdapter;
import com.iakrohmts.bestmoviesapp.Api.Client;
import com.iakrohmts.bestmoviesapp.BuildConfig;
import com.iakrohmts.bestmoviesapp.Interface.ApiService;
import com.iakrohmts.bestmoviesapp.Model.Review;
import com.iakrohmts.bestmoviesapp.Model.ReviewResponse;
import com.iakrohmts.bestmoviesapp.Model.Video;
import com.iakrohmts.bestmoviesapp.Model.VideoResponse;
import com.iakrohmts.bestmoviesapp.R;
import com.iakrohmts.bestmoviesapp.SQLiteDb.DBDataSource;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rohmts on 5/20/2017.
 */

public class DetailActivity extends AppCompatActivity {

  private ImageView img;
  private TextView tvTitle,tvReleaseDate,tvRatting, tvPlotSinopsis, tvReviews, tvTrailer;
  List<Video> videoList;
  private VideosAdapter adapter;
  private CoordinatorLayout coordinatLayout;
  DBDataSource db;

  private FloatingActionButton fab;

  public Activity getActivity() {
    Context context = this;
    while (context instanceof ContextWrapper) {
      if (context instanceof Activity) {
        return (Activity) context;
      }
      context = ((ContextWrapper) context).getBaseContext();
    }
    return null;
  }

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
    collapsingToolbarLayout.setTitle(" ");
    AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
    appBarLayout.setExpanded(true);

    appBarLayout.addOnOffsetChangedListener(new OnOffsetChangedListener() {

      boolean isShow = false;
      int scrollRange = -1;

      @Override
      public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (scrollRange == -1) {
          scrollRange = appBarLayout.getTotalScrollRange();
        }
        if (scrollRange + verticalOffset ==0) {
          collapsingToolbarLayout.setTitle(getString(R.string.movie_details));
          isShow = true;
        } else if (isShow) {
          collapsingToolbarLayout.setTitle(" ");
          isShow = false;
        }
      }
    });

    coordinatLayout = (CoordinatorLayout) findViewById(R.id.detail_content);
    initViews();

    fab = (FloatingActionButton) findViewById(R.id.fab_favourite);
    fab.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        saveData();
      }
    });
    Intent intent = getIntent();
    String id_movie = intent.getStringExtra("id");
    db = new DBDataSource(this);
    db.open();
    if (db.ifExists(id_movie)) {
      fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite));
      db.close();
    }
  }

  private void saveData() {
    Intent intent = getIntent();
    String id_movie = intent.getStringExtra("id");
    String original_title = intent.getStringExtra("original_title");
    String posterPath = intent.getStringExtra("poster_path");
    String overview = intent.getStringExtra("overview");
    Double voteAverage = Double.parseDouble(intent.getStringExtra("vote_average"));
    String releaseDate = intent.getStringExtra("release_date");
    save(id_movie, original_title, posterPath, overview, voteAverage, releaseDate);
  }

  private void save(String id_movie, String original_title, String posterPath, String overview, Double voteAverage,
      String releaseDate) {
    db = new DBDataSource(this);
    db.open();
    if (db.ifExists(id_movie)) {
      delete(id_movie);
      return;
    } else {
      long result = db.add(id_movie, original_title, posterPath, overview, voteAverage, releaseDate);
      Snackbar.make(coordinatLayout, "Added to favorite", Snackbar.LENGTH_LONG).show();
      fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite));
    }
    db.close();
  }

  private void initViews() {
    img = (ImageView) findViewById(R.id.thumbnail);
    tvTitle = (TextView) findViewById(R.id.tvTitle);
    tvReleaseDate = (TextView) findViewById(R.id.release_date);
    tvRatting = (TextView) findViewById(R.id.ratting);
    tvPlotSinopsis = (TextView) findViewById(R.id.plotSinopsis);
    tvReviews = (TextView) findViewById(R.id.reviews);
    tvTrailer = (TextView) findViewById(R.id.videoTCM);

    Intent intent = getIntent();
    if (intent.hasExtra("original_title")) {
      tvTitle.setText(intent.getStringExtra("original_title"));

      Glide.with(this).load("https://image.tmdb.org/t/p/w342" + intent.getStringExtra("poster_path"))
        .thumbnail(0.5f)
        .into(img);
      tvReleaseDate.setText(intent.getStringExtra("release_date"));
      tvRatting.setText(intent.getStringExtra("vote_average")+"/10");
      tvPlotSinopsis.setText(intent.getStringExtra("overview"));

      String id = intent.getStringExtra("id");
      //getReview
      try {
        Client client = new Client();
        ApiService apiService = client.getClient().create(ApiService.class);
        Call<ReviewResponse> call = apiService.getReviews(id, BuildConfig.MOVIE_API_TOKEN);
        call.enqueue(new Callback<ReviewResponse>() {
          @Override
          public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
            List<Review> reviews = response.body().getResults();
            if (reviews.isEmpty()) {
              tvReviews.setText(R.string.no_review);
            } else {
              for (Review r : reviews) {
                tvReviews.setText(r.getContent());
              }
            }
          }

          @Override
          public void onFailure(Call<ReviewResponse> call, Throwable t) {
            tvReviews.setText(R.string.cannt_load_data);
            tvReviews.setTypeface(null, Typeface.ITALIC);
          }
        });
      } catch (Exception e) {
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
      }

      //getVideo
      try {

        videoList = new ArrayList<>();
        adapter = new VideosAdapter(this, videoList);
        final RecyclerView mRv;
        mRv = (RecyclerView) findViewById(R.id.rvTrailer);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRv.setLayoutManager(layoutManager);
        mRv.setItemAnimator(new DefaultItemAnimator());
        mRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Client client = new Client();
        ApiService apiService = client.getClient().create(ApiService.class);
        Call<VideoResponse> call = apiService.getVideos(id, BuildConfig.MOVIE_API_TOKEN);
        call.enqueue(new Callback<VideoResponse>() {
          @Override
          public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
            List<Video> videos = response.body().getResults();
            mRv.setAdapter(new VideosAdapter(getActivity(), videos));
            mRv.smoothScrollToPosition(0);
          }

          @Override
          public void onFailure(Call<VideoResponse> call, Throwable t) {
            mRv.setVisibility(View.GONE);
            tvTrailer.setVisibility(View.VISIBLE);
            tvTrailer.setText(R.string.cannt_load_data);
          }
        });
      } catch (Exception e) {
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
      }
    } else {
      Toast.makeText(this,"No API data",Toast.LENGTH_SHORT).show();
    }

  }

  private void delete(String id_movie)
  {
    db = new DBDataSource(this);
    db.open();
    long result=db.delete(id_movie);

    if(result>0)
    {
      Snackbar.make(coordinatLayout, "Removed from favorite", Snackbar.LENGTH_LONG).show();
      fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_unfavorite));
    }else
    {
      Snackbar.make(coordinatLayout,"Unable to delete",Snackbar.LENGTH_SHORT).show();
    }

    db.close();
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        return true;
    }
    return false;
  }
}
