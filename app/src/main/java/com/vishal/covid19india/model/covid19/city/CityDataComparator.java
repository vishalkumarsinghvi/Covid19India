package com.vishal.covid19india.model.covid19.city;

import java.util.Comparator;

public class CityDataComparator {

  private static int extractInt(String s) {
    String num = s.replaceAll("\\D", "");
    // return 0 if no digits found
    return num.isEmpty() ? 0 : Integer.parseInt(num);
  }

  public static class ConfirmedSorter implements Comparator<DistrictData> {

    private boolean isConfirmedClicked;

    public ConfirmedSorter(boolean isConfirmedClicked) {
      this.isConfirmedClicked = isConfirmedClicked;
    }

    public int compare(DistrictData o1, DistrictData o2) {
      if (isConfirmedClicked) {
        return extractInt(o1.getConfirmed()) - extractInt(
            o2.getConfirmed());
      } else {

        return extractInt(o2.getConfirmed()) - extractInt(
            o1.getConfirmed());
      }
    }
  }

  public static class CitySorter implements Comparator<DistrictData> {

    private boolean isCityClicked;

    public CitySorter(boolean isCityClicked) {
      this.isCityClicked = isCityClicked;
    }

    public int compare(DistrictData o1, DistrictData o2) {
      if (isCityClicked) {
        return o1.getDistrict().compareTo(o2.getDistrict());
      } else {

        return o2.getDistrict().compareTo(o1.getDistrict());
      }
    }
  }

}
