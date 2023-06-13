package com.example.mathalarm.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathalarm.domain.AlarmItem
import com.example.mathalarm.domain.use_cases.DeleteAlarmItemUseCase
import com.example.mathalarm.domain.use_cases.EditAlarmItemUseCase
import com.example.mathalarm.domain.use_cases.GetAlarmItemListUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class AlarmListViewModel @Inject constructor (
    getAlarmListUseCase: GetAlarmItemListUseCase,
    private val deleteAlarmItemUseCase: DeleteAlarmItemUseCase,
    private val editAlarmItemUseCase: EditAlarmItemUseCase
) : ViewModel() {

    val alarmList = getAlarmListUseCase.getAlarmList()

    fun deleteAlarmItem(alarmItem: AlarmItem) {
        viewModelScope.launch {
            deleteAlarmItemUseCase.deleteAlarmItem(alarmItem)
        }

    }

    fun changeCheckedState(alarmItem: AlarmItem, checked: Boolean) {
        viewModelScope.launch {
            val newItem = alarmItem.copy(checked = checked)
            editAlarmItemUseCase.editAlarmItem(newItem)
        }
    }
}