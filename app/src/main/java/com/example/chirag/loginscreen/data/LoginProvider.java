package com.example.chirag.loginscreen.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class LoginProvider extends ContentProvider {

    private LoginDbHelper mLogibDbHelper;

    private static final int LOGIN = 100;

    public static final int LOGIN_ID = 101;

    public static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(LoginContract.CONTENT_AUTHORITY, LoginContract.PATH_LOGIN, LOGIN);

        sUriMatcher.addURI(LoginContract.CONTENT_AUTHORITY, LoginContract.PATH_LOGIN + "/#", LOGIN_ID);
    }

    @Override
    public boolean onCreate() {
        mLogibDbHelper = new LoginDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] seletionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mLogibDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case LOGIN:
                cursor = database.query(LoginContract.LoginEntry.TABLE_NAME, projection, selection, seletionArgs, null, null, sortOrder);
                break;
            case LOGIN_ID:
                selection = LoginContract.LoginEntry._ID + "=?";
                seletionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(LoginContract.LoginEntry.TABLE_NAME, projection, selection, seletionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("CANNOT QUERY UNKNOWN URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case LOGIN:
                return LoginContract.LoginEntry.CONTENT_LIST_TYPE;
            case LOGIN_ID:
                return LoginContract.LoginEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("UNKNOWN URI " + uri + " WITH MATCH " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case LOGIN:
                return addStock(uri, contentValues);
            default:
                throw new IllegalArgumentException("INSERTION IS NOT SUPPORTED FOR " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mLogibDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        switch (match) {
            case LOGIN:
                rowsDeleted = database.delete(LoginContract.LoginEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case LOGIN_ID:
                selection = LoginContract.LoginEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(LoginContract.LoginEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("CANNOT QUERY UNKNOWN URI " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case LOGIN:
                return editStock(uri, contentValues, selection, selectionArgs);
            case LOGIN_ID:
                selection = LoginContract.LoginEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return editStock(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("UPDATE IS NOT SUPPORTED FOR " + uri);
        }
    }

    private Uri addStock(Uri uri, ContentValues values) {
        SQLiteDatabase database = mLogibDbHelper.getWritableDatabase();

        long id = database.insert(LoginContract.LoginEntry.TABLE_NAME, null, values);

        if (id == -1) {
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    private int editStock(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mLogibDbHelper.getWritableDatabase();
        int rowsUpdated = database.update(LoginContract.LoginEntry.TABLE_NAME, values, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}
