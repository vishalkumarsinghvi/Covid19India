package com.vishal.covid19india.services;

import static com.vishal.covid19india.constants.AppConstants.REGISTRATION_ID;
import static com.vishal.covid19india.constants.AppConstants.SENT_TOKEN_TO_SERVER;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.vishal.covid19india.utils.NotificationHandler;
import org.jetbrains.annotations.NotNull;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

  private static final String TAG = "MyFirebaseMsgService";

  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    Log.d(TAG, "From: " + remoteMessage.getFrom());

    if (remoteMessage.getNotification() != null) {
      Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
      String title = remoteMessage.getNotification().getTitle();
      String messageBody = remoteMessage.getNotification().getBody();
      String link = remoteMessage.getNotification().getBody();
      NotificationHandler notificationHandler = new NotificationHandler(this);
      if (messageBody != null) {
        if (messageBody.contains("http")) {
          notificationHandler.sendNotification(this, title, messageBody,
              link);
        } else {
          notificationHandler.sendNotification(this, title, messageBody, "");
        }
      }
    } else if (remoteMessage.getData().size() > 0) {
      Log.d(TAG, "Message Notification Body: " + remoteMessage.getData().get("messageBody"));
      String title = remoteMessage.getData().get("title");
      String messageBody = remoteMessage.getData().get("messageBody");
      String link = remoteMessage.getData().get("link");
      NotificationHandler notificationHandler = new NotificationHandler(this);
      if (link != null && link.contains("http")) {
        notificationHandler.sendNotification(this, title, messageBody,
            link);
      } else {
        notificationHandler.sendNotification(this, title, messageBody, "");
      }
    }
  }

  @Override
  public void onNewToken(@NotNull String token) {
    Log.d(TAG, "Refreshed token: " + token);
    checkTokenRefresh(token);
  }

  public void checkTokenRefresh(String token) {
    SharedPreferences sharedPreferences = getApplicationContext()
        .getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
    if (!sharedPreferences.getBoolean(SENT_TOKEN_TO_SERVER, false)) {
      Log.d(TAG, "Refreshed token: " + token);
      SharedPreferences.Editor editor = sharedPreferences.edit();
      editor.putString(REGISTRATION_ID, token);
      editor.commit();
      sendRegistrationToServer(token);
//      } catch (JsonProcessingException | ApiException e) {
//        sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, false).apply();
//      }
    }
  }

  private void sendRegistrationToServer(String token) {
    final SharedPreferences sharedPreferences = getApplicationContext()
        .getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
//    String userId = sharedPreferences.getString(getResources().getString(R.string.user_id), "");
//    UserController.getInstance(this).updateNotificationToken(userId, token, new APICallBack<User>() {
//      @Override
//      public void onSuccess(HttpContext context, User response) {
//        sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, true).apply();
//      }
//      @Override
//      public void onFailure(HttpContext context, Throwable error) {
//        sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, false).apply();
//      }
//    });
  }

}
