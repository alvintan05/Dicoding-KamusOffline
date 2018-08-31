package com.alvin.kamusoffline.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.alvin.kamusoffline.Model.DataModel;

import java.util.ArrayList;

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

public class KamusHelper {

    private Context context;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase database;

    public KamusHelper(Context context) {
        this.context = context;
    }

    public KamusHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public ArrayList<DataModel> getDataByNameEng(String word) {
        String search = word.trim();
        Cursor cursor = database.query(TABLE_NAME1, null, KATA_ENG + " LIKE ?", new String[]{search + "%"}, null, null, _ID + " ASC", null);
        cursor.moveToFirst();

        ArrayList<DataModel> arrayList = new ArrayList<>();
        DataModel engIndoModel;
        if (cursor.getCount() > 0) {
            do {
                engIndoModel = new DataModel();
                engIndoModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                engIndoModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA_ENG)));
                engIndoModel.setArti(cursor.getString(cursor.getColumnIndexOrThrow(ARTI_ENG)));
                arrayList.add(engIndoModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<DataModel> getDataByNameIndo(String word) {
        String result = word.trim();
        Cursor cursor = database.query(TABLE_NAME2, null, KATA_INDO + " LIKE ?", new String[]{word + "%"}, null, null, _ID + " ASC", null);
        cursor.moveToFirst();

        ArrayList<DataModel> arrayList = new ArrayList<>();
        DataModel dataModel;
        if (cursor.getCount() > 0) {
            do {
                dataModel = new DataModel();
                dataModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                dataModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA_INDO)));
                dataModel.setArti(cursor.getString(cursor.getColumnIndexOrThrow(ARTI_INDO)));
                arrayList.add(dataModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<DataModel> getAllDataEng() {
        Cursor cursor = database.query(TABLE_NAME1, null, null, null, null, null, _ID + " ASC", null);
        cursor.moveToFirst();

        ArrayList<DataModel> arrayList = new ArrayList<>();
        DataModel engIndoModel;
        if (cursor.getCount() > 0) {
            do {
                engIndoModel = new DataModel();
                engIndoModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                engIndoModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA_ENG)));
                engIndoModel.setArti(cursor.getString(cursor.getColumnIndexOrThrow(ARTI_ENG)));
                arrayList.add(engIndoModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<DataModel> getAllDataIndo() {
        Cursor cursor = database.query(TABLE_NAME2, null, null, null, null, null, _ID + " ASC", null);
        cursor.moveToFirst();

        ArrayList<DataModel> arrayList = new ArrayList<>();
        DataModel dataModel;
        if (cursor.getCount() > 0) {
            do {
                dataModel = new DataModel();
                dataModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                dataModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA_INDO)));
                dataModel.setArti(cursor.getString(cursor.getColumnIndexOrThrow(ARTI_INDO)));
                arrayList.add(dataModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertEng(DataModel engIndoModel) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KATA_ENG, engIndoModel.getKata());
        initialValues.put(ARTI_ENG, engIndoModel.getArti());
        return database.insert(TABLE_NAME1, null, initialValues);
    }

    public long insertIndo(DataModel dataModel) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KATA_INDO, dataModel.getKata());
        initialValues.put(ARTI_INDO, dataModel.getArti());
        return database.insert(TABLE_NAME2, null, initialValues);
    }

    public void beginTransaction() {
        database.beginTransaction();
    }

    public void setTransactionSuccess() {
        database.setTransactionSuccessful();
    }

    public void endTransaction() {
        database.endTransaction();
    }

    public void insertTransactionEng(DataModel engIndoModel) {
        String sql = "INSERT INTO " + TABLE_NAME1 + " (" + KATA_ENG + ", " + ARTI_ENG + ") VALUE (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, engIndoModel.getKata());
        stmt.bindString(3, engIndoModel.getArti());
        stmt.execute();
        stmt.clearBindings();
    }

    public void insertTransactionIndo(DataModel dataModel) {
        String sql = "INSERT INTO " + TABLE_NAME2 + " (" + KATA_INDO + ", " + ARTI_INDO + ") VALUE (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, dataModel.getKata());
        stmt.bindString(2, dataModel.getArti());
        stmt.execute();
        stmt.clearBindings();
    }

    public int updateEng(DataModel engIndoModel) {
        ContentValues args = new ContentValues();
        args.put(KATA_ENG, engIndoModel.getKata());
        args.put(ARTI_ENG, engIndoModel.getArti());
        return database.update(TABLE_NAME1, args, _ID + "= '" + engIndoModel.getId() + "'", null);
    }

    public int updateIndo(DataModel dataModel) {
        ContentValues args = new ContentValues();
        args.put(KATA_INDO, dataModel.getKata());
        args.put(ARTI_INDO, dataModel.getArti());
        return database.update(TABLE_NAME2, args, _ID + "= '" + dataModel.getId() + "'", null);
    }

    public int deleteEng(int id) {
        return database.delete(TABLE_NAME1, _ID + " = '" + id + "'", null);
    }

    public int deleteIndo(int id) {
        return database.delete(TABLE_NAME2, _ID + " = '" + id + "'", null);
    }

}
