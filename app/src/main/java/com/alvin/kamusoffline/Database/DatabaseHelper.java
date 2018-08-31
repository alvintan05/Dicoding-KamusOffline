package com.alvin.kamusoffline.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.alvin.kamusoffline.Database.DatabaseContract.EngIndoColumns.ARTI_ENG;
import static com.alvin.kamusoffline.Database.DatabaseContract.EngIndoColumns.KATA_ENG;
import static com.alvin.kamusoffline.Database.DatabaseContract.IndoEngColumns.ARTI_INDO;
import static com.alvin.kamusoffline.Database.DatabaseContract.IndoEngColumns.KATA_INDO;
import static com.alvin.kamusoffline.Database.DatabaseContract.TABLE_NAME1;
import static com.alvin.kamusoffline.Database.DatabaseContract.TABLE_NAME2;

/**
 * Created by Alvin Tandiardi on 31/08/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbkamus";

    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_ENGINDO = "create table " + TABLE_NAME1 + " ("
            + _ID + " integer primary key autoincrement, "
            + KATA_ENG + " text not null, "
            + ARTI_ENG + " text not null);";

    public static String CREATE_TABLE_INDOENGLISH = "create table " + TABLE_NAME2 + " ("
            + _ID + " integer primary key autoincrement, "
            + KATA_INDO + " text not null, "
            + ARTI_INDO + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENGINDO);
        db.execSQL(CREATE_TABLE_INDOENGLISH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        onCreate(db);
    }
}
