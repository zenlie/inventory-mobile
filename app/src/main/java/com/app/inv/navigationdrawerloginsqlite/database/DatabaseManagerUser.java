package com.app.inv.navigationdrawerloginsqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import com.app.inv.navigationdrawerloginsqlite.entity.User;
import java.util.ArrayList;
import java.util.List;


public class DatabaseManagerUser extends DatabaseManager {

    private static final String TABLE_NAME = "demo";
    private static final String CN_ID = "_id";
    private static final String CN_EMAIL = "correo";
    private static final String CN_PASSWORD = "password";
    private static final String CN_IMAGE = "imagen";
    private static final String CN_NAME = "nombre";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + " ("
            + CN_ID + " integer PRIMARY KEY AUTOINCREMENT, "
            + CN_EMAIL + " text NULL, "
            + CN_PASSWORD + " text NULL, "
            + CN_IMAGE + " BLOB NULL, "
            + CN_NAME + " text NOT NULL "
            + ");";

    public DatabaseManagerUser(Context ctx) {
        super(ctx);
    }

    @Override
    public void close() {
        super.getDb().close();
    }

    private ContentValues generarContentValues(String id, String correo, String password, byte[] imagen, String nombre){
        ContentValues valores = new ContentValues();
        valores.put(CN_ID, id);
        valores.put(CN_EMAIL, correo);
        valores.put(CN_PASSWORD, password);
        valores.put(CN_IMAGE, imagen);
        valores.put(CN_NAME, nombre);

        return valores;
    }


    public void insertar_parametros(String id, String correo, String password, byte[] imagen, String nombre) {
        Log.d("usuario_insertar", super.getDb().insert(TABLE_NAME,null,generarContentValues(id, correo, password, imagen, nombre))+"");
    }

    public void actualizar_parametros(String id, String correo, String pass, byte[] imagen, String nombre) {

        ContentValues valores = new ContentValues();
        valores.put(CN_ID, id);
        valores.put(CN_EMAIL, correo);
        valores.put(CN_PASSWORD, pass);
        valores.put(CN_IMAGE, imagen);
        valores.put(CN_NAME, nombre);

        String [] args = new String[]{id};

        Log.d("actualizar", super.getDb().update(TABLE_NAME, valores,"_ID=?", args)+"");
    }

    @Override
    public void remove(String id) {

        super.getDb().delete(TABLE_NAME, CN_ID +"=?", new String[]{id});
    }

    @Override
    public void removeAll() {

        super.getDb().execSQL("DELETE FROM "+ TABLE_NAME+";");
    }

    @Override
    public Cursor loadCursor() {

        String [] columnas = new String[]{CN_ID, CN_EMAIL, CN_PASSWORD, CN_IMAGE, CN_NAME};

        return super.getDb().query(TABLE_NAME, columnas, null, null, null, null, null);
    }

    @Override
    public boolean checkRegister(String correo) {

        boolean esta;
        Cursor resultSet = super.getDb().rawQuery("SELECT correo FROM demo" + " WHERE correo='"+correo+"'", null);

        if(resultSet.getCount()<=0){
            esta = false;
        }else{
            esta = true;
        }
        return esta;
    }

    public List<User> getUsuariosList(){

        List<User> list = new ArrayList<>();
        Cursor c = loadCursor();

        while (c.moveToNext()){
            User usuario = new User();

            usuario.setId(c.getString(0));
            usuario.setCorreo(c.getString(1));
            usuario.setPassword(c.getString(2));
            usuario.setBytes(c.getBlob(3));
            usuario.setNombre(c.getString(4));
            //usuario.setActive(false);

            list.add(usuario);
        }

        return list;
    }

    public User getUsuario(String ident){

        Cursor c1 = super.getDb().rawQuery("SELECT _id, correo, password, imagen, nombre FROM demo WHERE correo" + "='" + ident+ "'", null);

        User user = new User();

        c1.moveToNext();

        user.setId(c1.getString(0));
        user.setCorreo(c1.getString(1));
        user.setPassword(c1.getString(2));
        user.setBytes(c1.getBlob(3));
        user.setNombre(c1.getString(4));
        return user;
    }
}
