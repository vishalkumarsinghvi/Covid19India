package com.vishal.covid19india.utils;

import android.app.Application;
import com.vishal.covid19india.R;
import com.vishal.covid19india.room.Covid19IndiaDatabase;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class Covid19IndiaApplication extends Application {

  public static Covid19IndiaDatabase db;

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
    db = Covid19IndiaDatabase.getINSTANCE(getApplicationContext());
  }

}
