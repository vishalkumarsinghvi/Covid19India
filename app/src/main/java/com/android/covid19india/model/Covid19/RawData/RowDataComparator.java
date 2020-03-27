package com.android.covid19india.model.Covid19.RawData;

import java.util.Comparator;

public class RowDataComparator {

  private static int extractInt(String s) {
    String num = s.replaceAll("\\D", "");
    // return 0 if no digits found
    return num.isEmpty() ? 0 : Integer.parseInt(num);
  }

  public static class PIDSorter implements Comparator<Raw_data> {

    private boolean isPIdClicked;

    public PIDSorter(boolean isPIdClicked) {
      this.isPIdClicked = isPIdClicked;
    }

    public int compare(Raw_data o1, Raw_data o2) {
      if (isPIdClicked) {
        return extractInt(o1.getPatientnumber()) - extractInt(o2.getPatientnumber());
      } else {
        return extractInt(o2.getPatientnumber()) - extractInt(o1.getPatientnumber());
      }
    }
  }

  public static class AgeSorter implements Comparator<Raw_data> {

    private boolean isAgeClicked;

    public AgeSorter(boolean isAgeClicked) {
      this.isAgeClicked = isAgeClicked;
    }

    public int compare(Raw_data o1, Raw_data o2) {
      if (isAgeClicked) {
        return extractInt(o1.getAgebracket()) - extractInt(o2.getAgebracket());
      } else {

        return extractInt(o2.getAgebracket()) - extractInt(o1.getAgebracket());
      }
    }
  }

  public static class CurrentSorter implements Comparator<Raw_data> {

    private boolean isCurrentClicked;

    public CurrentSorter(boolean isCurrentClicked) {
      this.isCurrentClicked = isCurrentClicked;
    }

    public int compare(Raw_data o1, Raw_data o2) {
      if (isCurrentClicked) {
        return o1.getCurrentstatus().compareTo(o2.getCurrentstatus());
      } else {

        return o2.getCurrentstatus().compareTo(o1.getCurrentstatus());
      }
    }
  }

  public static class CitySorter implements Comparator<Raw_data> {

    private boolean isCityClicked;

    public CitySorter(boolean isCityClicked) {
      this.isCityClicked = isCityClicked;
    }

    public int compare(Raw_data o1, Raw_data o2) {
      if (isCityClicked) {
        return o1.getDetectedcity().compareTo(o2.getDetectedcity());
      } else {

        return o2.getDetectedcity().compareTo(o1.getDetectedcity());
      }
    }
  }

}
