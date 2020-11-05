package com.example.myapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;

public class Note implements NoteDataBaseHelper.TableCreateInterface {
    // 定义表名
    public static String tableName = "Note";
    // 定义各字段名
    public static String _id = "_id"; // _id是SQLite中自动生成的主键，用语标识唯一的记录，为了方便使用，此处定义对应字段名
    public static String title = "title"; // 标题
    public static String content = "content"; // 内容
    public static String time = "date"; // 时间
    //私有化构造方法
    private Note(){}
    //初始化实例
    private static com.example.myapp.Note note = new com.example.myapp.Note();
    //只提供一个实例
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


    // 插入
    public static void insertNote( NoteDataBaseHelper dbHelper, ContentValues userValues ) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert( com.example.myapp.Note.tableName, null, userValues );
        db.close();
    }

    // 删除一条话题
    public static void deleteNote( NoteDataBaseHelper dbHelper, int _id ) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(  com.example.myapp.Note.tableName, com.example.myapp.Note._id + "=?",new String[] { _id + "" }  );
        db.close();

    }

    // 删除所有
    public static void deleteAllNote( NoteDataBaseHelper dbHelper ) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(  com.example.myapp.Note.tableName, null, null  );
        db.close();
    }

    // 修改
    public static void updateNote( NoteDataBaseHelper dbHelper,  int _id, ContentValues infoValues ) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update(com.example.myapp.Note.tableName, infoValues, com.example.myapp.Note._id + " =? ", new String[]{ _id + "" });
        db.close();
    }

    // 以HashMap<String, Object>键值对的形式获取一条信息
    public static HashMap< String, Object > getNote(NoteDataBaseHelper dbHelper, int _id ){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        HashMap< String, Object > NoteMap = new HashMap< String, Object >();
        // 此处要求查询Note._id为传入参数_id的对应记录，使游标指向此记录
        Cursor cursor = db.query( com.example.myapp.Note.tableName, null, com.example.myapp.Note._id + " =? ", new String[]{ _id + "" }, null, null, null);
        cursor.moveToFirst();
        NoteMap.put(com.example.myapp.Note.title, cursor.getLong(cursor.getColumnIndex(com.example.myapp.Note.title)));
        NoteMap.put(com.example.myapp.Note.content, cursor.getString(cursor.getColumnIndex(com.example.myapp.Note.content)));
        NoteMap.put(com.example.myapp.Note.time, cursor.getString(cursor.getColumnIndex(com.example.myapp.Note.time)));

        return NoteMap;

    }

    // 获得查询指向话题表的游标
    public static Cursor getAllNotes(NoteDataBaseHelper dbHelper) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(com.example.myapp.Note.tableName, null, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

}