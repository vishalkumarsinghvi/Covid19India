package com.vishal.covid19india.model.Covid19.RawData;

import com.vishal.covid19india.model.Covid19.Covid19Service;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RawData implements Serializable {

  @SerializedName("raw_data")
  private ArrayList<Raw_data> raw_data;

  public static void getRawData(Callback<RawData> callback) {
    Covid19Service.getInstanceCovid19().getRawData().enqueue(new Callback<RawData>() {
      @Override
      public void onResponse(@NotNull Call<RawData> call, @NotNull Response<RawData> response) {
        if (response.isSuccessful() && response.body() != null) {
          callback.onResponse(call, response);
        } else {
          callback.onFailure(call, new Throwable("Something went wrong"));
        }

      }

      @Override
      public void onFailure(@NotNull Call<RawData> call, @NotNull Throwable t) {
        callback.onFailure(call, t);
      }
    });
  }

  public ArrayList<Raw_data> getRaw_data() {
    return raw_data;
  }

  @NotNull
  @Override
  public String toString() {
    return "ClassPojo [raw_data = " + raw_data + "]";
  }
}
