package com.example.mathalarm.domain

import java.time.DayOfWeek

data class AlarmItem(
    val time: Long,
    val name: String,
    val daysOfWeek: MutableList<DayOfWeek>?,
    val checked: Boolean,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}