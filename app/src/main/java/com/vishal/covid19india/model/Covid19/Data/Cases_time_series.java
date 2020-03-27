package com.vishal.covid19india.model.Covid19.Data;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

public class Cases_time_series implements Serializable {

  @SerializedName("date")
  private String date;

  @SerializedName("dailyrecovered")
  private String dailyrecovered;

  @SerializedName("totalconfirmed")
  private String totalconfirmed;

  @SerializedName("totaldeceased")
  private String totaldeceased;

  @SerializedName("dailydeceased")
  private String dailydeceased;

  @SerializedName("totalrecovered")
  private String totalrecovered;

  @SerializedName("dailyconfirmed")
  private String dailyconfirmed;

  public String getDate() {
    return date;
  }

  public String getDailyrecovered() {
    return dailyrecovered;
  }

  public String getTotalconfirmed() {
    return totalconfirmed;
  }

  public String getTotaldeceased() {
    return totaldeceased;
  }

  public String getDailydeceased() {
    return dailydeceased;
  }

  public String getTotalrecovered() {
    return totalrecovered;
  }

  public String getDailyconfirmed() {
    return dailyconfirmed;
  }

  @NotNull
  @Override
  public String toString() {
    return "ClassPojo [date = " + date + ", dailyrecovered = " + dailyrecovered
        + ", totalconfirmed = " + totalconfirmed + ", totaldeceased = " + totaldeceased
        + ", dailydeceased = " + dailydeceased + ", totalrecovered = " + totalrecovered
        + ", dailyconfirmed = " + dailyconfirmed + "]";
  }
}
