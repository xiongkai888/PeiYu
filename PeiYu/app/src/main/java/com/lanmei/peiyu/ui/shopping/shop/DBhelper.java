package com.lanmei.peiyu.ui.shopping.shop;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xson.common.utils.L;


/**
 * Created by xkai
 */

public class DBhelper extends SQLiteOpenHelper {

    public static String TAG = "DBhelper";

    private static String dbName = "peiyu.db";
    private static int dbVersion = 1;
    public static DBhelper dBhelper;

    public static DBhelper newInstance(Context context) {
        if (dBhelper == null)
            dBhelper = new DBhelper(context);
        return dBhelper;
    }

    public DBhelper(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        L.d(TAG, "创建数据库成功:" + dbVersion);
        db.execSQL(DBShopCartHelper.createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        L.d(TAG, "更新数据库成功:" + oldVersion + "  newVersion:" + newVersion);
        update(oldVersion,db);

    }

    private void update(int oldVersion, SQLiteDatabase db) {
        switch (oldVersion) {
//            case 0:
//                db.execSQL(DBShopCartHelper.createTable);
//            case 1:
//                db.execSQL(DBShopCartHelper.createTable);
        }
    }

    public void deleteDatabase(Context context) {
        context.deleteDatabase(dbName);
    }

}
