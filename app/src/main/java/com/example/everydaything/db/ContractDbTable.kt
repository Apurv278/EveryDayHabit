package com.example.everydaything.db

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.everydaything.Habit
import java.io.ByteArrayOutputStream

class ContractDbTable(context: Context) {

    private val dbHelper = ContractsDB(context)

    fun store(ht: Habit): Long {
        val db = dbHelper.writableDatabase

        val values = ContentValues()
        with(values) {
            put(Entry.TITLE_COL, ht.title)
            put(Entry.DESC_COL, ht.desc)
            put(Entry.IMG_COL, toByteArray(ht.img))
        }
        db.beginTransaction()
        val id = try {
            val iReturn = db.insert(Entry.TABLE_NAME, null, values)
            db.setTransactionSuccessful()
            iReturn
        } finally {
            db.endTransaction()
        }
        db.close()
        Log.d("ContractDbTable", "DB Table $ht")
        return id
    }

    fun readAll(): List<Habit> {

        val cl = arrayOf(Entry._ID, Entry.TITLE_COL, Entry.DESC_COL, Entry.IMG_COL)
        val asc = "${Entry._ID} ASC"
        val db = dbHelper.readableDatabase

        val cursor = db.query(Entry.TABLE_NAME, cl, null, null, null, null, asc)
        val ht = mutableListOf<Habit>()

        while (cursor.moveToNext()) {
            val t = cursor.getString(cursor.getColumnIndex(Entry.TITLE_COL))
            val des = cursor.getString(cursor.getColumnIndex(Entry.DESC_COL))
            val byte = cursor.getBlob(cursor.getColumnIndex(Entry.IMG_COL))
            val bit = BitmapFactory.decodeByteArray(byte, 0, byte.size)
            ht.add(Habit(t, des, bit))
        }
        cursor.close()
        return ht
    }

    private fun toByteArray(img: Bitmap): ByteArray {
        val s = ByteArrayOutputStream()
        img.compress(Bitmap.CompressFormat.PNG, 0, s)
        return s.toByteArray()
    }
}