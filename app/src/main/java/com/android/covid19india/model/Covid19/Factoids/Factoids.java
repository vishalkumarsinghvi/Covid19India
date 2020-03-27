package com.android.covid19india.model.Covid19.Factoids;

import org.jetbrains.annotations.NotNull;

public class Factoids {

  private String numberoftimes;

  private String banner;

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
