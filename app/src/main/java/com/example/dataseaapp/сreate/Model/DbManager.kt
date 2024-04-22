package com.example.dataseaapp.сreate.Model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class DbManager(context: Context) {
    val DbHelper = DbHelper(context)
    // Открывать и закрывать
    var db: SQLiteDatabase? = null

    // открываем базу данных для записи
    fun openDb(){
        db = DbHelper.writableDatabase
    }

    // Записываем в базу данных
    fun insertToDb(title: String, content: String){
        val values = ContentValues().apply {
            put(Db.COLUMN_NAME_TITLE, title)
            put(Db.COLUMN_NAME_CONTENT, content)
        }
        // Передаём в базу данных
        db?.insert(Db.CREATE_TABLE, null, values)
    }

    // Считываем
    fun readDbData() : ArrayList<String>{
        val dataList = ArrayList<String>()
            val cursor = db?.query(Db.TABLE_NAME, null, null, null, null,
                null, null)

            with(cursor){
                while (this?.moveToNext()!!){
                    val dataText = cursor?.getString(cursor.getColumnIndexOrThrow(Db.COLUMN_NAME_TITLE))
                    dataList.add(dataText.toString())
                }
            }
        cursor?.close()
        return dataList
    }

    fun closeDb(){
        DbHelper.close()
    }
}