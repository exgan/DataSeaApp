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
            put(Db.COLUMN_TIME, note.time)
            put(Db.COLUMN_PRIORITY, note.priority)
        }
        // Передаём в базу данных
        db.insert(Db.TABLE_NAME, null, values)
        db.close()
    }

    fun getAllNotes(): List<Note>{
        val notesList = mutableListOf<Note>()
        val db = readableDatabase
        val query = "SELECT * FROM ${Db.TABLE_NAME}"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(Db.COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(Db.COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(Db.COLUMN_CONTENT))
            val time = cursor.getString(cursor.getColumnIndexOrThrow(Db.COLUMN_TIME))
            val priority = cursor.getString(cursor.getColumnIndexOrThrow(Db.COLUMN_PRIORITY))

            val note = Note(id, title, content, time, priority)
            notesList.add(note)
        }
        cursor.close()
        db.close()
        return notesList
    }

    fun updateNote(note: Note){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(Db.COLUMN_TITLE, note.title)
            put(Db.COLUMN_CONTENT, note.content)
            put(Db.COLUMN_TIME, note.time)
            put(Db.COLUMN_PRIORITY, note.priority)
        }

        // Переменная используется для идентификации строк, которые будут обнолены по идентификатору столбца
        val whereClause = "${Db.COLUMN_ID} = ?"
        // Инициализируем массив, содержащий аргумент, который являеся идентифик. примечания
        val whereArgs = arrayOf(note.id.toString())
        // Обновляем данные
        db.update(Db.TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getNoteByID(noteId: Int): Note{
        val db = readableDatabase
        val query = "SELECT * FROM ${Db.TABLE_NAME} WHERE ${Db.COLUMN_ID} = $noteId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(Db.COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(Db.COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(Db.COLUMN_CONTENT))
        val time = cursor.getString(cursor.getColumnIndexOrThrow(Db.COLUMN_TIME))
        val priority = cursor.getString(cursor.getColumnIndexOrThrow(Db.COLUMN_PRIORITY))

        cursor.close()
        db.close()
        return Note(id, title, content, time, priority)
    }

    fun deleteNote(noteId: Int){
        val db = writableDatabase
        val whereClause = "${Db.COLUMN_ID} = ?"
        val whereArgs = arrayOf(noteId.toString())
        db.delete(Db.TABLE_NAME, whereClause, whereArgs)
        db.close()
    }
}