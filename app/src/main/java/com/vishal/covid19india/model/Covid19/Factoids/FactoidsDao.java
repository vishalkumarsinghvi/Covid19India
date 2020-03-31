package com.vishal.covid19india.model.Covid19.Factoids;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.ArrayList;

@Dao
public interface FactoidsDao {

  @Insert
  void insertFactoids(ArrayList<Factoids> factoids);


  @Query("SELECT * FROM factoids")
  ArrayList<Factoids> loadAllFactoids();

}
