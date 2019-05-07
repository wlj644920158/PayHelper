package com.wanglijun.payhelper.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wanglijun.payhelper.common.PreferenceUtil;
import com.wanglijun.payhelper.entity.Bill;

import java.util.ArrayList;
import java.util.List;

public class BillDao {
    private HelperDbHelper helperDbHelper;


    public BillDao(Context context) {
        helperDbHelper = new HelperDbHelper(context);
    }

    public void insertBill(Bill bill) {
        SQLiteDatabase sqLiteDatabase = helperDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Bill.DATE, bill.getDate());
        values.put(Bill.TITLE, bill.getTitle());
        values.put(Bill.CONTENT, bill.getContent());
        values.put(Bill.MONEY, bill.getMoney());
        values.put(Bill.SYNC, bill.getSync());
        values.put(Bill.UID, bill.getUser_id());

        sqLiteDatabase.insert(Bill.TABLE_NAME, null, values);
        sqLiteDatabase.close();
    }

    public Bill lastedBill() {
        SQLiteDatabase sqLiteDatabase = helperDbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(Bill.TABLE_NAME, null, " uid = ?", new String[]{String.valueOf(PreferenceUtil.getInstance().userId())}, null, null, Bill._ID + " desc", "0,1");
        Bill bill = null;
        if (cursor != null && cursor.moveToFirst()) {
            bill = new Bill();
            bill.setId(cursor.getLong(cursor.getColumnIndex(Bill._ID)));
            bill.setDate(cursor.getString(cursor.getColumnIndex(Bill.DATE)));
            bill.setTitle(cursor.getString(cursor.getColumnIndex(Bill.TITLE)));
            bill.setContent(cursor.getString(cursor.getColumnIndex(Bill.CONTENT)));
            bill.setMoney(cursor.getString(cursor.getColumnIndex(Bill.MONEY)));
            bill.setSync(cursor.getInt(cursor.getColumnIndex(Bill.SYNC)));
            bill.setUser_id(cursor.getInt(cursor.getColumnIndex(Bill.UID)));
            cursor.close();
        }
        sqLiteDatabase.close();
        return bill;
    }

    public List<Bill> bills( int page, int limit) {
        SQLiteDatabase database = helperDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + Bill.TABLE_NAME + " where uid = ? order by id desc limit ?,?",
                new String[]{String.valueOf(PreferenceUtil.getInstance().userId()), String.valueOf(page), String.valueOf(limit)});
        ArrayList<Bill> list = new ArrayList<Bill>();
        while (cursor.moveToNext()) {
            Bill bill = new Bill();
            bill.setId(cursor.getLong(cursor.getColumnIndex(Bill._ID)));
            bill.setDate(cursor.getString(cursor.getColumnIndex(Bill.DATE)));
            bill.setTitle(cursor.getString(cursor.getColumnIndex(Bill.TITLE)));
            bill.setContent(cursor.getString(cursor.getColumnIndex(Bill.CONTENT)));
            bill.setMoney(cursor.getString(cursor.getColumnIndex(Bill.MONEY)));
            bill.setSync(cursor.getInt(cursor.getColumnIndex(Bill.SYNC)));
            bill.setUser_id(cursor.getInt(cursor.getColumnIndex(Bill.UID)));
            list.add(bill);
        }
        cursor.close();
        database.close();
        return list;


    }

}
