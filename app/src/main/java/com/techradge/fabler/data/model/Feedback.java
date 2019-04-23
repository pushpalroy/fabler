package com.techradge.fabler.data.model;

import java.util.Map;

public class Feedback {

    private String feedbackId;

    private String storyId;

    private String authorId;

    private String authorName;

    private String feedbackBody;

    private long timeStamp;

    private int totalLikes;

    private Map<String, String> likes;

    public Feedback() {
    }

    public Feedback(String storyId, String authorName, String feedbackBody, long timeStamp) {
        this.storyId = storyId;
        this.authorName = authorName;
        this.feedbackBody = feedbackBody;
        this.timeStamp = timeStamp;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getFeedbackBody() {
        return feedbackBody;
    }

    public void setFeedbackBody(String feedbackBody) {
        this.feedbackBody = feedbackBody;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
    }

    public Map<String, String> getLikes() {
        return likes;
    }

    public void setLikes(Map<String, String> likes) {
        this.likes = likes;
    }
}
