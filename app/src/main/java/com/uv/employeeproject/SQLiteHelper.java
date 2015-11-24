package com.uv.employeeproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by venkatsr on 21/11/15.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "EMPLOYEE_DATABASE.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "EMPLOYEE";
    private static final String COL_EMPLOYEE_NAME = "EMPLOYEE_NAME";
    private static final String COL_EMPLOYEE_AGE = "EMPLOYEE_AGE";
    private static final String COL_EMPLOYEE_PHOTO = "EMPLOYEE_PHOTO";
    private static final String COL_EMPLOYEE_ID = "EMPLOYEE_ID";

    SQLiteDatabase database;

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.e("DBHelper", "Constructor executed");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("DBHelper", "Table Creation");
        String create_table_ddl = "CREATE TABLE " + TABLE_NAME + "(" + COL_EMPLOYEE_ID + " INTEGER PRIMARY KEY, " + COL_EMPLOYEE_NAME + " TEXT, " + COL_EMPLOYEE_AGE + " INTEGER, " + COL_EMPLOYEE_PHOTO + " BLOB " + ");";
        db.execSQL(create_table_ddl);
        Log.e("DBHelper", "Table Creation done" + create_table_ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertEmployeeRecord(Employee employee) {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EMPLOYEE_NAME, employee.getName());
        values.put(COL_EMPLOYEE_AGE, employee.getAge());
        values.put(COL_EMPLOYEE_PHOTO, employee.getImage());
        database.insert(TABLE_NAME, null, values);
        database.close();
    }

    public Employee getAnEmployee(String searchEmployeeName) {

        String searchEmployeesQuery = null;
        //String searchEmployeesQuery = "select " + COL_EMPLOYEE_NAME + ", " + COL_EMPLOYEE_AGE + ", " + COL_EMPLOYEE_PHOTO + " from " + TABLE_NAME + " where " + COL_EMPLOYEE_NAME + " = '" + searchEmployeeName + "'";
        //String searchEmployeesQuery = "select " + COL_EMPLOYEE_NAME + ", " + COL_EMPLOYEE_AGE + " from " + TABLE_NAME + " where " + COL_EMPLOYEE_NAME + " = '" + searchEmployeeName + "'";

        if(searchEmployeeName == null) {
            searchEmployeesQuery = "select " + COL_EMPLOYEE_NAME + ", " + COL_EMPLOYEE_AGE + " from " + TABLE_NAME;
        } else {
            searchEmployeesQuery = "select " + COL_EMPLOYEE_NAME + ", " + COL_EMPLOYEE_AGE + " from " + TABLE_NAME + " where " + COL_EMPLOYEE_NAME + " = '" + searchEmployeeName + "'";
        }
        Log.e("DBHelper", "Select Query: " + searchEmployeesQuery);
        Employee employee = null;
        database = getReadableDatabase();
        Log.e("DBHelper", "Before rawQuery");
        Cursor cursor = database.rawQuery(searchEmployeesQuery, null);
        Log.e("DBHelper", "After rawQuery: " + cursor.getCount());

        if(cursor.getCount() >0 && cursor.moveToFirst()) {
            Log.e("DBHelper", "Inside if");
            do {
                Log.e("DBHelper", "Inside do-while");
                String employeeName = cursor.getString(cursor.getColumnIndex(COL_EMPLOYEE_NAME));
                int employeeAge = cursor.getInt(cursor.getColumnIndex(COL_EMPLOYEE_AGE));
                /*byte[] photo = cursor.getBlob(cursor.getColumnIndex(COL_EMPLOYEE_AGE));*/
                employee = new Employee(employeeName, employeeAge, null);
                break;
            } while (false);
        } else {
            Log.w("SQLiteHelper", "No records available");
        }
        Log.e("DBHelper", "Done");
        cursor.close();
        database.close();
        return employee;
    }

    public ArrayList<Employee> getAllEmployees() {
        ArrayList<Employee> employees = new ArrayList<Employee>();


        String searchEmployeesQuery = "select " + COL_EMPLOYEE_NAME + ", " + COL_EMPLOYEE_AGE + " from " + TABLE_NAME;

        Log.e("DBHelper", "Select Query: " + searchEmployeesQuery);
        Employee employee = null;
        database = getReadableDatabase();
        Log.e("DBHelper", "Before rawQuery");
        Cursor cursor = database.rawQuery(searchEmployeesQuery, null);
        Log.e("DBHelper", "After rawQuery: " + cursor.getCount());

        if(cursor.getCount() >0 && cursor.moveToFirst()) {
            Log.e("DBHelper", "Inside if");
            do {
                Log.e("DBHelper", "Inside do-while");
                String employeeName = cursor.getString(cursor.getColumnIndex(COL_EMPLOYEE_NAME));
                int employeeAge = cursor.getInt(cursor.getColumnIndex(COL_EMPLOYEE_AGE));
                employee = new Employee(employeeName, employeeAge, null);
                employees.add(employee);
            } while (cursor.moveToNext());
        } else {
            Log.w("SQLiteHelper", "No records available");
        }
        Log.e("DBHelper", "Done");
        cursor.close();
        database.close();
        Log.e("DBHelper", "Number of employees: " + employees.size());
        return employees;
    }

    public String[] getAllEmployeeNames() {
        ArrayList<String> allEmployeeNames = new ArrayList<String>();
        database = getReadableDatabase();
        Log.e("DBHelper", "Before rawQuery");
        String selectProductsQuery = "select " + COL_EMPLOYEE_NAME + " from " + TABLE_NAME;
        Cursor cursor = database.rawQuery(selectProductsQuery, null);
        Log.e("DBHelper", "After rawQuery: " + cursor.getCount());
        /*String[] columnNames = cursor.getColumnNames();
        for(int i=0;i<columnNames.length; i++) {
            Log.e("SQLiteHelper-Column name: ", columnNames[i]);
        }*/
        if(cursor.getCount() >0 && cursor.moveToFirst()) {
            Log.e("DBHelper", "Inside if");
            do {
                Log.e("DBHelper", "Inside do-while");
                String employeeName = cursor.getString(cursor.getColumnIndex("EMPLOYEE_NAME"));
                allEmployeeNames.add(employeeName);
            } while (cursor.moveToNext());
        } else {
            Log.w("SQLiteHelper", "No records available");
        }
        Log.e("DBHelper", "Done");
        cursor.close();
        database.close();
        String[] stringArray = allEmployeeNames.toArray(new String[0]);
        return stringArray;
    }

    public boolean isTableExists(String tableName, boolean openDb) {
        if(openDb) {
            if(database == null || !database.isOpen()) {
                database = getReadableDatabase();
            }

            if(!database.isReadOnly()) {
                database.close();
                database = getReadableDatabase();
            }
        }

        Cursor cursor = database.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
}