package com.example.dataseaapp.dataSea.Model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.dataseaapp.dataSea.Note

class DbHelper(context: Context): SQLiteOpenHelper(context, Db.DATABASE_NAME, null, Db.DATABASE_VERSION) {
    // Создание базы данных
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(Db.CREATE_TABLE)
    }

    // Обновление базы данных
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(Db.DELETE_TABLE)
        onCreate(db)
    }

    fun insertToDb(note: Note) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(Db.COLUMN_TITLE, note.title)
            put(Db.COLUMN_CONTENT, note.content)
        }
        // Передаём в базу данных
        db.insert(Db.TABLE_NAME, null, values)
        db.close()
    }
}