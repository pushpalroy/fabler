package com.techradge.fabler.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.firebase.database.Exclude;

import java.util.Map;

@Entity(tableName = "storyData")
public class StoryData {

    @Exclude
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String storyId;

    private String storyBody;

    @Ignore
    private String feedbackId;

    @Ignore
    private Map<String, String> likes;

    @Ignore
    private Map<String, String> tags;

    @Ignore
    public StoryData() {
    }

    public StoryData(String storyId, String storyBody) {
        this.storyId = storyId;
        this.storyBody = storyBody;
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

    public String getStoryBody() {
        return storyBody;
    }

    public void setStoryBody(String storyBody) {
        this.storyBody = storyBody;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Map<String, String> getLikes() {
        return likes;
    }

    public void setLikes(Map<String, String> likes) {
        this.likes = likes;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }
}
