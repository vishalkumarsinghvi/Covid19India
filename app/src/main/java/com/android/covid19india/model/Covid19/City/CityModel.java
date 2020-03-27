package com.android.covid19india.model.Covid19.City;

import com.android.covid19india.model.Covid19.Data.Statewise;

public class CityModel {

  private String state;
  private String district;
  private Statewise cityWiseData;

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public Statewise getCityWiseData() {
    return cityWiseData;
  }

  public void setCityWiseData(Statewise cityWiseData) {
    this.cityWiseData = cityWiseData;
  }
}
