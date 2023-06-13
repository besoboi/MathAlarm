package com.example.mathalarm.presentation.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mathalarm.R
import com.example.mathalarm.databinding.FragmentAlarmItemBinding
import com.example.mathalarm.domain.AlarmItem
import com.example.mathalarm.presentation.ViewModelFactory
import com.example.mathalarm.presentation.adapters.AlarmApp
import java.time.DayOfWeek
import java.util.Calendar
import javax.inject.Inject

class AlarmItemFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as AlarmApp).component
    }

    private lateinit var viewModel: AlarmItemViewModel
    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var screenMode: String = MODE_UNKNOWN
    private var alarmItemId: Int = AlarmItem.UNDEFINED_ID

    private val calendar = Calendar.getInstance()

    private var _binding : FragmentAlarmItemBinding? = null
    private val binding: FragmentAlarmItemBinding
        get() = _binding ?: throw RuntimeException("binding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlarmItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[AlarmItemViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.timePickerAlarm.apply {
            setIs24HourView(true)
        }
        launchRightMode()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishedListener.OnEditingFinished()
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchAddMode() {
        binding.btSaveAlarm.setOnClickListener {
            setupCalendar()
            viewModel.addAlarmItem(
                binding.etAlarmName.text.toString(),
                calendar.timeInMillis,
                makeListOfDays()
            )
        }
    }

    private fun launchEditMode() {
        viewModel.getAlarmItem(alarmItemId)
        binding.btSaveAlarm.setOnClickListener {
            setupCalendar()
            viewModel.editAlarmItem(
                binding.etAlarmName.text.toString(),
                calendar.timeInMillis,
                makeListOfDays()
            )
        }
    }

    private fun makeListOfDays() : MutableList<DayOfWeek>? {
        val list = mutableListOf(
            binding.tbMonday,
            binding.tbTuesday,
            binding.tbWednesday,
            binding.tbThursday,
            binding.tbFriday,
            binding.tbSaturday,
            binding.tbSunday
        ).filter { it.isChecked }
        return if (list.isNotEmpty()) {
            list.map {
                when (it.id) {
                    R.id.tb_monday -> DayOfWeek.MONDAY
                    R.id.tb_tuesday -> DayOfWeek.TUESDAY
                    R.id.tb_wednesday -> DayOfWeek.WEDNESDAY
                    R.id.tb_thursday -> DayOfWeek.THURSDAY
                    R.id.tb_friday -> DayOfWeek.FRIDAY
                    R.id.tb_saturday -> DayOfWeek.SATURDAY
                    else -> DayOfWeek.SUNDAY
                }
            }.toMutableList()
        } else null
    }

    interface OnEditingFinishedListener{
        fun OnEditingFinished()
    }

    private fun setupCalendar(){
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.set(Calendar.MINUTE, binding.timePickerAlarm.minute)
        calendar.set(Calendar.HOUR_OF_DAY, binding.timePickerAlarm.hour)
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(EXTRA_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(EXTRA_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown param screen mode $mode")
        }

        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(ALARM_ITEM_ID)) {
                throw RuntimeException("Param alarm item id is absent")
            }
            alarmItemId = args.getInt(ALARM_ITEM_ID, AlarmItem.UNDEFINED_ID)
        }
    }

    companion object {
        const val EXTRA_MODE = "extra_mode"
        const val MODE_EDIT = "mode_edit"
        const val MODE_ADD = "mode_add"
        const val MODE_UNKNOWN = ""
        const val ALARM_ITEM_ID = "extra_alarm_item_id"

        fun newInstanceAddItem() : AlarmItemFragment {
            return AlarmItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_MODE, MODE_ADD)
                }
            }

        }

        fun newInstanceEditItem(alarmItemID: Int) : AlarmItemFragment {
            return AlarmItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_MODE, MODE_EDIT)
                    putInt(ALARM_ITEM_ID, alarmItemID)
                }
            }
        }

    }

}