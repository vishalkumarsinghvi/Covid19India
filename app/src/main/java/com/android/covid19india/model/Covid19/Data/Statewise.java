package com.android.covid19india.model.Covid19.Data;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

public class Statewise implements Serializable {

  @SerializedName("recovered")
  private String recovered = "";

  @SerializedName("active")
  private String active = "";

  @SerializedName("state")
  private String state = "";

  @SerializedName("confirmed")
  private String confirmed = "";

  @SerializedName("deaths")
  private String deaths = "";

  @SerializedName("lastupdatedtime")
  private String lastupdatedtime = "";

  public String getRecovered() {
    return recovered;
  }

  public String getActive() {
    return active;
  }

  public String getState() {
    return state;
  }

  public String getConfirmed() {
    return confirmed;
  }

  public String getDeaths() {
    return deaths;
  }

  public String getLastupdatedtime() {
    return lastupdatedtime;
  }

  @NotNull
  @Override
  public String toString() {
    return "ClassPojo [recovered = " + recovered + ", active = " + active + ", state = " + state
        + ", confirmed = " + confirmed + ", deaths = " + deaths + ", lastupdatedtime = "
        + lastupdatedtime + "]";
  }
}
