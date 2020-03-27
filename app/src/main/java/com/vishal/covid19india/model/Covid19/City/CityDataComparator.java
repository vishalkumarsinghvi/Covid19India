package com.vishal.covid19india.model.Covid19.City;

import java.util.Comparator;

public class CityDataComparator {

  private static int extractInt(String s) {
    String num = s.replaceAll("\\D", "");
    // return 0 if no digits found
    return num.isEmpty() ? 0 : Integer.parseInt(num);
  }

  public static class ConfirmedSorter implements Comparator<CityModel> {

    private boolean isConfirmedClicked;

    public ConfirmedSorter(boolean isConfirmedClicked) {
      this.isConfirmedClicked = isConfirmedClicked;
    }

    public int compare(CityModel o1, CityModel o2) {
      if (isConfirmedClicked) {
        return extractInt(o1.getCityWiseData().getConfirmed()) - extractInt(
            o2.getCityWiseData().getConfirmed());
      } else {

        return extractInt(o2.getCityWiseData().getConfirmed()) - extractInt(
            o1.getCityWiseData().getConfirmed());
      }
    }
  }


  public static class StateSorter implements Comparator<CityModel> {

    private boolean isStateClicked;

    public StateSorter(boolean isStateClicked) {
      this.isStateClicked = isStateClicked;
    }

    public int compare(CityModel o1, CityModel o2) {
      if (isStateClicked) {
        return o1.getState().compareTo(o2.getState());
      } else {

        return o2.getState().compareTo(o1.getState());
      }
    }
  }

  public static class CitySorter implements Comparator<CityModel> {

    private boolean isCityClicked;

    public CitySorter(boolean isCityClicked) {
      this.isCityClicked = isCityClicked;
    }

    public int compare(CityModel o1, CityModel o2) {
      if (isCityClicked) {
        return o1.getDistrict().compareTo(o2.getDistrict());
      } else {

        return o2.getDistrict().compareTo(o1.getDistrict());
      }
    }
  }

}
