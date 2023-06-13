package com.example.mathalarm.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.mathalarm.R
import com.example.mathalarm.databinding.ItemAlarmBinding
import com.example.mathalarm.domain.AlarmItem
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Calendar

class AlarmListAdapter(private val context: Context) :
    ListAdapter<AlarmItem, AlarmItemViewHolder>(AlarmItemDiffCallback) {

    var onAlarmClickListener: ((AlarmItem) -> Unit)? = null

    var onCheckedStateChangeListener: ((AlarmItem, Boolean) -> Unit)? = null

    private val calendar = Calendar.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmItemViewHolder {
        val binding = ItemAlarmBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return AlarmItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlarmItemViewHolder, position: Int) {
        val alarmItem = getItem(position)
        val binding = holder.binding

        binding.root.setOnClickListener {
            onAlarmClickListener?.invoke(alarmItem)
        }
        binding.switchActivateAlarm.isChecked = alarmItem.checked
        binding.tvAlarmName.text = alarmItem.name
        binding.tvAlarmTime.text = convertMillisToHoursAndMinutes(alarmItem.time)
        if (alarmItem.daysOfWeek != null) {
            binding.tvDays.text = alarmItem.daysOfWeek.joinToString(", ") {
                it.name.take(3)
                    .lowercase()
                    .replaceFirstChar(Char::titlecase)
            }
        }
        binding.ivAlarmBackground.setBackgroundResource(
            if (alarmItem.checked) {
                R.drawable.ic_active_background_gradient
            } else {
                R.drawable.ic_inactive_background_gradient
            }
        )
        binding.tvAlarmRemaining.text = getRemainingTime(alarmItem.time)
        binding.switchActivateAlarm.setOnCheckedChangeListener { buttonView, checked ->
            if (buttonView?.isPressed == true)
                onCheckedStateChangeListener?.invoke(alarmItem, checked)
        }
    }

    private fun convertMillisToHoursAndMinutes(millis: Long): String {
        calendar.timeInMillis = millis
        val minutes = String.format("%02d", calendar.get(Calendar.MINUTE))
        val hours = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY))
        return "$hours:$minutes"
    }

    private fun getRemainingTime(scheduledTimeMillis: Long): String {
        calendar.timeInMillis = scheduledTimeMillis
        val minutes = calendar.get(Calendar.MINUTE)
        val hours = calendar.get(Calendar.HOUR_OF_DAY)

        val currentDateTime = LocalDateTime.now()
        var scheduledDateTime = LocalDateTime.of(LocalDate.now(),LocalTime.of(hours, minutes))

        if (currentDateTime.isAfter(scheduledDateTime)) {
            scheduledDateTime = LocalDateTime.of(LocalDate.now().plusDays(1),LocalTime.of(hours, minutes))
        }

        val duration = Duration.between(currentDateTime, scheduledDateTime)

        val remainingHours = String.format("%02d", duration.toHours())
        val remainingMinutes = String.format("%02d", duration.minusHours(duration.toHours()).toMinutes())
        return "${remainingHours}:${remainingMinutes}"
    }
}