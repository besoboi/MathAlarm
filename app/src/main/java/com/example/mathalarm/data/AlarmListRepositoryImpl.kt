package com.example.mathalarm.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.mathalarm.domain.AlarmItem
import com.example.mathalarm.domain.AlarmRepository
import javax.inject.Inject

class AlarmListRepositoryImpl @Inject constructor(
    private val application: Application,
    private val alarmListDao: AlarmListDao,
    private val mapper: AlarmListMapper
) : AlarmRepository {

    override fun getAlarmList(): LiveData<List<AlarmItem>> = MediatorLiveData<List<AlarmItem>>()
        .apply {
            addSource(alarmListDao.getAlarmList()) {
                value = mapper.mapListDbModelToListAlarmItem(it)
            }
        }

    override suspend fun addAlarmItem(alarmItem: AlarmItem) {
        alarmListDao.addAlarmItem(mapper.mapEntityToDbModel(alarmItem))
    }

    override suspend fun getItemByID(alarmItemID: Int): AlarmItem {
        return mapper.mapDbModelToEntity(alarmListDao.getAlarmItem(alarmItemID))
    }

    override suspend fun editAlarmItem(alarmItem: AlarmItem) {
        alarmListDao.addAlarmItem(mapper.mapEntityToDbModel(alarmItem))
    }

    override suspend fun deleteAlarmItem(alarmItem: AlarmItem) {
        alarmListDao.deleteAlarmItem(alarmItem.id)
    }
}