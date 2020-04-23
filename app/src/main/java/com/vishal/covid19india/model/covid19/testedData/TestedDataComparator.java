package com.vishal.covid19india.model.covid19.testedData;

import java.util.Comparator;

public class TestedDataComparator {

  private static int extractInt(String s) {
    String num = s.replaceAll("\\D", "");
    // return 0 if no digits found
    return num.isEmpty() ? 0 : Integer.parseInt(num);
  }

  public static class TotalTestedSorter implements Comparator<States_tested_data> {

    private boolean isTotalTestedClicked;

    public TotalTestedSorter(boolean isTotalTestedClicked) {
      this.isTotalTestedClicked = isTotalTestedClicked;
    }

    public int compare(States_tested_data o1, States_tested_data o2) {
      if (isTotalTestedClicked) {
        return extractInt(o1.getTotaltested()) - extractInt(
            o2.getTotaltested());
      } else {

        return extractInt(o2.getTotaltested()) - extractInt(
            o1.getTotaltested());
      }
    }
  }

  public static class PositiveSorter implements Comparator<States_tested_data> {

    private boolean isPositiveClicked;

    public PositiveSorter(boolean isPositiveClicked) {
      this.isPositiveClicked = isPositiveClicked;
    }

    public int compare(States_tested_data o1, States_tested_data o2) {
      if (isPositiveClicked) {
        return extractInt(o1.getPositive()) - extractInt(
            o2.getPositive());
      } else {

        return extractInt(o2.getPositive()) - extractInt(
            o1.getPositive());
      }
    }
  }

  public static class NegativeSorter implements Comparator<States_tested_data> {

    private boolean isNegativeClicked;

    public NegativeSorter(boolean isNegativeClicked) {
      this.isNegativeClicked = isNegativeClicked;
    }

    public int compare(States_tested_data o1, States_tested_data o2) {
      if (isNegativeClicked) {
        return extractInt(o1.getNegative()) - extractInt(
            o2.getNegative());
      } else {

        return extractInt(o2.getNegative()) - extractInt(
            o1.getNegative());
      }
    }
  }

  public static class DateSorter implements Comparator<States_tested_data> {

    private boolean isDateClicked;

    public DateSorter(boolean isDateClicked) {
      this.isDateClicked = isDateClicked;
    }

    public int compare(States_tested_data o1, States_tested_data o2) {
      if (isDateClicked) {
        return extractInt(o1.getUpdatedon()) - extractInt(
            o2.getUpdatedon());
      } else {

        return extractInt(o2.getUpdatedon()) - extractInt(
            o1.getUpdatedon());
      }
    }
  }


  public static class StateSorter implements Comparator<States_tested_data> {

    private boolean isStateClicked;

    public StateSorter(boolean isStateClicked) {
      this.isStateClicked = isStateClicked;
    }

    public int compare(States_tested_data o1, States_tested_data o2) {
      if (isStateClicked) {
        return o1.getState().compareTo(o2.getState());
      } else {

        return o2.getState().compareTo(o1.getState());
      }
    }
  }

}
