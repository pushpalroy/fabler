package com.techradge.fabler.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "user")
public class User {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String uid;
    @ColumnInfo(name = "userName")
    private String userName;
    @ColumnInfo(name = "fullName")
    private String fullName;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "photoURL")
    private String photoURL;
    @ColumnInfo(name = "gender")
    private String gender;
    @ColumnInfo(name = "location")
    private String location;
    @ColumnInfo(name = "about")
    private String about;
    @ColumnInfo(name = "level")
    private String level;
    @ColumnInfo(name = "profileCreationTimeStamp")
    private String profileCreationTimeStamp;
    @ColumnInfo(name = "lastOnlineTimeStamp")
    private String lastOnlineTimeStamp;
    @ColumnInfo(name = "emailVerified")
    private boolean emailVerified;

    @Ignore
    public User() {
    }

    @Ignore
    public User(String fullName, String email, boolean emailVerified, String photoURL, String uid) {
        this.fullName = fullName;
        this.email = email;
        this.emailVerified = emailVerified;
        this.photoURL = photoURL;
        this.uid = uid;
    }

    public User(String userName, String fullName, String email, boolean emailVerified, String photoURL,
                String gender, String location, String about, String level, String profileCreationTimeStamp,
                String lastOnlineTimeStamp, String uid) {
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.emailVerified = emailVerified;
        this.photoURL = photoURL;
        this.gender = gender;
        this.location = location;
        this.about = about;
        this.level = level;
        this.profileCreationTimeStamp = profileCreationTimeStamp;
        this.lastOnlineTimeStamp = lastOnlineTimeStamp;
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getProfileCreationTimeStamp() {
        return profileCreationTimeStamp;
    }

    public void setProfileCreationTimeStamp(String profileCreationTimeStamp) {
        this.profileCreationTimeStamp = profileCreationTimeStamp;
    }

    public String getLastOnlineTimeStamp() {
        return lastOnlineTimeStamp;
    }

    public void setLastOnlineTimeStamp(String lastOnlineTimeStamp) {
        this.lastOnlineTimeStamp = lastOnlineTimeStamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
