package com.example.sqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBController extends SQLiteOpenHelper {

    //    construktor
    public DBController(Context context) {
        super(context, "ProdiTI", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        create table
        sqLiteDatabase.execSQL("create table teman (id integer primary key,nama text,telpon text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists teman");
        onCreate(sqLiteDatabase);
    }
    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onUpgrade(sqLiteDatabase,i, i1);
    }
    //    insert data
    public void insertData(HashMap<String,String> queryvalues){
        SQLiteDatabase basisdata = this.getWritableDatabase();
        ContentValues nilai = new ContentValues();
        nilai.put("nama",queryvalues.get("nama"));
        nilai.put("telpon",queryvalues.get("telpon"));
        basisdata.insert("teman",null,nilai);
        basisdata.close();
    }

    public ArrayList<HashMap<String,String>> getAllTeman(){
        ArrayList<HashMap<String,String>> daftarTeman ;
        daftarTeman = new ArrayList<HashMap<String, String>>();
//        query
        String selectQuery = "Select * from teman";
//        database
        SQLiteDatabase db = this.getReadableDatabase();
//        cursor
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do {
//                mengambil data
                HashMap<String,String> map = new HashMap<>();
                map.put("id",cursor.getString(0));
                map.put("nama",cursor.getString(1));
                map.put("telpon",cursor.getString(2));
//                memasukan ke daftar teman
                daftarTeman.add(map);

            }while (cursor.moveToNext());
        }
        db.close();
        return daftarTeman;
    }

    public void editData(HashMap<String ,String> queryValues){
    SQLiteDatabase basisData = this.getWritableDatabase();
    ContentValues nilai = new ContentValues();

        nilai.put("nama", queryValues.get("nama"));
        nilai.put("telpon", queryValues.get("telpon"));

        basisData.update("teman", nilai, "id = ?", new String[]{queryValues.get("id")});
        basisData.close();
    }

    public  void hapusData(String id){
        SQLiteDatabase basisData = this.getWritableDatabase();
        basisData.delete("teman", "id = ?",new String[] {id});
        basisData.close();
    }

}