package com.example.chirag.loginscreen.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.chirag.loginscreen.data.LoginContract.LoginEntry;

public class LoginDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "user_info.db";
    public static final int DATABASE_VERSION = 1;

    public LoginDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_USER_INFO = "CREATE TABLE " + LoginEntry.TABLE_NAME + " ("+
                LoginEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LoginEntry.FIRST_NAME + " TEXT NOT NULL, " +
                LoginEntry.LAST_NAME + " TEXT, " +
                LoginEntry.EMAIL_ADDRESS + " TEXT, " +
                LoginEntry.USER_PASSWORD + " TEXT); ";
        sqLiteDatabase.execSQL(SQL_CREATE_USER_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
