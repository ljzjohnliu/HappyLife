package com.ilife.dataroom.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ilife.dataroom.model.FamousAphorismModel;

import java.util.List;

@Dao
public interface FamousAphorismDao {

    @Query("SELECT COUNT(*) FROM " + FamousAphorismModel.FAMOUS_APHORISM_TABLE_NAME)
    int count();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertOneFamousAphorism(FamousAphorismModel famousAphorismModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertFamousAphorism(FamousAphorismModel... famousAphorismModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertFamousAphorisms(List<FamousAphorismModel> famousAphorismModels);

    @Query("SELECT * FROM " + FamousAphorismModel.FAMOUS_APHORISM_TABLE_NAME + " WHERE " +
            FamousAphorismModel.SHOW_TAG + " = :showTag")
    FamousAphorismModel queryFamousAphorism(boolean showTag);

    @Update
    public int updateFamousAphorism(FamousAphorismModel... famousAphorism);

    @Query("SELECT * FROM " + FamousAphorismModel.FAMOUS_APHORISM_TABLE_NAME)
    List<FamousAphorismModel> queryAll();
}
