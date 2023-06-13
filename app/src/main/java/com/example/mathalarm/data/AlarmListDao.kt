package com.example.mathalarm.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AlarmListDao {
    @Query("SELECT * FROM alarm_items")
    fun getAlarmList() : LiveData<List<AlarmItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAlarmItem(alarmItemDbModel: AlarmItemDbModel)

    @Query("DELETE FROM alarm_items WHERE id =:alarmItemId")
    suspend fun deleteAlarmItem(alarmItemId : Int)

    @Query("SELECT * FROM alarm_items WHERE id =:alarmItemId LIMIT 1")
    suspend fun getAlarmItem(alarmItemId: Int) : AlarmItemDbModel
}