package com.vishal.covid19india.utils;

import static com.vishal.covid19india.constants.AppConstants.FIRST_TIME_APP_OPEN;
import static com.vishal.covid19india.constants.AppConstants.REGISTRATION_ID;
import static com.vishal.covid19india.constants.AppConstants.breakingNewsTopic;
import static com.vishal.covid19india.constants.AppConstants.subscribeToTopic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import androidx.appcompat.app.AlertDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.vishal.covid19india.BuildConfig;
import java.util.HashMap;

public class AppController {

  private static final String UPDATE_URL = "latest_store_url";
  private static final String FORCE_UPDATE_APP = "force_update_app";
  private static final String VERSION_CODE_KEY = "latest_app_version";
  private static String TAG = "AppController";
  private static long cacheExpiration = 3600;
  private Context context;
  private FirebaseRemoteConfig mFirebaseRemoteConfig;

  public AppController(Context context) {
    this.context = context;
  }


  @SuppressLint("HardwareIds")
  public void sendTokenToFirebase() {
    SharedPreferences sharedPreferences = context.getApplicationContext()
        .getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("token");
    myRef.child(Settings.Secure.getString(context.getContentResolver(),
        Settings.Secure.ANDROID_ID))
        .setValue(sharedPreferences.getString(REGISTRATION_ID, ""));
//    subScribeToChannel();
  }

  void firstTimeAppOpen() {
    SharedPreferences sharedPreferences = context.getApplicationContext()
        .getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean(FIRST_TIME_APP_OPEN, true);
    editor.commit();
  }

  boolean getFirstTimeAppOpen() {
    SharedPreferences sharedPreferences = context.getApplicationContext()
        .getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    return sharedPreferences.getBoolean(FIRST_TIME_APP_OPEN, false);
  }

  public void subScribeToChannel() {
    SharedPreferences sharedPreferences = context.getApplicationContext()
        .getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean(subscribeToTopic, true);
    editor.commit();
    FirebaseMessaging.getInstance().subscribeToTopic(breakingNewsTopic);
  }

  public void unSubScribeToChannel() {
    SharedPreferences sharedPreferences = context.getApplicationContext()
        .getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean(subscribeToTopic, false);
    editor.commit();
    FirebaseMessaging.getInstance().unsubscribeFromTopic(breakingNewsTopic);
  }

  public void firebaseUpdateInit() {
    mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
    HashMap<String, Object> firebaseDefaultMap = new HashMap<>();
    firebaseDefaultMap.put(VERSION_CODE_KEY, getCurrentVersionCode());
    firebaseDefaultMap.put(FORCE_UPDATE_APP, false);
    firebaseDefaultMap
        .put(UPDATE_URL, "http://www.mediafire.com/file/xxojb4r9h4cu176/Covid19India.apk/file");

    mFirebaseRemoteConfig.setDefaultsAsync(firebaseDefaultMap);
    FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
        .setMinimumFetchIntervalInSeconds(cacheExpiration)
        .setDeveloperModeEnabled(BuildConfig.DEBUG)
        .build();
    mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
    if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
      cacheExpiration = 0;
    }
    mFirebaseRemoteConfig.fetchAndActivate()
        .addOnCompleteListener((Activity) context, task -> {
          if (task.isSuccessful() && task.getResult() != null) {
            boolean updated = task.getResult();
            Log.d(TAG, "Config params updated: " + updated);
            Log.d(TAG, "Fetch and activate succeeded");
            Log.d(TAG, "Fetched value: " + mFirebaseRemoteConfig.getString(VERSION_CODE_KEY));
            Log.d(TAG, "Fetched url value: " + mFirebaseRemoteConfig.getString(UPDATE_URL));
            Log.d(TAG, "Fetched force update app value: " + mFirebaseRemoteConfig
                .getString(FORCE_UPDATE_APP));
            checkForUpdate();

          } else {
            Log.d(TAG, "Fetch failed");
          }
        });

    Log.d(TAG, "Default value: " + mFirebaseRemoteConfig.getString(VERSION_CODE_KEY));
    Log.d(TAG, "Default value Url: " + mFirebaseRemoteConfig.getString(UPDATE_URL));

  }

  private int getCurrentVersionCode() {
    try {
      return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
    } catch (NameNotFoundException e) {
      e.printStackTrace();
    }
    return -1;
  }

  private void checkForUpdate() {
    int latestAppVersion = (int) mFirebaseRemoteConfig.getDouble(VERSION_CODE_KEY);
    String latestAppUpdateUrl = mFirebaseRemoteConfig.getString(UPDATE_URL);
    boolean forceUpdate = mFirebaseRemoteConfig.getBoolean(FORCE_UPDATE_APP);
    if (latestAppVersion > getCurrentVersionCode()) {
      new AlertDialog.Builder(context).setTitle("Please Update the App")
          .setMessage("A new version of this app is available. Please update it").setPositiveButton(
          "OK", (dialog, which) -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(latestAppUpdateUrl));
            context.startActivity(i);
          }).setCancelable(!forceUpdate).show();
    } else {
      Log.d(TAG, "This app is already upto date");
    }
  }
}