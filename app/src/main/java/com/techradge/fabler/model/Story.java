package com.techradge.fabler.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "story")
public class Story {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int storyId;
    private String author;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "story")
    private String story;
    @ColumnInfo(name = "time")
    private String time;
    private String likes;
    private String comments;
    private String shares;

    @Ignore
    public Story() {
    }

    public Story(@NonNull int storyId, String title, String story, String time) {
        this.storyId = storyId;
        this.title = title;
        this.story = story;
        this.time = time;
    }

    @Ignore
    public Story(@NonNull int storyId, String author, String title, String story, String time, String likes, String comments, String shares) {
        this.storyId = storyId;
        this.author = author;
        this.title = title;
        this.story = story;
        this.time = time;
        this.likes = likes;
        this.comments = comments;
        this.shares = shares;
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(@NonNull int storyId) {
        this.storyId = storyId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getShares() {
        return shares;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }
}
