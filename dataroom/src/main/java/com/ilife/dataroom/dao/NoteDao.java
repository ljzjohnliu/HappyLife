package com.ilife.dataroom.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ilife.dataroom.model.NoteModel;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT COUNT(*) FROM " + NoteModel.NOTE_TABLE_NAME)
    int count();

    @Query("SELECT * FROM " + NoteModel.NOTE_TABLE_NAME + " WHERE " +
            NoteModel.USER_ID + " = :userId")
    List<NoteModel> queryNotesByUserId(long userId);

    @Query("SELECT * FROM " + NoteModel.NOTE_TABLE_NAME + " WHERE " +
            NoteModel.DATE + " = :date")
    List<NoteModel> queryNotesByDate(String date);

    @Query("SELECT * FROM " + NoteModel.NOTE_TABLE_NAME)
    List<NoteModel> queryAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertOneNote(NoteModel NoteModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertNote(NoteModel... NoteModels);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertNotes(List<NoteModel> NoteModels);

    @Query("DELETE FROM " + NoteModel.NOTE_TABLE_NAME + " WHERE " +
            NoteModel.USER_ID + " = :userId")
    int deleteByUserId(long userId);

    @Delete
    void delete(NoteModel... NoteModels);

    @Delete
    void deleteNotes(List<NoteModel> NoteModels);

    @Query("DELETE FROM " + NoteModel.NOTE_TABLE_NAME)
    void deleteAll();

    @Update
    public int updateUsers(List<NoteModel> NoteModels);

    @Update
    public int updateNotes(NoteModel... NoteModels);

}