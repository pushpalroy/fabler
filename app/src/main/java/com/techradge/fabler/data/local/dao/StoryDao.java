package com.techradge.fabler.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import androidx.lifecycle.LiveData;

import com.techradge.fabler.data.model.Story;

import java.util.List;

@Dao
public interface StoryDao {

    @Query("SELECT * FROM story")
    LiveData<List<Story>> getStories();

    @Query("SELECT * FROM story WHERE id = :id")
    Story getStoryById(int id);

    @Query("SELECT * FROM story WHERE storyTitle = :title")
    List<Story> getStoryByTitle(String title);

    @Insert
    void insertStory(Story story);

    @Update
    void updateStory(Story story);

    @Delete
    void deleteStory(Story story);
}