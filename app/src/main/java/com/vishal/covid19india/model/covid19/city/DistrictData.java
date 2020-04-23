package com.vishal.covid19india.model.covid19.city;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

public class DistrictData implements Serializable {

  @SerializedName("district")
  private String district;

  @SerializedName("delta")
  private Delta delta;

  @SerializedName("confirmed")
  private String confirmed;

  @SerializedName("lastupdatedtime")
  private String lastupdatedtime;

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public Delta getDelta() {
    return delta;
  }

  public void setDelta(Delta delta) {
    this.delta = delta;
  }

  public String getConfirmed() {
    return confirmed;
  }

  public void setConfirmed(String confirmed) {
    this.confirmed = confirmed;
  }

  public String getLastupdatedtime() {
    return lastupdatedtime;
  }

  public void setLastupdatedtime(String lastupdatedtime) {
    this.lastupdatedtime = lastupdatedtime;
  }

  @NotNull
  @Override
  public String toString() {
    return "ClassPojo [district = " + district + ", delta = " + delta + ", confirmed = " + confirmed
        + ", lastupdatedtime = " + lastupdatedtime + "]";
  }
}
