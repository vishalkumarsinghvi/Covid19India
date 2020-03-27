package com.android.covid19india.retrofit;

import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

  private static final String base_url = "https://api.covid19india.org/";
  private static Retrofit retrofitMovies, retrofitRadio, retrofitCovid19;

  public static Retrofit getRetrofitCovid19() {
    if (retrofitCovid19 == null) {
      retrofitCovid19 = new Builder()
          .baseUrl(base_url)
          .addConverterFactory(GsonConverterFactory.create()).build();
    }
    return retrofitCovid19;
  }
}
