package com.example.ahmed.appsquaretask;

/**
 * Created by ahmed on 5/2/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

    static final String KEY_ROWID = "_id";
    static final String KEY_REPO_NAME = "repoName";
    static final String KEY_REPO_DESCRIPTION = "description";
    static final String KEY_OWNER_USER_NAME= "ownerUserName";
    static final String TAG = "DBAdapter";
    static final String DATABASE_NAME = "DBrepository";
    static final String DATABASE_TABLE = "RepoInformation";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_CREATE =
    " create table RepoInformation (_id integer primary key autoincrement , "
            + " repoName text not null, description text not null , ownerUserName text not null );";


    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)


        {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion +"o"
            +newVersion +",which will destroy all old data");
            db.execSQL(" DROP TABLE IF EXISTS RepoInformation ");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close() {
        DBHelper.close();
    }

    //---insert a single row into the database---
    public long insertRepo(String name, String description,String ownerUserName) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_REPO_NAME, name);
        initialValues.put(KEY_REPO_DESCRIPTION, description);
        initialValues.put(KEY_OWNER_USER_NAME, ownerUserName);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular repo---
    public boolean deleterepo(long rowId) {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "="+rowId, null)>0;
    }

    //---deletes a particular repo---
    public boolean deleteAllRepos() {
        return db.delete(DATABASE_TABLE ,null,null)>0;
    }

    //---retrieves all the repos---
    public Cursor getAllRepos() {
        return db.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_REPO_NAME,KEY_REPO_DESCRIPTION,KEY_OWNER_USER_NAME}, null, null, null, null, null);
    }

    //---retrieves a particular repo---
    public Cursor getRepo(long rowId) throws SQLException {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                KEY_REPO_NAME,KEY_REPO_DESCRIPTION,KEY_OWNER_USER_NAME }, KEY_ROWID + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    public boolean updateRepo(long rowId, String name, String email)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_REPO_NAME, name);
        args.put(KEY_REPO_DESCRIPTION, name);
        args.put(KEY_OWNER_USER_NAME, name);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
    }
