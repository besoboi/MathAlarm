package com.example.mathalarm.domain

import androidx.lifecycle.LiveData

interface AlarmRepository {
    fun getAlarmList(): LiveData<List<AlarmItem>>
    suspend fun addAlarmItem(alarmItem: AlarmItem)
    suspend fun getItemByID(alarmItemID: Int): AlarmItem
    suspend fun editAlarmItem(alarmItem: AlarmItem)
    suspend fun deleteAlarmItem(alarmItem: AlarmItem)

}