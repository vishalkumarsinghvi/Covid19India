package com.vishal.covid19india.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import com.vishal.covid19india.R;
import com.bumptech.glide.Glide;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class SplashActivity extends AppCompatActivity {

  private ProgressBar progressBar;
  private ImageView ivLogo;
  private int progressStatus = 0;
  private Handler handler = new Handler();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    init();
    setData();
  }

  private void init() {
    ivLogo = findViewById(R.id.iv_logo);
    progressBar = findViewById(R.id.progressBar);
    getSharedPreferences("covid19india", MODE_PRIVATE).edit().putBoolean("isRunInBackground",false).apply();
  }

  private void setData() {
    Glide.with(this)
        .load(R.mipmap.ic_launcher).into(ivLogo);
    new Thread(() -> {
      setProgressStatus();
      openMainActivity();
    }).start();
  }

  private void setProgressStatus() {
    while (progressStatus < 100) {
      progressStatus++;
      handler.post(() -> progressBar.setProgress(progressStatus));
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private void openMainActivity() {
    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
    startActivity(intent);
    finish();
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
  }
}
