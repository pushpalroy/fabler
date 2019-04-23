package com.techradge.fabler.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

@Entity(tableName = "story")
public class Story {

    @Exclude
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String storyId;

    private String authorId;

    private String authorName;

    private long createdOn;

    private String storyTitle;

    private String storyBrief;

    @Exclude
    private String storyBody;

    private String photoUrl;

    @Ignore
    private int totalParts;

    @Ignore
    private int partNum;

    private String category;

    @Ignore
    private int readTime;

    @Ignore
    private boolean isEdited;

    @Ignore
    private long editedOn;

    @Ignore
    private int totalLikes;

    @Ignore
    private int totalFeedbacks;

    @Ignore
    private boolean isCollaborative;

    @Ignore
    private int totalContributors;

    @Ignore
    private Map<String, String> contributors;

    @Ignore
    private long publishedOn;

    @Ignore
    // For Firebase Realtime DB
    public Story() {
    }

    // For Room
    public Story(String storyId, String authorId, String authorName, long createdOn,
                 String storyTitle, String storyBrief, String photoUrl, String category) {
        this.storyId = storyId;
        this.authorId = authorId;
        this.authorName = authorName;
        this.createdOn = createdOn;
        this.storyTitle = storyTitle;
        this.storyBrief = storyBrief;
        this.photoUrl = photoUrl;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getStoryId() {
        return storyId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public String getStoryBrief() {
        return storyBrief;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public int getTotalParts() {
        return totalParts;
    }

    public int getPartNum() {
        return partNum;
    }

    public String getCategory() {
        return category;
    }

    public int getReadTime() {
        return readTime;
    }

    public boolean isEdited() {
        return isEdited;
    }

    public long getEditedOn() {
        return editedOn;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public int getTotalFeedbacks() {
        return totalFeedbacks;
    }

    public boolean isCollaborative() {
        return isCollaborative;
    }

    public int getTotalContributors() {
        return totalContributors;
    }

    public Map<String, String> getContributors() {
        return contributors;
    }

    public long getPublishedOn() {
        return publishedOn;
    }

    public String getStoryBody() {
        return storyBody;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public void setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
    }

    public void setStoryBrief(String storyBrief) {
        this.storyBrief = storyBrief;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setTotalParts(int totalParts) {
        this.totalParts = totalParts;
    }

    public void setPartNum(int partNum) {
        this.partNum = partNum;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setReadTime(int readTime) {
        this.readTime = readTime;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }

    public void setEditedOn(long editedOn) {
        this.editedOn = editedOn;
    }

    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
    }

    public void setTotalFeedbacks(int totalFeedbacks) {
        this.totalFeedbacks = totalFeedbacks;
    }

    public void setCollaborative(boolean collaborative) {
        isCollaborative = collaborative;
    }

    public void setTotalContributors(int totalContributors) {
        this.totalContributors = totalContributors;
    }

    public void setContributors(Map<String, String> contributors) {
        this.contributors = contributors;
    }

    public void setPublishedOn(long publishedOn) {
        this.publishedOn = publishedOn;
    }

    public void setStoryBody(String storyBody) {
        this.storyBody = storyBody;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("storyId", storyId);
        result.put("authorName", authorName);
        result.put("storyTitle", storyTitle);
        result.put("storyBrief", storyBrief);
        return result;
    }

}
