package com.vishal.covid19india.model.covid19.factoids;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

public class Factoids implements Serializable {

  @SerializedName("numberoftimes")
  private String numberoftimes;

  @SerializedName("banner")
  private String banner;

  @SerializedName("id")
  private String id;

  public String getNumberoftimes() {
    return numberoftimes;
  }

  public String getBanner() {
    return banner;
  }

  public String getId() {
    return id;
  }

  @NotNull
  @Override
  public String toString() {
    return "ClassPojo [numberoftimes = " + numberoftimes + ", banner = " + banner + ", id = " + id
        + "]";
  }
}
