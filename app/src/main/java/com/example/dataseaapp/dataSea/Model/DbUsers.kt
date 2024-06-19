package com.example.dataseaapp.dataSea.Model

object DbUsers {
    const val TABLE_NAME = "date"
    const val COLUMN_ID = "id"
    const val COLUMN_USERNAME = "username"
    const val COLUMN_PASSWORD = "password"
    //0    username    password(таблица)

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "dateBaseUsers.db"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_USERNAME TEXT, $COLUMN_PASSWORD TEXT)"

    const val DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}