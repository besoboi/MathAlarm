package com.example.mathalarm.data

import androidx.room.TypeConverter
import java.time.DayOfWeek

private const val SEPARATOR = ","

class DaysConverter {


    @TypeConverter
    fun daysOfWeekToString(daysOfWeek: MutableList<DayOfWeek>?): String? =
        daysOfWeek?.map { it.value }?.joinToString(separator = SEPARATOR)

    @TypeConverter
    fun stringToDaysOfWeek(daysOfWeek: String?): MutableList<DayOfWeek>? =
        daysOfWeek?.split(SEPARATOR)?.map { DayOfWeek.of(it.toInt()) }?.toMutableList()
}