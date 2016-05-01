package com.tutorials.hp.sqliteswipetodelete.mDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Oclemmy on 5/1/2016 for ProgrammingWizards Channel and http://www.Camposha.com.
 */
public class DBAdapter {

    Context c;
    SQLiteDatabase db;
    DBHelper helper;

    public DBAdapter(Context c) {
        this.c = c;
        helper=new DBHelper(c);
    }

    //OPEN DB
    public void openDB()
    {
        try {
            db=helper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //CLOSE DB
    public void closeDB()
    {
        try {
           helper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //INSERT/SAVE
    public boolean add(String name)
    {
        try {
        ContentValues cv=new ContentValues();
        cv.put(Constants.NAME, name);

         db.insert(Constants.TB_NAME, Constants.ROW_ID, cv);
        return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //SELECT/RETRIEVE
    public Cursor retrieve()
    {
        String[] columns={Constants.ROW_ID,Constants.NAME};

        return db.query(Constants.TB_NAME,columns,null,null,null,null,null);
    }

    //DELETE/REMOVE
    public boolean delete(int id)
    {
        try {
            int result=db.delete(Constants.TB_NAME,Constants.ROW_ID+" =?",new String[]{String.valueOf(id)});
            if(result>0) {
                return true;
            }

          } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}













