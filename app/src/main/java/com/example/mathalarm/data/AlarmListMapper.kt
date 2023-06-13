package com.example.mathalarm.data

import com.example.mathalarm.domain.AlarmItem
import javax.inject.Inject

class AlarmListMapper @Inject constructor() {
    fun mapEntityToDbModel(alarmItem: AlarmItem): AlarmItemDbModel = AlarmItemDbModel(
        id = alarmItem.id,
        time = alarmItem.time,
        name = alarmItem.name,
        daysOfWeek = alarmItem.daysOfWeek,
        checked = alarmItem.checked
    )

    fun mapDbModelToEntity(alarmItemDbModel: AlarmItemDbModel): AlarmItem = AlarmItem(
        id = alarmItemDbModel.id,
        time = alarmItemDbModel.time,
        name = alarmItemDbModel.name,
        daysOfWeek = alarmItemDbModel.daysOfWeek,
        checked = alarmItemDbModel.checked
    )

    fun mapListDbModelToListAlarmItem(list: List<AlarmItemDbModel>) = list.map{
        mapDbModelToEntity(it)
    }
}