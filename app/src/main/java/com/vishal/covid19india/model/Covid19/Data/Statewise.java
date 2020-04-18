package com.vishal.covid19india.model.Covid19.Data;

import com.google.gson.annotations.SerializedName;
import com.vishal.covid19india.model.Covid19.City.DistrictData;
import java.io.Serializable;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;

public class Statewise implements Serializable {

  public boolean isVisible = true;
  public boolean isCityClicked = true;
  public boolean isConfirmedClicked = true;

  @SerializedName("deltarecovered")
  private String deltarecovered = "";

  @SerializedName("deltadeaths")
  private String deltadeaths = "";

  @SerializedName("deltaconfirmed")
  private String deltaconfirmed = "";

  @SerializedName("statecode")
  private String statecode = "";

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

  private ArrayList<DistrictData> districtDataList;

  public ArrayList<DistrictData> getDistrictDataList() {
    return districtDataList;
  }

  public void setDistrictDataList(
      ArrayList<DistrictData> districtDataList) {
    this.districtDataList = districtDataList;
  }

  public String getDeltarecovered() {
    return "Recovered\n" + "[+" + deltarecovered + "]\n" + recovered;
  }

  public String getDeltadeaths() {
    return "Deceased\n" + "[+" + deltadeaths + "]\n" + deaths;
  }

  public String getDeltaconfirmed() {
    return "Confirmed\n" + "[+" + deltaconfirmed + "]\n" + confirmed;
  }

  public String getDeltaActive() {
    return "Active\n\n" + active;
  }

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
