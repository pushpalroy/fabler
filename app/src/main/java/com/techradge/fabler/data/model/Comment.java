package com.techradge.fabler.data.model;

public class Comment {
    private String commentId, comment, author, storyId;

    public Comment() {
    }

    public Comment(String comment, String author, String storyId) {
        this.comment = comment;
        this.author = author;
        this.storyId = storyId;
    }

    public Comment(String commentId, String comment, String author, String storyId) {
        this.commentId = commentId;
        this.comment = comment;
        this.author = author;
        this.storyId = storyId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
}
