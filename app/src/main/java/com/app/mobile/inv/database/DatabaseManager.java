package com.app.mobile.inv.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public abstract class DatabaseManager {

    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public DatabaseManager(Context ctx){
        helper = new DatabaseHelper(ctx);
        db = helper.getWritableDatabase();
    }

    public void untuk_menutup(){
        db.close();
    }

    abstract public void menghapus(String id);
    abstract public void menghapus_semua();
    abstract public Cursor loadCursor();
    abstract boolean checkRegister(String id);

    public DatabaseHelper getHelper() {
        return helper;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setHelper(DatabaseHelper helper) {
        this.helper = helper;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

}

