package com.example.dataseaapp.dataSea.Model


object Db {
    const val TABLE_NAME = "notes"
    const val COLUMN_ID = "id"
    const val COLUMN_TITLE = "title"
    const val COLUMN_CONTENT = "content"
    const val COLUMN_TIME = "time"
    //0    title    content(таблица)

    const val DATABASE_VERSION = 2
    const val DATABASE_NAME = "Notes.db"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT, $COLUMN_TIME TEXT)"

    const val DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}
