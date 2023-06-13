package com.example.mathalarm.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.DayOfWeek

@Entity(tableName = "alarm_items")
@TypeConverters(DaysConverter::class)
data class AlarmItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val time: Long,
    val name: String,
    val daysOfWeek: MutableList<DayOfWeek>?,
    val checked: Boolean,
)
