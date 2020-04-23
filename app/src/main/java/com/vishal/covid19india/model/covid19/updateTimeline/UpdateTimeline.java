package com.vishal.covid19india.model.covid19.updateTimeline;

import com.google.gson.annotations.SerializedName;
import com.vishal.covid19india.model.covid19.Covid19Service;
import java.io.Serializable;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateTimeline implements Serializable {

  @SerializedName("update")
  private String update;

  @SerializedName("timestamp")
  private String timestamp;

  private String timeShow;

  public static void getUpdateTimelineList(Callback<List<UpdateTimeline>> callback) {
    Covid19Service.getInstanceCovid19().getUpdateTimeline()
        .enqueue(new Callback<List<UpdateTimeline>>() {
          @Override
          public void onResponse(@NotNull Call<List<UpdateTimeline>> call,
              @NotNull Response<List<UpdateTimeline>> response) {
            if (response.isSuccessful() && response.body() != null) {
              callback.onResponse(call, response);
            } else {
              callback.onFailure(call, new Throwable("Something went wrong"));
            }

          }

          @Override
          public void onFailure(@NotNull Call<List<UpdateTimeline>> call, @NotNull Throwable t) {
            callback.onFailure(call, t);
          }
        });
  }

  public String getTimeShow() {
    return timeShow;
  }

  public void setTimeShow(String timeShow) {
    this.timeShow = timeShow;
  }

  public String getUpdate() {
    return update;
  }

  public void setUpdate(String update) {
    this.update = update;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  @NotNull
  @Override
  public String toString() {
    return "ClassPojo [update = " + update + ", timestamp = " + timestamp + "]";
  }
}
