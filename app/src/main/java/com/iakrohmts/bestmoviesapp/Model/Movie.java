package com.iakrohmts.bestmoviesapp.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rohmts on 5/20/2017.
 */

public class Movie {
  @SerializedName("poster_path")
  private String posterPath;
  @SerializedName("overview")
  private String overview;
  @SerializedName("release_date")
  private String releaseDate;
  @SerializedName("id")
  private String id;
  @SerializedName("original_title")
  private String originalTitle;
  @SerializedName("title")
  private String title;
  @SerializedName("backdrop_path")
  private String backdropPath;
  @SerializedName("popularity")
  private String popularity;
  @SerializedName("vote_count")
  private Integer voteCount;
  @SerializedName("video")
  private boolean video;
  @SerializedName("vote_average")
  private Double voteAverage;

  private int id_increment;

  public Movie(int id_increment, String id, String originalTitle, String posterPath, String overview,
      Double voteAverage, String releaseDate) {
    this.id = id;
    this.id_increment = id_increment;
    this.originalTitle = originalTitle;
    this.posterPath = posterPath;
    this.overview = overview;
    this.voteAverage = voteAverage;
    this.releaseDate = releaseDate;
  }


  public Movie(String posterPath, String overview, String releaseDate,
      String id, String originalTitle, String title, String backdropPath,
      String popularity, Integer voteCount, boolean video, Double voteAverage) {
    this.posterPath = posterPath;
    this.overview = overview;
    this.releaseDate = releaseDate;
    this.id = id;
    this.originalTitle = originalTitle;
    this.title = title;
    this.backdropPath = backdropPath;
    this.popularity = popularity;
    this.voteCount = voteCount;
    this.video = video;
    this.voteAverage = voteAverage;
  }

  public String getPosterPath() {
    return posterPath;
  }

  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOriginalTitle() {
    return originalTitle;
  }

  public void setOriginalTitle(String originalTitle) {
    this.originalTitle = originalTitle;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBackdropPath() {
    return backdropPath;
  }

  public void setBackdropPath(String backdropPath) {
    this.backdropPath = backdropPath;
  }

  public String getPopularity() {
    return popularity;
  }

  public void setPopularity(String popularity) {
    this.popularity = popularity;
  }

  public Integer getVoteCount() {
    return voteCount;
  }

  public void setVoteCount(Integer voteCount) {
    this.voteCount = voteCount;
  }

  public boolean isVideo() {
    return video;
  }

  public void setVideo(boolean video) {
    this.video = video;
  }

  public Double getVoteAverage() {
    return voteAverage;
  }

  public void setVoteAverage(Double voteAverage) {
    this.voteAverage = voteAverage;
  }

  public int getId_increment() {
    return id_increment;
  }

  public void setId_increment(int id_increment) {
    this.id_increment = id_increment;
  }

}
