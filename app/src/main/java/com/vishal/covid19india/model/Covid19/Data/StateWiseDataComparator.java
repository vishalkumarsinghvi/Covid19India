package com.vishal.covid19india.model.Covid19.Data;

import java.util.Comparator;

public class StateWiseDataComparator {

  private static int extractInt(String s) {
    String num = s.replaceAll("\\D", "");
    // return 0 if no digits found
    return num.isEmpty() ? 0 : Integer.parseInt(num);
  }

  public static class ConfirmedSorter implements Comparator<Statewise> {

    private boolean isConfirmedClicked;

    public ConfirmedSorter(boolean isConfirmedClicked) {
      this.isConfirmedClicked = isConfirmedClicked;
    }

    public int compare(Statewise o1, Statewise o2) {
      if (isConfirmedClicked) {
        return extractInt(o1.getConfirmed()) - extractInt(
            o2.getConfirmed());
      } else {

        return extractInt(o2.getConfirmed()) - extractInt(
            o1.getConfirmed());
      }
    }
  }
  public static class RecoveredSorter implements Comparator<Statewise> {

    private boolean isRecoveredClicked;

    public RecoveredSorter(boolean isRecoveredClicked) {
      this.isRecoveredClicked = isRecoveredClicked;
    }

    public int compare(Statewise o1, Statewise o2) {
      if (isRecoveredClicked) {
        return extractInt(o1.getRecovered()) - extractInt(
            o2.getRecovered());
      } else {

        return extractInt(o2.getRecovered()) - extractInt(
            o1.getRecovered());
      }
    }
  }
  public static class ActiveSorter implements Comparator<Statewise> {

    private boolean isActiveClicked;

    public ActiveSorter(boolean isActiveClicked) {
      this.isActiveClicked = isActiveClicked;
    }

    public int compare(Statewise o1, Statewise o2) {
      if (isActiveClicked) {
        return extractInt(o1.getActive()) - extractInt(
            o2.getActive());
      } else {

        return extractInt(o2.getActive()) - extractInt(
            o1.getActive());
      }
    }
  }
  public static class DeathsSorter implements Comparator<Statewise> {

    private boolean isDeathsClicked;

    public DeathsSorter(boolean isDeathsClicked) {
      this.isDeathsClicked = isDeathsClicked;
    }

    public int compare(Statewise o1, Statewise o2) {
      if (isDeathsClicked) {
        return extractInt(o1.getDeaths()) - extractInt(
            o2.getDeaths());
      } else {

        return extractInt(o2.getDeaths()) - extractInt(
            o1.getDeaths());
      }
    }
  }


  public static class StateSorter implements Comparator<Statewise> {

    private boolean isStateClicked;

    public StateSorter(boolean isStateClicked) {
      this.isStateClicked = isStateClicked;
    }

    public int compare(Statewise o1, Statewise o2) {
      if (isStateClicked) {
        return o1.getState().compareTo(o2.getState());
      } else {

        return o2.getState().compareTo(o1.getState());
      }
    }
  }

}
