package com.example.myapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteDataBaseHelper extends SQLiteOpenHelper {

    public NoteDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //表创建接口 有多张表时 方便统一调用
    public static interface TableCreateInterface {
        //创建表
        public void onCreate( SQLiteDatabase db );

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        com.example.myapp.Note.getInstance().onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        com.example.myapp.Note.getInstance().onUpgrade(db,oldVersion,newVersion);
    }
}
