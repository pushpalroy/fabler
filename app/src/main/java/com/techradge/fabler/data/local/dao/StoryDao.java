package com.techradge.fabler.data.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.techradge.fabler.data.model.Story;

import java.util.List;

@Dao
public interface StoryDao {

    @Query("SELECT * FROM story")
    LiveData<List<Story>> getStories();

    @Query("SELECT * FROM story WHERE id = :id")
    Story getStoryById(int id);

    @Query("SELECT * FROM story WHERE title = :title")
    List<Story> getStoryByTitle(String title);

    @Insert
    void insertStory(Story story);

    @Update
    void updateStory(Story story);

    @Delete
    void deleteStory(Story story);
}