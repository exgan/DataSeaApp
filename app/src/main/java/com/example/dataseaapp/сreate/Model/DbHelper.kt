package com.example.dataseaapp.сreate.Model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context): SQLiteOpenHelper(context, Db.DATABASE_NAME, null, Db.DATABASE_VERSION) {
    // Создание базы данных
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(Db.CREATE_TABLE)
    }

    // Обновление базы данных
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(Db.SQL_DELETE_TABLE)
        onCreate(db)
    }
}