package com.iakrohmts.bestmoviesapp.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rohmts on 5/21/2017.
 */

public class Video {
  @SerializedName("id")
  private String id;
  @SerializedName("key")
  private String key;
  @SerializedName("name")
  private String name;
  @SerializedName("site")
  private String youtube;
  @SerializedName("size")
  private String size;
  @SerializedName("type")
  private String type;

  public Video(String id, String key, String name, String youtube, String size, String type) {
    this.id = id;
    this.key = key;
    this.name = name;
    this.youtube = youtube;
    this.size = size;
    this.type = type;
  }

  String baseThumbnailUrl = "http://img.youtube.com/vi/"+key+"/mqdefault.jpg";

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getYoutube() {
    return youtube;
  }

  public void setYoutube(String youtube) {
    this.youtube = youtube;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getBaseThumbnailUrl() {
    return "http://img.youtube.com/vi/"+key+"/hqdefault.jpg";
  }

  public void setBaseThumbnailUrl(String baseThumbnailUrl) {
    this.baseThumbnailUrl = baseThumbnailUrl;
  }
}
