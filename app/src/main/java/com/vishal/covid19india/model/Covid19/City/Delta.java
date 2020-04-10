package com.vishal.covid19india.model.Covid19.City;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

class Delta implements Serializable {

  @SerializedName("confirmed")
  private String confirmed;

  public String getConfirmed() {
    return confirmed;
  }

  public void setConfirmed(String confirmed) {
    this.confirmed = confirmed;
  }

  @NotNull
  @Override
  public String toString() {
    return "ClassPojo [confirmed = " + confirmed + "]";
  }
}
