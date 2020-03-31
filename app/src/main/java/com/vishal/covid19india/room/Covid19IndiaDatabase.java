package com.vishal.covid19india.room;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.vishal.covid19india.model.Covid19.Factoids.Factoids;
import com.vishal.covid19india.model.Covid19.Factoids.FactoidsDao;

@Database(entities = {Factoids.class}, exportSchema = false, version = 1)
public abstract class Covid19IndiaDatabase extends RoomDatabase {

  private static final String DB_NAME = "covid-19-india";
  private static Covid19IndiaDatabase INSTANCE;

  public static synchronized Covid19IndiaDatabase getINSTANCE(Context context) {
    if (INSTANCE == null) {
      INSTANCE = Room
          .databaseBuilder(context.getApplicationContext(), Covid19IndiaDatabase.class, DB_NAME)
          .fallbackToDestructiveMigration().build();
    }
    return INSTANCE;
  }

  public abstract FactoidsDao factoidsDao();
}
