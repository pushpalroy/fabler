package com.techradge.fabler.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Map;

@Entity(tableName = "user")
public class User {
    @NonNull
    @PrimaryKey
    private String uid;

    private String fullName;

    private String email;

    private String photoURL;

    private boolean emailVerified;

    @Ignore
    private String gender;

    @Ignore
    private String location;

    @Ignore
    private String language;

    @Ignore
    private String about;

    @Ignore
    private long profileCreatedOn;

    @Ignore
    private int totalStories;

    @Ignore
    private int totalFollowers;

    @Ignore
    private int totalFollowing;

    @Ignore
    private int totalBookmarks;

    @Ignore
    private Map<String, String> followers;

    @Ignore
    private Map<String, String> following;

    @Ignore
    private Map<String, String> myStories;

    @Ignore
    private Map<String, String> myBookmarks;


    @Ignore
    // For Firebase Realtime DB
    public User() {
    }

    // For Room
    public User(@NonNull String uid, String fullName, String email, boolean emailVerified, String photoURL) {
        this.uid = uid;
        this.fullName = fullName;
        this.email = email;
        this.photoURL = photoURL;
        this.emailVerified = emailVerified;
    }

    @NonNull
    public String getUid() {
        return uid;
    }

    public void setUid(@NonNull String uid) {
        this.uid = uid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public long getProfileCreatedOn() {
        return profileCreatedOn;
    }

    public void setProfileCreatedOn(long profileCreatedOn) {
        this.profileCreatedOn = profileCreatedOn;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public int getTotalStories() {
        return totalStories;
    }

    public void setTotalStories(int totalStories) {
        this.totalStories = totalStories;
    }

    public int getTotalFollowers() {
        return totalFollowers;
    }

    public void setTotalFollowers(int totalFollowers) {
        this.totalFollowers = totalFollowers;
    }

    public int getTotalFollowing() {
        return totalFollowing;
    }

    public void setTotalFollowing(int totalFollowing) {
        this.totalFollowing = totalFollowing;
    }

    public int getTotalBookmarks() {
        return totalBookmarks;
    }

    public void setTotalBookmarks(int totalBookmarks) {
        this.totalBookmarks = totalBookmarks;
    }

    public Map<String, String> getFollowers() {
        return followers;
    }

    public void setFollowers(Map<String, String> followers) {
        this.followers = followers;
    }

    public Map<String, String> getFollowing() {
        return following;
    }

    public void setFollowing(Map<String, String> following) {
        this.following = following;
    }

    public Map<String, String> getMyStories() {
        return myStories;
    }

    public void setMyStories(Map<String, String> myStories) {
        this.myStories = myStories;
    }

    public Map<String, String> getMyBookmarks() {
        return myBookmarks;
    }

    public void setMyBookmarks(Map<String, String> myBookmarks) {
        this.myBookmarks = myBookmarks;
    }
}
