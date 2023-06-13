package com.example.mathalarm.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.mathalarm.domain.AlarmItem

object AlarmItemDiffCallback : DiffUtil.ItemCallback<AlarmItem>() {
    override fun areItemsTheSame(oldItem: AlarmItem, newItem: AlarmItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AlarmItem, newItem: AlarmItem): Boolean {
        return oldItem == newItem
    }
}