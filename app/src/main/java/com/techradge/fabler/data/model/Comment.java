package com.techradge.fabler.data.model;

import java.util.Map;

public class Comment {

    private String commentId;

    private String storyId;

    private String authorId;

    private String authorName;

    private String commentBody;

    private long timeStamp;

    private int totalLikes;

    private Map<String, String> likes;

    public Comment() {
    }

    public Comment(String storyId, String authorName, String commentBody, long timeStamp) {
        this.storyId = storyId;
        this.authorName = authorName;
        this.commentBody = commentBody;
        this.timeStamp = timeStamp;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
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

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
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
