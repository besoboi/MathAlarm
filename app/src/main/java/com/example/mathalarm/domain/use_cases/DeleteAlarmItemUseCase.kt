package com.example.mathalarm.domain.use_cases

import com.example.mathalarm.domain.AlarmItem
import com.example.mathalarm.domain.AlarmRepository
import javax.inject.Inject

class DeleteAlarmItemUseCase @Inject constructor (
    private val alarmRepository: AlarmRepository
) {
    suspend fun deleteAlarmItem(alarmItem: AlarmItem) {
        alarmRepository.deleteAlarmItem(alarmItem)
    }
}