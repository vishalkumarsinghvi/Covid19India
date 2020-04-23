package com.vishal.covid19india.model.covid19.testedData;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class States_tested_data implements Serializable {


  @SerializedName("totalpeopleinquarantine")
  private String totalpeopleinquarantine;
  @SerializedName("unconfirmed")
  private String unconfirmed;
  @SerializedName("testsperthousand")
  private String testsperthousand;
  @SerializedName("numisolationbeds")
  private String numisolationbeds;
  @SerializedName("positive")
  private String positive;
  @SerializedName("source")
  private String source;
  @SerializedName("source2")
  private String source2;
  @SerializedName("updatedon")
  private String updatedon;
  @SerializedName("positiveratebytests")
  private String positiveratebytests;
  @SerializedName("totaltested")
  private String totaltested;
  @SerializedName("negative")
  private String negative;
  @SerializedName("numventilators")
  private String numventilators;
  @SerializedName("numicubeds")
  private String numicubeds;
  @SerializedName("totalpeoplereleasedfromquarantine")
  private String totalpeoplereleasedfromquarantine;
  @SerializedName("numcallsstatehelpline")
  private String numcallsstatehelpline;
  @SerializedName("state")
  private String state;

  public States_tested_data() {
  }

  public States_tested_data(String state) {
    this.state = state;
  }

  public String getPositive() {
    return positive;
  }

  public String getSource() {
    return source;
  }

  public String getUpdatedon() {
    return updatedon;
  }

  public String getPositiveratebytests() {
    return positiveratebytests;
  }

  public String getTotaltested() {
    return totaltested;
  }

  public String getNegative() {
    return negative;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }
}
