package com.vishal.covid19india.model.Covid19;

import com.google.gson.JsonObject;
import com.vishal.covid19india.model.Covid19.City.NewCityModel;
import com.vishal.covid19india.model.Covid19.Data.Data;
import com.vishal.covid19india.model.Covid19.Factoids.FactoidsModel;
import com.vishal.covid19india.model.Covid19.RawData.RawData;
import com.vishal.covid19india.model.Covid19.TestedData.TestedData;
import com.vishal.covid19india.model.Covid19.TravelHistory.TravelHistory;
import com.vishal.covid19india.model.Covid19.UpdateTimeline.UpdateTimeline;
import com.vishal.covid19india.retrofit.RetrofitClientInstance;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public class Covid19Service {

  private static final Covid19Api covid19Api = RetrofitClientInstance.getRetrofitCovid19()
      .create(Covid19Api.class);

  public Covid19Service() {
  }

  public static Covid19Api getInstanceCovid19() {
    return covid19Api;
  }

  public interface Covid19Api {

    @GET("raw_data.json")
    Call<RawData> getRawData();

    @GET("travel_history.json")
    Call<TravelHistory> getTravelHistory();

    @GET("data.json")
    Call<Data> getData();

    @GET("state_district_wise.json")
    Call<JsonObject> getStateDistrictWise();

    @GET("v2/state_district_wise.json")
    Call<List<NewCityModel>> getNewCityDistrictWise();

    @GET("website_data.json")
    Call<FactoidsModel> getFactoidsList();

    @GET("updatelog/log.json")
    Call<List<UpdateTimeline>> getUpdateTimeline();

    @GET("state_test_data.json")
    Call<TestedData> getTestedData();
  }

}
