package com.iakrohmts.bestmoviesapp.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Rohmts on 5/24/2017.
 */

public class UtilTCM {
  public static boolean verifyConnection(Context context) {
    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
    if (networkInfo != null && networkInfo.isConnected()) {
      return true;
    } else {
      return false;
    }
  }
}
