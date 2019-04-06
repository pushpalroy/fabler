package com.techradge.fabler.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

@Entity(tableName = "story")
public class Story {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    private String storyId;
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

    public Story(@NonNull int id, String title, String story, String time) {
        this.id = id;
        this.title = title;
        this.story = story;
        this.time = time;
    }

    @Ignore
    public Story(@NonNull int id, String storyId, String author, String title, String story, String time, String likes, String comments, String shares) {
        this.id = id;
        this.storyId = storyId;
        this.author = author;
        this.title = title;
        this.story = story;
        this.time = time;
        this.likes = likes;
        this.comments = comments;
        this.shares = shares;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("storyId", storyId);
        result.put("author", author);
        result.put("title", title);
        result.put("story", story);
        result.put("time", time);
        result.put("likes", likes);
        result.put("comments", comments);
        result.put("shares", shares);

        return result;
    }
}
