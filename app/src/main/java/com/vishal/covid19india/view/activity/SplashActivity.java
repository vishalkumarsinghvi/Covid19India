package com.vishal.covid19india.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.vishal.covid19india.R;
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
  }

  private void setData() {
    Glide.with(this)
        .load(R.drawable.logo).into(ivLogo);
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
