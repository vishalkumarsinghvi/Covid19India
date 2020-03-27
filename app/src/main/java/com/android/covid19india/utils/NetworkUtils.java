package com.android.covid19india.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkUtils {

  public static boolean isNetworkAvailable(Context context) {
    ConnectivityManager connectivityManager = ((ConnectivityManager) context
        .getSystemService(Context.CONNECTIVITY_SERVICE));
    if (connectivityManager != null) {
      return connectivityManager.getActiveNetworkInfo() != null && connectivityManager
          .getActiveNetworkInfo().isConnected();
    }
    return false;
  }
}
