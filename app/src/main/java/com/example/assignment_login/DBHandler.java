package com.example.assignment_login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.assignment_login.Model.LoginModel;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "users";
    private static final int DB_VERSION = 1;
    private static final String TB_NAME = "user_details";
    private static final String COL_ID = "id";
    private static final String COL_USER = "username";
    private static final String COL_PASS = "password";


    public DBHandler(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE "+TB_NAME+" ("+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_USER+" TEXT, "+COL_PASS+" TEXT);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TB_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean isUsernameExist(String userName) {
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.query(TB_NAME,new String[]{COL_ID,COL_USER,COL_PASS}, COL_USER+"=?",new String[]{userName},null,null,null);
        if (cursor != null && cursor.moveToFirst()){
            return true;
        }
        else
            return false;
    }

    public void addUser(String userName, String passWord) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER,userName);
        values.put(COL_PASS,passWord);
        db.insert(TB_NAME,null,values);
        db.close();
    }

    public LoginModel authenticate(LoginModel loginModel) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+COL_USER+", "+COL_PASS+" FROM "+TB_NAME,null);
        if (cursor != null && cursor.moveToFirst()){
            LoginModel loginModel1 = new LoginModel(cursor.getString(0), cursor.getString(1));

            if (loginModel.getUserName().equalsIgnoreCase(loginModel1.getUserName()) && loginModel.getPassWord().equalsIgnoreCase(loginModel1.getPassWord())){
                return loginModel1;
            }
        }
        return null;
    }
}
