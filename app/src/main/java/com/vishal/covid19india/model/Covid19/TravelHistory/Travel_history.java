package com.vishal.covid19india.model.Covid19.TravelHistory;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

public class Travel_history implements Serializable {

  @SerializedName("timefrom")
  private String timefrom;

  @SerializedName("address")
  private String address;

  @SerializedName("timeto")
  private String timeto;

  @SerializedName("modeoftravel")
  private String modeoftravel;

  @SerializedName("accuracylocation")
  private String accuracylocation;

  @SerializedName("datasource")
  private String datasource;

  @SerializedName("latlong")
  private String latlong;

  @SerializedName("_cn6ca")
  private String _cn6ca;

  @SerializedName("pid")
  private String pid;

  @SerializedName("placename")
  private String placename;

  @SerializedName("type")
  private String type;

  public String getTimefrom() {
    return timefrom;
  }

  public String getAddress() {
    return address;
  }

  public String getTimeto() {
    return timeto;
  }

  public String getModeoftravel() {
    return modeoftravel;
  }

  public String getAccuracylocation() {
    return accuracylocation;
  }

  public String getDatasource() {
    return datasource;
  }

  public String getLatlong() {
    return latlong;
  }

  public String get_cn6ca() {
    return _cn6ca;
  }

  public String getPid() {
    return pid;
  }

  public String getPlacename() {
    return placename;
  }

  public String getType() {
    return type;
  }

  @NotNull
  @Override
  public String toString() {
    return "ClassPojo [timefrom = " + timefrom + ", address = " + address + ", timeto = " + timeto
        + ", modeoftravel = " + modeoftravel + ", accuracylocation = " + accuracylocation
        + ", datasource = " + datasource + ", latlong = " + latlong + ", _cn6ca = " + _cn6ca
        + ", pid = " + pid + ", placename = " + placename + ", type = " + type + "]";
  }
}
