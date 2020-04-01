package com.vishal.covid19india.utils;

import static com.google.firebase.crashlytics.internal.Logger.TAG;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import androidx.multidex.MultiDex;
import com.google.firebase.iid.FirebaseInstanceId;
import com.vishal.covid19india.R;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class Covid19IndiaApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    ViewPump.init(ViewPump.builder()
        .addInterceptor(new CalligraphyInterceptor(
            new CalligraphyConfig.Builder()
                .setDefaultFontPath("../assets/fonts/roboto-regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()))
        .build());

    FirebaseInstanceId.getInstance().getInstanceId()
        .addOnCompleteListener(task -> {
          if (!task.isSuccessful()) {
            Log.w(TAG, "getInstanceId failed", task.getException());
            return;
          }

          // Get new Instance ID token
          String token = task.getResult().getToken();

          // Log and toast
          String msg = getString(R.string.msg_token_fmt, token);
          Log.d(TAG, msg);
        });
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(base);
  }
}
