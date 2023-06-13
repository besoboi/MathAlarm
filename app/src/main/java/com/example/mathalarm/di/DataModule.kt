package com.example.mathalarm.di

import android.app.Application
import com.example.mathalarm.data.AlarmListDao
import com.example.mathalarm.data.AlarmListRepositoryImpl
import com.example.mathalarm.data.AppDatabase
import com.example.mathalarm.data.QuestionRepositoryImpl
import com.example.mathalarm.domain.AlarmRepository
import com.example.mathalarm.domain.QuestionRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @Binds
    @ApplicationScope
    fun bindAlarmListRepository(impl: AlarmListRepositoryImpl) : AlarmRepository

    @Binds
    @ApplicationScope
    fun bindQuestionRepository(impl: QuestionRepositoryImpl) : QuestionRepository

    companion object {
        @Provides
        @ApplicationScope
        fun provideAlarmListDao(
            application: Application
        ): AlarmListDao {
            return AppDatabase.getInstance(application).alarmListDao()
        }
    }

}