package com.example.mathalarm.domain.use_cases

import com.example.mathalarm.domain.AlarmItem
import com.example.mathalarm.domain.AlarmRepository
import javax.inject.Inject

class GetItemByIDUseCase @Inject constructor (
    private val alarmRepository: AlarmRepository
) {
    suspend fun getItemByID(alarmItemID: Int) : AlarmItem {
        return alarmRepository.getItemByID(alarmItemID)
    }
}