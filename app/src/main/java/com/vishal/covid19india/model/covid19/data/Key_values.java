package com.vishal.covid19india.model.covid19.data;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

public class Key_values implements Serializable
{
  @SerializedName("confirmeddelta")
  private String confirmeddelta;

  @SerializedName("recovereddelta")
  private String recovereddelta;

  @SerializedName("deceaseddelta")
  private String deceaseddelta;

  @SerializedName("statesdelta")
  private String statesdelta;

  @SerializedName("lastupdatedtime")
  private String lastupdatedtime;

  public String getConfirmeddelta() {
    return confirmeddelta;
  }

  public String getRecovereddelta() {
    return recovereddelta;
  }

  public String getDeceaseddelta() {
    return deceaseddelta;
  }

  public String getStatesdelta() {
    return statesdelta;
  }

  public String getLastupdatedtime() {
    return lastupdatedtime;
  }

  @NotNull
  @Override
  public String toString()
  {
    return "ClassPojo [confirmeddelta = "+confirmeddelta+", recovereddelta = "+recovereddelta+", deceaseddelta = "+deceaseddelta+", statesdelta = "+statesdelta+", lastupdatedtime = "+lastupdatedtime+"]";
  }
}
