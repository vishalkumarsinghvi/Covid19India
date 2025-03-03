package com.vishal.covid19india.model.covid19.rawData;

import com.google.gson.annotations.SerializedName;
import com.vishal.covid19india.R;
import com.vishal.covid19india.constants.AppConstants;
import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

public class Raw_data implements Serializable {

  @SerializedName("patientnumber")
  private String patientnumber;

  @SerializedName("currentstatus")
  private String currentstatus;

  @SerializedName("estimatedonsetdate")
  private String estimatedonsetdate;

  @SerializedName("_d180g")
  private String notes;

  @SerializedName("gender")
  private String gender;

  @SerializedName("source3")
  private String source3;

  @SerializedName("agebracket")
  private String agebracket;

  @SerializedName("source1")
  private String source1;

  @SerializedName("source2")
  private String source2;

  @SerializedName("detectedstate")
  private String detectedstate;

  @SerializedName("statuschangedate")
  private String statuschangedate;

  @SerializedName("dateannounced")
  private String dateannounced;

  @SerializedName("detecteddistrict")
  private String detecteddistrict;

  @SerializedName("backupnotes")
  private String backupnotes;

  @SerializedName("nationality")
  private String nationality;

  @SerializedName("contractedfromwhichpatientsuspected")
  private String contractedfromwhichpatientsuspected;

  @SerializedName("detectedcity")
  private String detectedcity;

  @SerializedName("statepatientnumber")
  private String statepatientnumber;

  private int unKnownImageUrl;

  private int genderImageUrl = 0;

  public int getGenderImageUrl() {
    return genderImageUrl;
  }

  public void setGenderImageUrl() {
    if (currentstatus.equals(AppConstants.Recovered)) {
      if (isGenderMale()) {
        this.genderImageUrl = R.drawable.male_recovered;
      } else if (isGenderFeMale()) {
        this.genderImageUrl = R.drawable.female_recovered;
      } else {
        setUnKnownImageUrl(R.drawable.ic_unkown_recovered);
      }
    }
    if (currentstatus.equals(AppConstants.Hospitalized)) {
      if (isGenderMale()) {
        this.genderImageUrl = R.drawable.male_hospitalized;
      } else if (isGenderFeMale()) {
        this.genderImageUrl = R.drawable.female_hospitalized;
      } else {
        setUnKnownImageUrl(R.drawable.ic_unkown_hospitalized);
      }
    }
    if (currentstatus.equals(AppConstants.Deceased)) {
      if (isGenderMale()) {
        this.genderImageUrl = R.drawable.male_deceased;
      } else if (isGenderFeMale()) {
        this.genderImageUrl = R.drawable.female_deceased;
      } else {
        setUnKnownImageUrl(R.drawable.ic_unkown_deceased);
      }
    }
  }

  public int getUnKnownImageUrl() {
    return unKnownImageUrl;
  }

  private void setUnKnownImageUrl(int unKnownImageUrl) {
    this.unKnownImageUrl = unKnownImageUrl;
  }

  private boolean isGenderMale() {
    return gender.equals("M");
  }

  private boolean isGenderFeMale() {
    return gender.equals("F");
  }

  public String getPatientnumber() {
    return patientnumber.equals("") ? "-" : patientnumber;
  }

  public String getCurrentstatus() {
    return currentstatus.equals("") ? "-" : currentstatus;
  }

  public String getEstimatedonsetdate() {
    return estimatedonsetdate;
  }

  public String getNotes() {
    return notes != null ? notes : backupnotes;
  }

  public String getGender() {
    String genderFull;
    if (gender.equals("M")) {
      genderFull = "Male";
    } else if (gender.equals("F")) {
      genderFull = "Female";
    } else {
      genderFull = "Unknown";
    }
    return genderFull;
  }

  public String getSource3() {
    if (source3.equals("")) {
      return source3;
    } else {
      return "\n3:- ".concat(source3);
    }

  }

  public String getAgebracket() {
    return agebracket.equals("") ? "-" : agebracket;
  }

  public String getSource1() {
    if (source1.equals("")) {
      return source1;
    } else {
      return "\n1:- ".concat(source1);
    }
  }

  public String getSource() {
    if (!source1.equals("")) {
      return source1;
    } else if (!source2.equals("")) {
      return source2;
    } else if (!source3.equals("")) {
      return source3;
    } else {
      return "";
    }
  }

  public String getSource2() {
    if (source2.equals("")) {
      return source2;
    } else {
      return "\n2:- ".concat(source2);
    }

  }

  public String getDetectedstate() {
    return detectedstate;
  }

  public String getStatuschangedate() {
    return statuschangedate;
  }

  public String getDateannounced() {
    return dateannounced;
  }

  public String getDetecteddistrict() {
    return detecteddistrict.equals("") ? "-" : detecteddistrict;
  }

  public String getBackupnotes() {
    return backupnotes;
  }

  public String getNationality() {
    return nationality;
  }

  public String getContractedfromwhichpatientsuspected() {
    return contractedfromwhichpatientsuspected;
  }

  public String getDetectedcity() {
    return detectedcity.equals("") ? "-" : detectedcity;
  }

  public String getStatepatientnumber() {
    return statepatientnumber;
  }

  @NotNull
  @Override
  public String toString() {
    return "ClassPojo [patientnumber = " + patientnumber + ", currentstatus = " + currentstatus
        + ", estimatedonsetdate = " + estimatedonsetdate + ", notes = " + notes + ", gender = "
        + gender + ", source3 = " + source3 + ", agebracket = " + agebracket + ", source1 = "
        + source1 + ", source2 = " + source2 + ", detectedstate = " + detectedstate
        + ", statuschangedate = " + statuschangedate + ", dateannounced = " + dateannounced
        + ", detecteddistrict = " + detecteddistrict + ", backupnotes = " + backupnotes
        + ", nationality = " + nationality + ", contractedfromwhichpatientsuspected = "
        + contractedfromwhichpatientsuspected + ", detectedcity = " + detectedcity
        + ", statepatientnumber = " + statepatientnumber + "]";
  }
}