package ru.kostya.sqlitetraining.dbRecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HelperRecyclerView extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Students.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "students_table";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_SURNAME = "Surname";
    //Marks  (с.англ) - Метки
    public static final String COLUMN_MARKS = "Marks";

    public HelperRecyclerView(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_NAME + " (" + COLUMN_ID + " integer primary key autoincrement, "
                + COLUMN_NAME + " text, "
                + COLUMN_SURNAME + " text, "
                + COLUMN_MARKS + " text);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String name,String surname,String  mark){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_SURNAME,surname);
        cv.put(COLUMN_MARKS,mark);

        long inserted = db.insert(TABLE_NAME,null,cv);
        if (inserted == -1){
            return false;
        } else {
            return true;
        }

    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery("select * from " + TABLE_NAME,null);
        return result;
    }

}
