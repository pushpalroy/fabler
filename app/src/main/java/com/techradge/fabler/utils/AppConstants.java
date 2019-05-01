package com.techradge.fabler.utils;

public final class AppConstants {

    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";
    public static final String TIMESTAMP_FORMAT1 = "yyyyMMdd_HHmmss";
    // Shared preferences file name
    public static final String PREF_NAME = "PREF_FILE_FABLE";
    public static final String DB_NAME_STORY = "storyDatabase";
    public static final String DB_NAME_USER = "userDatabase";

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

    public enum RoomInsertion {

        INSERTION_STATUS_NOT_INSERTED(0),
        INSERTION_STATUS_INSERTED(1),
        INSERTION_STATUS_ERROR(2);

        private final int mType;

        RoomInsertion(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }

    public enum RoomDeletion {

        DELETION_STATUS_NOT_DELETED(0),
        DELETION_STATUS_DELETED(1),
        DELETION_STATUS_ERROR(2);

        private final int mType;

        RoomDeletion(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }

    public enum RoomUpdating {

        UPDATING_STATUS_NOT_UPDATED(0),
        UPDATING_STATUS_UPDATED(1),
        UPDATING_STATUS_ERROR(2);

        private final int mType;

        RoomUpdating(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }
}
