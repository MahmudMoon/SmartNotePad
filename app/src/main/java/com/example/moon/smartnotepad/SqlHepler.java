package com.example.moon.smartnotepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import javax.xml.validation.Validator;

public class SqlHepler extends SQLiteOpenHelper {

    public SqlHepler(Context context) {
        super(context, DatabaseNames.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists " + DatabaseNames.TABLE_NAME  + " ( " + DatabaseNames.COL_ONE +
        " integer primary key autoincrement, " + DatabaseNames.COL_TWO + " text , "
                + DatabaseNames.COL_THREE + " text , " + DatabaseNames.COL_FOUR + " text )";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public long insert(ObjectForNotes objectForNotes){
        String date = objectForNotes.getDate();
        String title = objectForNotes.getTitle();
        String detail = objectForNotes.getTitle();

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseNames.COL_TWO,objectForNotes.getDate());
        contentValues.put(DatabaseNames.COL_THREE,objectForNotes.getTitle());
        contentValues.put(DatabaseNames.COL_FOUR,objectForNotes.getDetails());
        long inserted = sqLiteDatabase.insert(DatabaseNames.TABLE_NAME, null, contentValues);
        return inserted;

    }

    public Cursor read(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String sql = "select * from " + DatabaseNames.TABLE_NAME ;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        return cursor;
    }

    public int Update(int position,String title,String detail){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(DatabaseNames.COL_THREE,title);
        value.put(DatabaseNames.COL_FOUR,detail);
        int updated = sqLiteDatabase.update(DatabaseNames.TABLE_NAME, value, " id = ? ", new String[]{String.valueOf(position)});
        return updated;
    }

    public int delete(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {
            int deleted = sqLiteDatabase.delete(DatabaseNames.TABLE_NAME, " id = ? ", new String[]{String.valueOf(id)});
            return deleted;
        }catch (Exception e){
           Log.i("databaseUpdate",e.toString());
           return 0;
        }

    }



}
