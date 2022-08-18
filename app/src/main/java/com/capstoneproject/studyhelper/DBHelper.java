package com.capstoneproject.studyhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    DBHelper(Context context)
    {
        super(context,"studyhelper.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table Users(Name VARCHAR,Pass VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists Users");
    }
    public Boolean NewUser(String Name, String Pass)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put("Name",Name);
        values.put("Pass",Pass);
        long result = db.insert("Users",null,values);
        Cursor cur =db.rawQuery("select * from Users where Name = ?",new String[] {Name});
        if(cur.getCount()==0)
        {
            if(result == -1)
                return false;
            else
                return true;
        }
        else
        {
            return false;
        }

    }
    public Boolean ExistingUser(String Name, String Pass)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur =db.rawQuery("select * from Users where Name = ?",new String[] {Name});
        if(cur.getCount() == 0)
        {
            return false;
        }
        else
        {
            cur.moveToNext();
            String matchpass = cur.getString(1);
            if(Pass.equals(matchpass))
            {
                return true;
            }
            else
                return false;
        }
    }
    public StringBuffer UserDetails()
    {
        StringBuffer users = new StringBuffer();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("select * from Users",null);
        if(cur.getCount() == 0)
        {
            users.append("No User data found");
        }
        else
        {
            int i = 1;
            while(cur.moveToNext())
            {
                users.append(i+" "+cur.getString(0)+"\n");
                i++;
            }
        }
        return users;
    }
}
