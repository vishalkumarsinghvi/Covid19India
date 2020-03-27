package com.vishal.covid19india.model.Covid19.Factoids;

import com.vishal.covid19india.model.Covid19.Covid19Service;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FactoidsModel {

  private ArrayList<Factoids> factoids;

  public static void getFactoidsList(Callback<FactoidsModel> callback) {
    Covid19Service.getInstanceCovid19().getFactoidsList().enqueue(new Callback<FactoidsModel>() {
      @Override
      public void onResponse(@NotNull Call<FactoidsModel> call,
          @NotNull Response<FactoidsModel> response) {
        if (response.isSuccessful() && response.body() != null) {
          callback.onResponse(call, response);
        } else {
          callback.onFailure(call, new Throwable("Something went wrong"));
        }

      }

      @Override
      public void onFailure(@NotNull Call<FactoidsModel> call, @NotNull Throwable t) {
        callback.onFailure(call, t);
      }
    });
  }

  public ArrayList<Factoids> getFactoids() {
    return factoids;
  }

  public void setFactoids(ArrayList<Factoids> factoids) {
    this.factoids = factoids;
  }

  @NotNull
  @Override
  public String toString() {
    return "ClassPojo [factoids = " + factoids + "]";
  }
}
