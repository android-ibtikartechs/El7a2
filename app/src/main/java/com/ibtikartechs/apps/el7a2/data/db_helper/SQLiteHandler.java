package com.ibtikartechs.apps.el7a2.data.db_helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ahmedyehya on 4/30/18.
 */

public class SQLiteHandler extends SQLiteOpenHelper {
    Context context;

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "app_database";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
