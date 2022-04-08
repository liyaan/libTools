package com.liyaan.myffmepgfirst.carhome.di

import android.app.Application
import androidx.room.Room
import com.liyaan.myffmepgfirst.carhome.sdb.AppDatabase
import com.liyaan.myffmepgfirst.carhome.sdb.SubjectDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RoomModule {
    @Singleton
    @Provides
    fun provideAppDataBase(application: Application):AppDatabase{
        return Room.databaseBuilder(application,AppDatabase::class.java,"subject_list.db").build()
    }
    @Singleton
    @Provides
    fun provideSubjectDao(database: AppDatabase):SubjectDao = database.subjectDao()
}