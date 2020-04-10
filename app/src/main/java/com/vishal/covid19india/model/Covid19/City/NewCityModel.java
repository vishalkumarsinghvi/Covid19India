package com.vishal.covid19india.model.Covid19.City;

import com.google.gson.annotations.SerializedName;
import com.vishal.covid19india.model.Covid19.Covid19Service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewCityModel implements Serializable {

  @SerializedName("districtData")
  private ArrayList<DistrictData> districtData;

  @SerializedName("state")
  private String state;

  public static void getNewCityDistrictWise(Callback<List<NewCityModel>> callback) {
    Covid19Service.getInstanceCovid19().getNewCityDistrictWise()
        .enqueue(new Callback<List<NewCityModel>>() {
          @Override
          public void onResponse(@NotNull Call<List<NewCityModel>> call,
              @NotNull Response<List<NewCityModel>> response) {
            if (response.isSuccessful() && response.body() != null) {
              callback.onResponse(call, response);
            } else {
              callback.onFailure(call, new Throwable("Something went wrong"));
            }

          }

          @Override
          public void onFailure(@NotNull Call<List<NewCityModel>> call, @NotNull Throwable t) {
            callback.onFailure(call, t);
          }
        });
  }

  public ArrayList<DistrictData> getDistrictData() {
    return districtData;
  }

  public void setDistrictData(
      ArrayList<DistrictData> districtData) {
    this.districtData = districtData;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }
}
