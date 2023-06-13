package com.example.mathalarm.di

import android.app.Application
import com.example.mathalarm.presentation.AlarmListActivity
import com.example.mathalarm.presentation.adapters.AlarmApp
import com.example.mathalarm.presentation.ui.main.AlarmItemFragment
import com.example.mathalarm.presentation.ui.main.MathFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [DataModule::class, ViewModelModule::class]
)
@ApplicationScope
interface ApplicationComponent {

    fun inject(application: AlarmApp)

    fun inject(activity: AlarmListActivity)

    fun inject(fragment: AlarmItemFragment)

    fun inject(fragment: MathFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}