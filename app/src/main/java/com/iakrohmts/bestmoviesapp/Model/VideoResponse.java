package com.iakrohmts.bestmoviesapp.Model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by Rohmts on 5/21/2017.
 */

public class VideoResponse {
  @SerializedName("id")
  private String id;
  @SerializedName("results")
  private List<Video> results;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<Video> getResults() {
    return results;
  }

  public void setResults(List<Video> results) {
    this.results = results;
  }
}
