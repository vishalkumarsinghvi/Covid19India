package com.vishal.covid19india.model.covid19.precaution;

import com.google.gson.annotations.SerializedName;
import com.vishal.covid19india.model.covid19.Covid19Service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Precautions implements Serializable {

  @SerializedName("data")
  private ArrayList<String> data;

  @SerializedName("type")
  private String type;

  @SerializedName("title")
  private String title;

  @SerializedName("dataTitle")
  private ArrayList<String> dataTitle;

  public static void getPrecautionsList(Callback<List<Precautions>> callback) {
    Covid19Service.getInstanceGithubCovid19().getPrecautionsData()
        .enqueue(new Callback<List<Precautions>>() {
          @Override
          public void onResponse(@NotNull Call<List<Precautions>> call,
              @NotNull Response<List<Precautions>> response) {
            if (response.isSuccessful() && response.body() != null) {
              callback.onResponse(call, response);
            } else {
              callback.onFailure(call, new Throwable("Something went wrong"));
            }

          }

          @Override
          public void onFailure(@NotNull Call<List<Precautions>> call, @NotNull Throwable t) {
            callback.onFailure(call, t);
          }
        });
  }

  public ArrayList<String> getDataTitle() {
    return dataTitle;
  }

  public ArrayList<String> getData() {
    return data;
  }

  public String getType() {
    return type;
  }

  public String getTitle() {
    return title;
  }

  @NotNull
  @Override
  public String toString() {
    return "ClassPojo [data = " + data + ", type = " + type + ", dataTitle = " + dataTitle
        + ", title = " + title + "]";
  }
}

