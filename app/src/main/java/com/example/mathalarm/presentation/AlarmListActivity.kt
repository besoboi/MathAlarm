package com.example.mathalarm.presentation

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mathalarm.databinding.ActivityAlarmListBinding
import com.example.mathalarm.domain.AlarmReceiver
import com.example.mathalarm.presentation.adapters.AlarmApp
import com.example.mathalarm.presentation.adapters.AlarmListAdapter
import com.google.android.material.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import javax.inject.Inject

class AlarmListActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityAlarmListBinding.inflate(layoutInflater)
    }

    private lateinit var alarmListAdapter: AlarmListAdapter
    private lateinit var viewModel: AlarmListViewModel
    private lateinit var pendingIntent: PendingIntent
    private val alarmManager by lazy {
        getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as AlarmApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        checkPermissions()
        createNotificationChannel()
        viewModel = ViewModelProvider(this, viewModelFactory)[AlarmListViewModel::class.java]
        setupRecyclerView()
        setupFloatingActionButton()
        viewModel.alarmList.observe(this) {
            alarmListAdapter.submitList(it)
        }
    }

    private fun createNotificationChannel() {
        val name: CharSequence = "mathAlarmReminderChannel"
        val description = "Channel for Math Alarm"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = description
        val notificationManager = getSystemService(
            NotificationManager::class.java
        )

        notificationManager.createNotificationChannel(channel)
    }

    private fun setupRecyclerView() {
        with(binding.rvAlarmList) {
            alarmListAdapter = AlarmListAdapter(this@AlarmListActivity)
            adapter = alarmListAdapter
        }
        setupClickListener()
        setupSwipeListener(binding.rvAlarmList)
        setupCheckedStateListener()
    }

    private fun setupSwipeListener(rvAlarmList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = alarmListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteAlarmItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvAlarmList)
    }

    private fun setupClickListener() {
        alarmListAdapter.onAlarmClickListener = {
            val intent = AlarmItemActivity.newIntentEditItem(this, it.id)
            startActivity(intent)
        }
    }

    private fun setupFloatingActionButton() {
        binding.fabAddAlarm.setOnClickListener {
            val intent = AlarmItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }
    }

    private fun setupCheckedStateListener() {
        alarmListAdapter.onCheckedStateChangeListener = { alarmItem, checked ->
            viewModel.changeCheckedState(alarmItem, checked)
            if (checked) {
                setAlarm(time = alarmItem.time)
            } else {
                cancelAlarm()
            }
        }
    }


    private fun showSettingDialog() {
        MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_Material3)
            .setTitle("Notification Permission")
            .setMessage("Notification permission is required, Please allow notification permission from setting")
            .setPositiveButton("Ok") { _, _ ->
                val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    100
                )
            } else {
                showSettingDialog()
            }
            return
        }
    }

    private fun setAlarm(time: Long) {
        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val alarmClockInfo = AlarmManager.AlarmClockInfo(time, getAlarmInfoPendingIntent())
        alarmManager.setAlarmClock(
            alarmClockInfo,
            pendingIntent
        )
    }

    private fun cancelAlarm(){
        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.cancel(pendingIntent)
    }

    private fun getAlarmInfoPendingIntent(): PendingIntent {
        val alarmInfoIntent = Intent(this, AlarmListActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK

        }
        return PendingIntent.getActivity(this, 0, alarmInfoIntent, PendingIntent.FLAG_IMMUTABLE)

    }

    companion object {
        private const val CHANNEL_ID = "math_alarm_channel_ID"
    }

    //TODO: Повторение, звук у уведомления, попробовать передавать название, выключение будильника при финише фрагмента с задачей
}