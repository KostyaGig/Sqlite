package ru.kostya.sqlitetraining.db1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Students.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "students_table";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_SURNAME = "Surname";
    //Marks  (с.англ) - Метки
    public static final String COLUMN_MARKS = "Marks";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_NAME + " (" + COLUMN_ID + " integer primary key autoincrement, "
                + COLUMN_NAME + " text, "
                + COLUMN_SURNAME + " text, "
                + COLUMN_MARKS + " integer);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    //Метод для вставки данных в бд
    public boolean insertData(String name,String surname,int mark){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_SURNAME,surname);
        cv.put(COLUMN_MARKS,mark);

        //Если данные не вставились,то метод insert вернет нам - 1
        long result = db.insert(TABLE_NAME,null,cv);

        if (result == -1){
            return false;
        } else {
            return true;
        }

    }

    //Для получения всех данных из бд
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();

        //Этой строчкой мы говорим,что хотим получить все данные (это знак "*") из таблицы TABLE_NAME
        Cursor result = db.rawQuery("select * from " + TABLE_NAME,null);

        return result;
    }

    //Для обновления данных
    //Наверное id нам нужен в параметрах для того,чтобы обновлять данные по id,и добавлять их в cv
    public boolean updateData(String id,String name,String surname,int mark){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID,id);
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_SURNAME,surname);
        cv.put(COLUMN_MARKS,mark);

        //_id = ? - таким образом мы говорим,что будет обновлять данные по id
        //ВАЖНО название 3 параметра должно вопадать с column table,например
        //COLUMN_ID У МЕНЯ называется  _id,значит и в 3 параметре мы должны написать
        //_id = ?,именно с нижней чертой
        //В 4 параметре перечисляем id,значение которых мы хотим обновить,в моем случае - будет 1 id,
        //Который мы будем обновлять
        db.update(TABLE_NAME,cv,"_id = ?",new String[]{ id });
        return true;
    }

    //Для удаления данных
    //Будем удалять данные по id
    //_id = ? - Это я коментировал в методе updateData (75 строчка кода)
    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        //Возвращает сколько записей было удалено,но у нас будет удалена всегда 1 запись
        //Т.К мы передаем 1 id  в массив id'шников (3 параметр)
        return db.delete(TABLE_NAME,"_id = ?",new String[] {id});
    }

}
