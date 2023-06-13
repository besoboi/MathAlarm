package com.example.mathalarm.presentation

import android.widget.TimePicker
import android.widget.ToggleButton
import androidx.databinding.BindingAdapter
import java.time.DayOfWeek
import java.util.Calendar

@BindingAdapter("tb_monday")
fun bindTbMonday(toggleButton: ToggleButton, days: MutableList<DayOfWeek>?) {
    if (days != null) {
        for (day in days) {
            if (day == DayOfWeek.MONDAY) toggleButton.isChecked = true
        }
    }
}

@BindingAdapter("tb_tuesday")
fun bindTbTuesday(toggleButton: ToggleButton, days: MutableList<DayOfWeek>?) {
    if (days != null) {
        for (day in days) {
            if (day == DayOfWeek.TUESDAY) toggleButton.isChecked = true
        }
    }
}

@BindingAdapter("tb_wednesday")
fun bindTbWednesday(toggleButton: ToggleButton, days: MutableList<DayOfWeek>?) {
    if (days != null) {
        for (day in days) {
            if (day == DayOfWeek.WEDNESDAY) toggleButton.isChecked = true
        }
    }
}

@BindingAdapter("tb_thursday")
fun bindTbThursday(toggleButton: ToggleButton, days: MutableList<DayOfWeek>?) {
    if (days != null) {
        for (day in days) {
            if (day == DayOfWeek.THURSDAY) toggleButton.isChecked = true
        }
    }
}

@BindingAdapter("tb_friday")
fun bindTbFriday(toggleButton: ToggleButton, days: MutableList<DayOfWeek>?) {
    if (days != null) {
        for (day in days) {
            if (day == DayOfWeek.FRIDAY) toggleButton.isChecked = true
        }
    }
}

@BindingAdapter("tb_saturday")
fun bindTbSaturday(toggleButton: ToggleButton, days: MutableList<DayOfWeek>?) {
    if (days != null) {
        for (day in days) {
            if (day == DayOfWeek.SATURDAY) toggleButton.isChecked = true
        }
    }
}

@BindingAdapter("tb_sunday")
fun bindTbSunday(toggleButton: ToggleButton, days: MutableList<DayOfWeek>?) {
    if (days != null) {
        for (day in days) {
            if (day == DayOfWeek.SUNDAY) toggleButton.isChecked = true
        }
    }
}

@BindingAdapter("set_time_picker_time")
fun bindTimePicker(timePicker: TimePicker, time: Long){
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = time
    timePicker.hour = calendar.get(Calendar.HOUR_OF_DAY)
    timePicker.minute = calendar.get(Calendar.MINUTE)
}
