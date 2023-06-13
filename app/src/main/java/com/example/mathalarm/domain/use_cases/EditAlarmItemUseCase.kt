package com.example.mathalarm.domain.use_cases

import com.example.mathalarm.domain.AlarmItem
import com.example.mathalarm.domain.AlarmRepository
import javax.inject.Inject

class EditAlarmItemUseCase @Inject constructor (
    private val alarmRepository: AlarmRepository
) {
    suspend fun editAlarmItem(alarmItem: AlarmItem) {
        alarmRepository.editAlarmItem(alarmItem)
    }
}