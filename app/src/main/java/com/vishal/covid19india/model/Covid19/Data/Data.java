package com.vishal.covid19india.model.Covid19.Data;

import com.vishal.covid19india.model.Covid19.Covid19Service;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Data implements Serializable {
  @SerializedName("cases_time_series")
  private ArrayList<Cases_time_series> cases_time_series;

  @SerializedName("tested")
  private ArrayList<Tested>  tested;

  @SerializedName("key_values")
  private ArrayList<Key_values> key_values;

  @SerializedName("statewise")
  private ArrayList<Statewise> statewise;

  public ArrayList<Cases_time_series> getCases_time_series() {
    return cases_time_series;
  }

  public ArrayList<Tested> getTested() {
    return tested;
  }

  public ArrayList<Key_values> getKey_values() {
    return key_values;
  }

  public ArrayList<Statewise> getStatewise() {
    return statewise;
  }

  public static void getData(Callback<Data> callback) {
    Covid19Service.getInstanceCovid19().getData().enqueue(new Callback<Data>() {
      @Override
      public void onResponse(@NotNull Call<Data> call, @NotNull Response<Data> response) {
        if (response.isSuccessful() && response.body() != null) {
          callback.onResponse(call, response);
        } else {
          callback.onFailure(call, new Throwable("Something went wrong"));
        }

      }

      @Override
      public void onFailure(@NotNull Call<Data> call, @NotNull Throwable t) {
        callback.onFailure(call, t);
      }
    });
  }

  @NotNull
  @Override
  public String toString()
  {
    return "ClassPojo [cases_time_series = "+cases_time_series+", tested = "+tested+", key_values = "+key_values+", statewise = "+statewise+"]";
  }
}
