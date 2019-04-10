package com.techradge.fabler.utils;

public final class AppConstants {

    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";
    // Shared preferences file name
    public static final String PREF_NAME = "PREF_FILE_FABLE";
    public static final String DB_NAME = "storyDatabase";
    public static final String DB_NAME_1 = "userDatabase";

    private AppConstants() {
        // This utility class is not publicly instantiable
    }


    public enum LoggedInMode {

        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_GOOGLE(1),
        LOGGED_IN_MODE_FB(2),
        LOGGED_IN_MODE_SERVER(3);

        private final int mType;

        LoggedInMode(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }
}
