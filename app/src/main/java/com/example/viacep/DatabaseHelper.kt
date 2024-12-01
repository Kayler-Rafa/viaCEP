package com.example.viacep

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE $TABLE_NAME (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "cep TEXT," +
                    "street TEXT," +
                    "neighborhood TEXT," +
                    "city TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertAddress(cep: String, street: String, neighborhood: String, city: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("cep", cep)
            put("street", street)
            put("neighborhood", neighborhood)
            put("city", city)
        }
        db.insert(TABLE_NAME, null, values)
    }

    fun getAllAddresses(): List<String> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val addresses = mutableListOf<String>()

        cursor.use {
            while (it.moveToNext()) {
                val cep = it.getString(it.getColumnIndexOrThrow("cep"))
                val street = it.getString(it.getColumnIndexOrThrow("street"))
                val neighborhood = it.getString(it.getColumnIndexOrThrow("neighborhood"))
                val city = it.getString(it.getColumnIndexOrThrow("city"))
                addresses.add("CEP: $cep, Rua: $street, Bairro: $neighborhood, Cidade: $city")
            }
        }
        return addresses
    }

    fun clearAll() {
        writableDatabase.execSQL("DELETE FROM $TABLE_NAME")
    }

    companion object {
        const val DATABASE_NAME = "viacep.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "addresses"
    }
}
