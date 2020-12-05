package com.example.everydaything

import android.graphics.Bitmap

data class Habit (val title: String, val desc: String, val img: Bitmap)

//fun getSampleHabit(): List<Habit> {
//    return listOf(
//        Habit("Go for a Walk", "Everyday", R.drawable.ic_launcher_background),
//        Habit("Go for a Swim", "Once a week", R.drawable.ic_launcher_foreground),
//        Habit("Drink plenty of fluids", "Regularly on a daily basis", R.drawable.ic_launcher_background)
//    )
//}