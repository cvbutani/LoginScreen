package com.example.chirag.loginscreen.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class LoginContract {
    public LoginContract() {
    }

    public static final String CONTENT_AUTHORITY = "com.example.chirag.loginscreen";
    public static final String PATH_LOGIN = "userdata";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class LoginEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_LOGIN);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOGIN;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOGIN;

        public static final String TABLE_NAME = "userdata";
        public static final String _ID = BaseColumns._ID;
        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";
        public static final String EMAIL_ADDRESS = "emailAddress";
        public static final String USER_PASSWORD = "password";

        public LoginEntry() {
        }
    }
}
