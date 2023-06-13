package com.example.mathalarm.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathalarm.domain.AlarmItem
import com.example.mathalarm.domain.use_cases.AddAlarmItemUseCase
import com.example.mathalarm.domain.use_cases.EditAlarmItemUseCase
import com.example.mathalarm.domain.use_cases.GetItemByIDUseCase
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import javax.inject.Inject

class AlarmItemViewModel @Inject constructor (
    private val addAlarmItemUseCase: AddAlarmItemUseCase,
    private val editShopItemUseCase: EditAlarmItemUseCase,
    private val getItemByIDUseCase: GetItemByIDUseCase

) : ViewModel() {
    private val _alarmItem = MutableLiveData<AlarmItem>()
    val alarmItem: LiveData<AlarmItem>
        get() = _alarmItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getAlarmItem(alarmItemId: Int) {
        viewModelScope.launch {
            val item = getItemByIDUseCase.getItemByID(alarmItemId)
            _alarmItem.value = item
        }
    }

    fun addAlarmItem(inputName: String?, inputTime: Long, daysOfWeek: MutableList<DayOfWeek>?) {
        viewModelScope.launch {
            val newItem = AlarmItem(
                inputTime,
                parseName(inputName),
                daysOfWeek,
                false
            )
            addAlarmItemUseCase.addShopItem(newItem)
            finishScreen()
        }
    }

    fun editAlarmItem(inputName: String?, inputTime: Long, daysOfWeek: MutableList<DayOfWeek>?) {
       viewModelScope.launch {
           alarmItem.value?.let {
               val item = it.copy(
                   name = parseName(inputName),
                   time = inputTime,
                   daysOfWeek = daysOfWeek
               )
               editShopItemUseCase.editAlarmItem(item)
               finishScreen()
           }
       }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun finishScreen() {
        _shouldCloseScreen.value = Unit
    }


}