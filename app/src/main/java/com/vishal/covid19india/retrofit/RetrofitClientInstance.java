package com.vishal.covid19india.retrofit;

import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

  private static final String base_url = "https://api.covid19india.org/";
  private static final String github_base_url = "https://raw.githubusercontent.com/vishalkumarsinghvi/vishalkumarsinghvi.github.io/master/";
  private static Retrofit retrofitGithub, retrofitCovid19;

  public static Retrofit getRetrofitCovid19() {
    if (retrofitCovid19 == null) {
      retrofitCovid19 = new Builder()
          .baseUrl(base_url)
          .addConverterFactory(GsonConverterFactory.create()).build();
    }
    return retrofitCovid19;
  }

  public static Retrofit getRetrofitGithubCovid19() {
    if (retrofitGithub == null) {
      retrofitGithub = new Builder()
          .baseUrl(github_base_url)
          .addConverterFactory(GsonConverterFactory.create()).build();
    }
    return retrofitGithub;
  }
}
