package com.vishal.covid19india.utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.vishal.covid19india.R;
import com.vishal.covid19india.view.activity.HomeActivity;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotificationHandler {

  private Context context;
  private String channelId = "Covid19-india";

  public NotificationHandler(Context context) {
    this.context = context;
  }

  public void sendNotification(Context context, String title, String messageBody,
      String messageContentJpgOrPng) {
    if (messageContentJpgOrPng.contains(".png")
        || messageContentJpgOrPng.contains(".jpg")) {
      new generatePictureStyleNotification(context, title, messageContentJpgOrPng)
          .execute();
    } else if (messageContentJpgOrPng.contains("http")) {
      OpenWebViewNotification(context, title, messageContentJpgOrPng);
    } else {
      TextNotification(context, title, messageBody);
    }
  }

  private void TextNotification(Context context, String title, String messageBody) {
    Intent intent = new Intent(context, HomeActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    PendingIntent pendingIntent = PendingIntent
        .getActivity(context, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);

    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,
        channelId)
        .setSmallIcon(R.drawable.ic_covid19)
        .setContentTitle(title)
        .setAutoCancel(true)
        .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
        .setContentText(messageBody)
        .setSound(defaultSoundUri)
        .setContentIntent(pendingIntent);

    NotificationManager notificationManager = (NotificationManager) context
        .getSystemService(Context.NOTIFICATION_SERVICE);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      // Channel human readable title
      NotificationChannel channel = new NotificationChannel(channelId,
          "Cloud Messaging Service",
          NotificationManager.IMPORTANCE_DEFAULT);
      if (notificationManager != null) {
        notificationManager.createNotificationChannel(channel);
      }
    }

    if (notificationManager != null) {
      notificationManager.notify(createID() /* ID of notification */, notificationBuilder.build());
    }
  }

  private int createID() {
    Date now = new Date();
    return Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.US).format(now));
  }

//  public void sendPushUpdateNotification(String type, String url, String description, String title,
//      String message, String imageUrl) {
//    SharedPreferences sharedPreferencesAds = context.getApplicationContext()
//        .getSharedPreferences(context.getPackageName(), MODE_PRIVATE); // 0 - for private mode
//    SharedPreferences.Editor editorAds = sharedPreferencesAds.edit();
//    editorAds.putString("type", type);
//    editorAds.putString("url", url);
//    editorAds.putString("description", description);
//    editorAds.putString("title", title);
//    editorAds.putString("message", message);
//    editorAds.commit();
//    new generatePictureStyleNotification(context, title, message, imageUrl).execute();
//  }

  private void OpenWebViewNotification(Context context, String title, String message) {
    Intent intent;
    final String appPackageName = context.getPackageName();
    try {
      intent = (new Intent(Intent.ACTION_VIEW, Uri.parse(message)));
    } catch (android.content.ActivityNotFoundException anfe) {
      intent = (new Intent(Intent.ACTION_VIEW,
          Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
    }
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    PendingIntent pendingIntent = PendingIntent
        .getActivity(context, 101 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT);

    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    NotificationCompat.Builder notificationBuilder;

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      notificationBuilder = new NotificationCompat.Builder(context, channelId);
      notificationBuilder.setPriority(NotificationManager.IMPORTANCE_HIGH);
      notificationBuilder.setDefaults(Notification.DEFAULT_ALL);
      notificationBuilder.setVibrate(new long[0]);
    } else {
      notificationBuilder = new NotificationCompat.Builder(context, channelId);
      notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
    }

    notificationBuilder.setSmallIcon(R.drawable.ic_covid19);
    notificationBuilder.setContentTitle(title);
    notificationBuilder.setAutoCancel(true);
    notificationBuilder.setContentText(message);
    notificationBuilder.setSound(defaultSoundUri);
    notificationBuilder.setContentIntent(pendingIntent);

    NotificationManager notificationManager = (NotificationManager) context
        .getSystemService(Context.NOTIFICATION_SERVICE);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      // Channel human readable title
      NotificationChannel channel = new NotificationChannel(channelId,
          "Cloud Messaging Service",
          NotificationManager.IMPORTANCE_DEFAULT);
      if (notificationManager != null) {
        notificationManager.createNotificationChannel(channel);
      }
    }
    if (notificationManager != null) {
      notificationManager.notify(createID(), notificationBuilder.build());
    }

  }

  public class generatePictureStyleNotification extends AsyncTask<String, Void, Bitmap> {

    private Context mContext;
    private String title, imageUrl;

    generatePictureStyleNotification(Context context, String title,
        String imageUrl) {
      super();
      this.mContext = context;
      this.title = title;
      this.imageUrl = imageUrl;
    }

    @Override
    protected Bitmap doInBackground(String... params) {

      InputStream in;
      try {
        URL url = new URL(this.imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.connect();
        in = connection.getInputStream();
        return BitmapFactory.decodeStream(in);
      } catch (IOException e) {
        e.printStackTrace();
      }
      return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onPostExecute(Bitmap result) {
      super.onPostExecute(result);
      Intent intent = new Intent(mContext, HomeActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      PendingIntent pendingIntent = PendingIntent
          .getActivity(mContext, 100, intent, PendingIntent.FLAG_ONE_SHOT);
      Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
      NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,
          channelId);
      notificationBuilder.setContentIntent(pendingIntent);
      notificationBuilder.setContentTitle(title);
      notificationBuilder.setSmallIcon(R.drawable.ic_covid19);
      notificationBuilder.setLargeIcon(result);
      notificationBuilder.setSound(defaultSoundUri);
      notificationBuilder.setAutoCancel(true);
      NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle()
          .bigPicture(result);
      notificationBuilder.setStyle(bigPictureStyle);
      notificationBuilder.build();
      NotificationManager notificationManager = (NotificationManager) context
          .getSystemService(Context.NOTIFICATION_SERVICE);
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Channel human readable title
        NotificationChannel channel = new NotificationChannel(channelId,
            "Cloud Messaging Service",
            NotificationManager.IMPORTANCE_DEFAULT);
        if (notificationManager != null) {
          notificationManager.createNotificationChannel(channel);
        }
      }
      if (notificationManager != null) {
        notificationManager
            .notify(createID() /* ID of notification */, notificationBuilder.build());
      }
    }
  }

}
