package com.example.everydaything.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ContractsDB(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val ENTRIES = "CREATE TABLE ${Entry.TABLE_NAME} (" +
        "${Entry._ID} INTEGER PRIMARY KEY," +
        "${Entry.TITLE_COL} TEXT," +
        "${Entry.DESC_COL} TEXT," +
        "${Entry.IMG_COL} BLOB" + ")"

    private val DEL_ENTRIES = "DROP TABLE IF EXISTS ${Entry.TABLE_NAME}"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DEL_ENTRIES)
        onCreate(db)
    }
}