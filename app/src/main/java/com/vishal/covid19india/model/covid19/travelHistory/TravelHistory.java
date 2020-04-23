package com.vishal.covid19india.model.covid19.travelHistory;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;

public class TravelHistory implements Serializable {

  @SerializedName("travel_history")
  private ArrayList<Travel_history> travel_history;

  public ArrayList<Travel_history> getTravel_history() {
    return travel_history;
  }

  @NotNull
  @Override
  public String toString() {
    return "ClassPojo [travel_history = " + travel_history + "]";
  }
}
