package com.iakrohmts.bestmoviesapp.Interface;

import com.iakrohmts.bestmoviesapp.Model.MovieResponse;
import com.iakrohmts.bestmoviesapp.Model.ReviewResponse;
import com.iakrohmts.bestmoviesapp.Model.VideoResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Rohmts on 5/20/2017.
 */

public interface ApiService {

  @GET("movie/popular")
  Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

  @GET("movie/top_rated")
  Call<MovieResponse> getTopRetedMovies(@Query("api_key") String apiKey);

  @GET("movie/{id}/reviews")
  Call<ReviewResponse> getReviews(@Path("id") String id, @Query("api_key") String apiKey);

  @GET("movie/{id}/videos")
  Call<VideoResponse> getVideos(@Path("id") String id, @Query("api_key") String apiKey);

}
