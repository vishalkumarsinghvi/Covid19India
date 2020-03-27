package com.vishal.covid19india.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.util.Objects;

public class NetworkUtils {

  public static boolean isNetworkAvailable(Context context) {
    ConnectivityManager connectivityManager = (ConnectivityManager) context
        .getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connectivityManager != null) {
      //we are connected to a network
      return Objects
          .requireNonNull(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE))
          .getState()
          == NetworkInfo.State.CONNECTED ||
          Objects.requireNonNull(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI))
              .getState()
              == NetworkInfo.State.CONNECTED;
    }
    return false;
  }
}
