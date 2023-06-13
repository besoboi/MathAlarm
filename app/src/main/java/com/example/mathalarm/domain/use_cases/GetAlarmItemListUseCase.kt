package com.example.mathalarm.domain.use_cases

import androidx.lifecycle.LiveData
import com.example.mathalarm.domain.AlarmItem
import com.example.mathalarm.domain.AlarmRepository
import javax.inject.Inject

class GetAlarmItemListUseCase @Inject constructor (
    private val alarmRepository: AlarmRepository
) {
    fun getAlarmList(): LiveData<List<AlarmItem>> {
        return alarmRepository.getAlarmList()
    }
}