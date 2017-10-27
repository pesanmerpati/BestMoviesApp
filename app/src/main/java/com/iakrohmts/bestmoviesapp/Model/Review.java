package com.iakrohmts.bestmoviesapp.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rohmts on 5/21/2017.
 */

public class Review {
  @SerializedName("id")
  private String id;
  @SerializedName("author")
  private String author;
  @SerializedName("content")
  private String content;
  @SerializedName("url")
  private String url;

  public Review(String id, String author, String content, String url) {
    this.id = id;
    this.author = author;
    this.content = content;
    this.url = url;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
