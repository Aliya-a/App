package com.example.myapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;

public class Note implements NoteDataBaseHelper.TableCreateInterface {
    public static String tableName = "Note";
    public static String _id = "_id";
    public static String title = "title";
    public static String content = "content";
    public static String time = "date";

    private Note(){}

    private static com.example.myapp.Note note = new com.example.myapp.Note();

    public static com.example.myapp.Note getInstance(){
        return note;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "
                + com.example.myapp.Note.tableName
                + " (  "
                + "_id integer primary key autoincrement, "
                + com.example.myapp.Note.title + " TEXT, "
                + com.example.myapp.Note.content + " TEXT, "
                + com.example.myapp.Note.time + " TEXT "
                + ");";
        db.execSQL( sql );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if ( oldVersion < newVersion ) {
            String sql = "DROP TABLE IF EXISTS " + com.example.myapp.Note.tableName;
            db.execSQL( sql );
            this.onCreate( db );
        }
    }

    public static void insertNote( NoteDataBaseHelper dbHelper, ContentValues userValues ) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert( com.example.myapp.Note.tableName, null, userValues );
        db.close();
    }

    public static void deleteNote( NoteDataBaseHelper dbHelper, int _id ) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(  com.example.myapp.Note.tableName, com.example.myapp.Note._id + "=?",new String[] { _id + "" }  );
        db.close();

    }

    public static void deleteAllNote( NoteDataBaseHelper dbHelper ) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(  com.example.myapp.Note.tableName, null, null  );
        db.close();
    }

    public static void updateNote( NoteDataBaseHelper dbHelper,  int _id, ContentValues infoValues ) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update(com.example.myapp.Note.tableName, infoValues, com.example.myapp.Note._id + " =? ", new String[]{ _id + "" });
        db.close();
    }
    public static HashMap< String, Object > getNote(NoteDataBaseHelper dbHelper, int _id ){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        HashMap< String, Object > NoteMap = new HashMap< String, Object >();
        Cursor cursor = db.query( com.example.myapp.Note.tableName, null, com.example.myapp.Note._id + " =? ", new String[]{ _id + "" }, null, null, null);
        cursor.moveToFirst();
        NoteMap.put(com.example.myapp.Note.title, cursor.getLong(cursor.getColumnIndex(com.example.myapp.Note.title)));
        NoteMap.put(com.example.myapp.Note.content, cursor.getString(cursor.getColumnIndex(com.example.myapp.Note.content)));
        NoteMap.put(com.example.myapp.Note.time, cursor.getString(cursor.getColumnIndex(com.example.myapp.Note.time)));

        return NoteMap;

    }

    public static Cursor getAllNotes(NoteDataBaseHelper dbHelper) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(com.example.myapp.Note.tableName, null, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

}
