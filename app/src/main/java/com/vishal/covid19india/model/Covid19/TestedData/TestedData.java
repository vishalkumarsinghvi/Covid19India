package com.vishal.covid19india.model.Covid19.TestedData;

import com.google.gson.annotations.SerializedName;
import com.vishal.covid19india.model.Covid19.Covid19Service;
import java.io.Serializable;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestedData implements Serializable {

  @SerializedName("states_tested_data")
  private ArrayList<States_tested_data> states_tested_data;

  public static void getTestedData(Callback<TestedData> callback) {
    Covid19Service.getInstanceCovid19().getTestedData().enqueue(new Callback<TestedData>() {
      @Override
      public void onResponse(@NotNull Call<TestedData> call,
          @NotNull Response<TestedData> response) {
        if (response.isSuccessful() && response.body() != null) {
          callback.onResponse(call, response);
        } else {
          callback.onFailure(call, new Throwable("Something went wrong"));
        }

      }

      @Override
      public void onFailure(@NotNull Call<TestedData> call, @NotNull Throwable t) {
        callback.onFailure(call, t);
      }
    });
  }

  public ArrayList<States_tested_data> getStates_tested_data() {
    return states_tested_data;
  }

  public void setStates_tested_data(
      ArrayList<States_tested_data> states_tested_data) {
    this.states_tested_data = states_tested_data;
  }

}
