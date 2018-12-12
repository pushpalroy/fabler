package com.techradge.fabler.database.operations.user;

import android.content.Context;

import com.techradge.fabler.model.User;

interface UserData {

    void insertUserData(User user, Context context);
}
