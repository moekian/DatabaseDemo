package com.w20.databasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    // using Constants for column names

    private static final String DATABASE_NAME = "EmployeeDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "employees";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DEPT = "department";
    private static final String COLUMN_JOIN_DATE = "joiningdate";
    private static final String COLUMN_SALARY = "salary";

    public DatabaseHelper(@Nullable Context context) {
        // cursor factory is when you are using your own custom cursor
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER NOT NULL CONSTRAINT employee_pk PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " varchar(200) NOT NULL, " +
                COLUMN_DEPT + " varchar(200) NOT NULL, " +
                COLUMN_JOIN_DATE + " varchar(200) NOT NULL, " +
                COLUMN_SALARY + " double NOT NULL);";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        * we are just dropping the table and recreate it
        * */

        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);
    }

    boolean addEmployee(String name, String dept, String joiningDate, double salary) {

        // in order to insert items into database, we need a writable database
        // this method returns a SQLite database instance
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        // we need to define a contentValues instance
        ContentValues cv = new ContentValues();

        // the first argument of the put method is the column name and the second the value
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DEPT, dept);
        cv.put(COLUMN_JOIN_DATE, joiningDate);
        cv.put(COLUMN_SALARY, String.valueOf(salary));

        // the insert method returns row number if the insertion is successful and -1 if unsuccessful
        return sqLiteDatabase.insert(TABLE_NAME, null, cv) != -1;
//        return true;
    }

    Cursor getAllEmployees() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    boolean updateEmployee(int id, String name, String dept, double salary) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DEPT, dept);
        cv.put(COLUMN_SALARY, String.valueOf(salary));

        // this method returns the number of rows affected
        return sqLiteDatabase.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    boolean deleteEmployee(int id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        // the delete method returns the number of rows affected
        return sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

}
