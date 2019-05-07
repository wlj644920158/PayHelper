package com.wanglijun.payhelper.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wanglijun.payhelper.entity.Bill;

import static com.wanglijun.payhelper.common.Constants.DATABASE_VERSION;
import static com.wanglijun.payhelper.common.Constants.DB_NAME;

public class HelperDbHelper extends SQLiteOpenHelper {

    public HelperDbHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + Bill.TABLE_NAME + " ("
                + Bill._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Bill.DATE + " TEXT NOT NULL, "
                + Bill.UID + " INTEGER NOT NULL, "
                + Bill.TITLE + " TEXT NOT NULL, "
                + Bill.CONTENT + " TEXT NOT NULL, "
                + Bill.MONEY + " TEXT NOT NULL, "
                + Bill.SYNC + " INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
