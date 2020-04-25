package com.vishal.covid19india.utils;

import static com.google.firebase.crashlytics.internal.Logger.TAG;
import static com.vishal.covid19india.constants.AppConstants.covid19india;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import androidx.multidex.MultiDex;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.vishal.covid19india.R;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import java.util.Objects;

public class Covid19IndiaApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    ViewPump.init(ViewPump.builder()
        .addInterceptor(new CalligraphyInterceptor(
            new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/roboto-medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .setFontMapper(font -> font)
                .build()))
        .build());

    FirebaseInstanceId.getInstance().getInstanceId()
        .addOnCompleteListener(task -> {
          if (!task.isSuccessful()) {
            Log.w(TAG, "getInstanceId failed", task.getException());
            return;
          }

          // Get new Instance ID token
          String token = Objects.requireNonNull(task.getResult()).getToken();

          // Log and toast
//          String msg = getString(R.string.msg_token_fmt, token);
          Log.d(TAG, token);
        });
    FirebaseMessaging.getInstance().subscribeToTopic(covid19india);
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(base);
  }
}
