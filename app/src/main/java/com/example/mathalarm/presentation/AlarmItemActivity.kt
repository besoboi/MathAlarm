package com.example.mathalarm.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mathalarm.R
import com.example.mathalarm.domain.AlarmItem
import com.example.mathalarm.presentation.ui.main.AlarmItemFragment

class AlarmItemActivity : AppCompatActivity(), AlarmItemFragment.OnEditingFinishedListener {

    private var screenMode = MODE_UNKNOWN
    private var alarmItemId = AlarmItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_item)
        parseIntent()
        if (savedInstanceState == null) {
            launchRightMode()
        }
    }

    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> AlarmItemFragment.newInstanceEditItem(alarmItemId)
            MODE_ADD -> AlarmItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("Unknown screen mode $screenMode")

        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.alarm_item_container, fragment)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown param screen mode $mode")
        }

        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_ALARM_ITEM_ID)) {
                throw RuntimeException("Param alarm item id is absent")
            }
            alarmItemId = intent.getIntExtra(EXTRA_ALARM_ITEM_ID, AlarmItem.UNDEFINED_ID)
        }
    }

    companion object {
        const val EXTRA_MODE = "extra_mode"
        const val MODE_EDIT = "mode_edit"
        const val MODE_ADD = "mode_add"
        const val MODE_UNKNOWN = ""
        const val EXTRA_ALARM_ITEM_ID = "extra_alarm_item_id"

        fun newIntentEditItem(context: Context, alarmItemID: Int): Intent {
            val intent = Intent(context, AlarmItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_ALARM_ITEM_ID, alarmItemID)
            return intent
        }

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, AlarmItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_ADD)
            return intent
        }
    }

    override fun OnEditingFinished() {
        finish()
    }
}