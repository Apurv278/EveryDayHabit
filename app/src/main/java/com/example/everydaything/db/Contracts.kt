package com.example.everydaything.db

import android.provider.BaseColumns

val DATABASE_NAME = "EveryDayHabit.db"
val DATABASE_VERSION = 1

object Entry : BaseColumns {
    val TABLE_NAME = "Habit"
    val _ID = "ID"
    val TITLE_COL = "Title"
    val DESC_COL = "Description"
    val IMG_COL = "Image"
}
