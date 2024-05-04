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

    fun getAllNotes(): List<Note>{
        val notesList = mutableListOf<Note>()
        val db = readableDatabase
        //Db.TABLE_NAME возможно что-то не то
        val query = "SELECT * FROM TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(Db.COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(Db.COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(Db.COLUMN_CONTENT))

            val note = Note(id, title, content)
            notesList.add(note)
        }
        cursor.close()
        db.close()
        return notesList
    }
}