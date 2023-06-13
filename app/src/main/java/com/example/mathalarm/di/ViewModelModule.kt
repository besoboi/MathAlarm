package com.example.mathalarm.di

import androidx.lifecycle.ViewModel
import com.example.mathalarm.presentation.AlarmListViewModel
import com.example.mathalarm.presentation.ui.main.AlarmItemViewModel
import com.example.mathalarm.presentation.ui.main.MathViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AlarmListViewModel::class)
    fun bindAlarmListViewModel(viewModel: AlarmListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AlarmItemViewModel::class)
    fun bindAlarmItemViewModel(viewModel: AlarmItemViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MathViewModel::class)
    fun bindMathViewModel(viewModel: MathViewModel): ViewModel
}