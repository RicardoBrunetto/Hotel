package com.pet.hotel.dados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class HotelController {

    private HotelSQLHelper helper;

    public HotelController(Context ctx) {
        helper = new HotelSQLHelper(ctx);
    }

    public long inserir(Hotel hotel) {

        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(HotelSQLHelper.COLUNA_NOME, hotel.nome);
        cv.put(HotelSQLHelper.COLUNA_ENDERECO, hotel.endereco);
        cv.put(HotelSQLHelper.COLUNA_ESTRELAS, hotel.estrelas);

        long id = db.insert(HotelSQLHelper.TABELA_HOTEL, null, cv);

        if (id != -1) {
            hotel.id = id;
        }

        db.close();

        return id;

    }

    public List<Hotel> buscarHotel() {

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + HotelSQLHelper.TABELA_HOTEL + " ORDER BY " + HotelSQLHelper.COLUNA_NOME, null);
        List<Hotel> hoteis = new ArrayList<>();

        while (cursor.moveToNext()) {

            long id = cursor.getLong(cursor.getColumnIndex(HotelSQLHelper.COLUNA_ID));
            String nome = cursor.getString(cursor.getColumnIndex(HotelSQLHelper.COLUNA_NOME));
            String endereco = cursor.getString(cursor.getColumnIndex(HotelSQLHelper.COLUNA_ENDERECO));
            float estrelas = cursor.getFloat(cursor.getColumnIndex(HotelSQLHelper.COLUNA_ESTRELAS));

            Hotel hotel = new Hotel(id, nome, endereco, estrelas);
            hoteis.add(hotel);

        }

        cursor.close();
        db.close();

        return hoteis;

    }

}
