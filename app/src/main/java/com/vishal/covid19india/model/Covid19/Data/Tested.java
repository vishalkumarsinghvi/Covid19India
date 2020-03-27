package com.vishal.covid19india.model.Covid19.Data;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

public class Tested implements Serializable {

  @SerializedName("totalsamplestested")
  private String totalsamplestested;

  @SerializedName("source")
  private String source;

  @SerializedName("updatetimestamp")
  private String updatetimestamp;

  @SerializedName("totalindividualstested")
  private String totalindividualstested;

  @SerializedName("totalpositivecases")
  private String totalpositivecases;

  public String getTotalsamplestested() {
    return totalsamplestested;
  }

  public String getSource() {
    return source;
  }

  public String getUpdatetimestamp() {
    return updatetimestamp;
  }

  public String getTotalindividualstested() {
    return totalindividualstested;
  }

  public String getTotalpositivecases() {
    return totalpositivecases;
  }

  @NotNull
  @Override
  public String toString() {
    return "ClassPojo [totalsamplestested = " + totalsamplestested + ", source = " + source
        + ", updatetimestamp = " + updatetimestamp + ", totalindividualstested = "
        + totalindividualstested + ", totalpositivecases = " + totalpositivecases + "]";
  }
}