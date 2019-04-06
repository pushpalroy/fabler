package com.techradge.fabler.data.firebase.operations.user;

import android.content.Context;

import com.techradge.fabler.data.model.User;

interface UserData {

    void insertUserData(User user, Context context);
}
