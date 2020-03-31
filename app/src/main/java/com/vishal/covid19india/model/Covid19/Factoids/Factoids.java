package com.vishal.covid19india.model.Covid19.Factoids;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

@Entity(tableName = "factoids")
public class Factoids implements Serializable {

  @PrimaryKey
  @SerializedName("id")
  private String id;

  @ColumnInfo(name = "numberoftimes")
  @SerializedName("numberoftimes")
  private String numberoftimes;

  @ColumnInfo(name = "banner")
  @SerializedName("banner")
  private String banner;

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
