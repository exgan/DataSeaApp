package com.example.dataseaapp.dataSea.Model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbUsersHelper(context: Context): SQLiteOpenHelper(context, DbUsers.DATABASE_NAME, null, DbUsers.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DbUsers.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(Db.DELETE_TABLE)
        onCreate(db)
    }

    fun insertUsers(username: String, password: String): Long {
        val values = ContentValues().apply {
            put(DbUsers.COLUMN_USERNAME, username)
            put(DbUsers.COLUMN_PASSWORD, password)
        }
        val db = writableDatabase
        return db.insert(DbUsers.TABLE_NAME, null, values)
    }

    fun readUser(username: String, password: String): Boolean {
        val db = readableDatabase
        val selection = "${DbUsers.COLUMN_USERNAME} = ? AND ${DbUsers.COLUMN_PASSWORD} = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db.query(DbUsers.TABLE_NAME, null, selection, selectionArgs, null, null, null)

        val userExists = cursor.count > 0
        cursor.close()
        return userExists
    }
}