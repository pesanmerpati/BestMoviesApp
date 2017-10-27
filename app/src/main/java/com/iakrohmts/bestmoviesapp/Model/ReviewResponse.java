package com.iakrohmts.bestmoviesapp.Model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by Rohmts on 5/21/2017.
 */

public class ReviewResponse {
  @SerializedName("id")
  private String id;
  @SerializedName("page")
  private int page;
  @SerializedName("results")
  private List<Review> results;
  @SerializedName("total_pages")
  private int totalPages;
  @SerializedName("total_results")
  private int totalResults;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public List<Review> getResults() {
    return results;
  }

  public void setResults(List<Review> results) {
    this.results = results;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public int getTotalResults() {
    return totalResults;
  }

  public void setTotalResults(int totalResults) {
    this.totalResults = totalResults;
  }
}
