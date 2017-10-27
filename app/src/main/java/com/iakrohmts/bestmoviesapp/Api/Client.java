package com.iakrohmts.bestmoviesapp.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rohmts on 5/20/2017.
 */

public class Client {
  public static final String BASE_URL = "http://api.themoviedb.org/3/";
  public static Retrofit sretrofit = null;

  public static Retrofit getClient() {
    if (sretrofit == null)  {
      sretrofit = new Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .build();
    }
    return sretrofit;
  }

}
