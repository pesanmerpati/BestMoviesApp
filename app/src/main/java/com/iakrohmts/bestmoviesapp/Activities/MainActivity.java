package com.iakrohmts.bestmoviesapp.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.iakrohmts.bestmoviesapp.Adapter.MoviesAdapter;
import com.iakrohmts.bestmoviesapp.Api.Client;
import com.iakrohmts.bestmoviesapp.BuildConfig;
import com.iakrohmts.bestmoviesapp.Interface.ApiService;
import com.iakrohmts.bestmoviesapp.Model.Movie;
import com.iakrohmts.bestmoviesapp.Model.MovieResponse;
import com.iakrohmts.bestmoviesapp.R;
import com.iakrohmts.bestmoviesapp.SQLiteDb.DBDataSource;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

  private RecyclerView recyclerView;
  private MoviesAdapter adapter;
  private List<Movie> movieList;
  private ArrayList<Movie> movieArrayList = new ArrayList<>();
  ProgressDialog pd;
  private SwipeRefreshLayout swipe;
  private RelativeLayout relativeL;
  Toolbar toolbar;

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
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    initViews();
    swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
    swipe.setColorSchemeResources(android.R.color.holo_blue_dark,
        android.R.color.holo_blue_bright,
        android.R.color.holo_orange_dark,
        android.R.color.holo_red_dark);
    swipe.setOnRefreshListener(new OnRefreshListener() {
      @Override
      public void onRefresh() {
        initViews();
      }
    });
  }



  private void initViews() {
    pd = new ProgressDialog(this);
    pd.setMessage("Fetching movies...");
    pd.setCanceledOnTouchOutside(false);
    pd.show();

    recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    relativeL = (RelativeLayout) findViewById(R.id.relativeLayout);

    movieList = new ArrayList<>();
    adapter = new MoviesAdapter(this, movieList);

    if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
      recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    } else {
      recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
    }
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setAdapter(adapter);
    adapter.notifyDataSetChanged();
    loadJSON();
  }

  private void loadJSON() {
    try {
      Client client = new Client();
      ApiService apiService = client.getClient().create(ApiService.class);
      Call<MovieResponse> call = apiService.getPopularMovies(BuildConfig.MOVIE_API_TOKEN);
      call.enqueue(new Callback<MovieResponse>() {
        @Override
        public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
          List<Movie> movies = response.body().getResults();
          recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(), movies));
          recyclerView.smoothScrollToPosition(0);
          if (swipe.isRefreshing()) {
            swipe.setRefreshing(false);
          }
          pd.dismiss();
        }

        @Override
        public void onFailure(Call<MovieResponse> call, Throwable t) {
          final Snackbar snackbar = Snackbar.make(relativeL, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
              .setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  swipe.setRefreshing(true);
                  new Thread(new Runnable() {
                    @Override
                    public void run() {
                      SystemClock.sleep(3000);
                      getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                          loadJSON();
                        }
                      });
                    }
                  }).start();
                }
              });
          snackbar.setActionTextColor(Color.GREEN);
          View sbView = snackbar.getView();
          TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
          textView.setTextColor(Color.YELLOW);
          snackbar.show();
          swipe.setRefreshing(false);
          pd.dismiss();
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void loadJSONbyToprated() {

    try {
      Client client = new Client();
      ApiService apiService = client.getClient().create(ApiService.class);
      Call<MovieResponse> call = apiService.getTopRetedMovies(BuildConfig.MOVIE_API_TOKEN);
      call.enqueue(new Callback<MovieResponse>() {
        @Override
        public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
          List<Movie> movies = response.body().getResults();
          recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(), movies));
          recyclerView.smoothScrollToPosition(0);
          if (swipe.isRefreshing()) {
            swipe.setRefreshing(false);
          }
          pd.dismiss();

        }

        @Override
        public void onFailure(Call<MovieResponse> call, Throwable t) {
          final Snackbar snackbar = Snackbar.make(relativeL, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
              .setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  swipe.setRefreshing(true);
                  new Thread(new Runnable() {
                    @Override
                    public void run() {
                      SystemClock.sleep(3000);
                      getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                          loadJSON();
                        }
                      });
                    }
                  }).start();
                }
              });
          snackbar.setActionTextColor(Color.GREEN);
          View sbView = snackbar.getView();
          TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
          textView.setTextColor(Color.YELLOW);
          snackbar.show();
          swipe.setRefreshing(false);
          pd.dismiss();
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_popular:
        loadJSON();
        return true;
      case R.id.action_top_rated:
        loadJSONbyToprated();
        return true;
      case R.id.action_my_favorite:
        loadMyFavorit();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void loadMyFavorit() {
    adapter = new MoviesAdapter(this, movieArrayList);
    DBDataSource db = new DBDataSource(this);
    db.open();
    movieArrayList.clear();
    Cursor c = db.getAllMoviews();

    while (c.moveToNext()) {
      int id = c.getInt(0);
      String id_movie = c.getString(1);
      String original_title = c.getString(2);
      String posterPath = c.getString(3);
      String overview = c.getString(4);
      Double voteAverage = c.getDouble(5);
      String releaseDate = c.getString(6);

      Movie m = new Movie(id, id_movie, original_title, posterPath, overview, voteAverage, releaseDate);

      movieArrayList.add(m);
    }
        recyclerView.setAdapter(adapter);
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);

    // Checks the orientation of the screen
    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
      recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
      recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }
  }

}
