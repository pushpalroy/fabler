package com.techradge.fabler.database.offline;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.techradge.fabler.model.Story;

import java.util.List;

@Dao
public interface StoryDao {

    @Query("SELECT * FROM story")
    LiveData<List<Story>> getStories();

    @Insert
    void insertStory(Story story);

    @Update
    void updateStory(Story story);

    @Delete
    void deleteStory(Story story);
}