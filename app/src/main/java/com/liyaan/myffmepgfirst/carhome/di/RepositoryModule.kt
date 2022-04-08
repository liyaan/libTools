package com.liyaan.myffmepgfirst.carhome.di

import com.liyaan.myffmepgfirst.carhome.SubjectService
import com.liyaan.myffmepgfirst.carhome.mapper.EntityItemModelMapper
import com.liyaan.myffmepgfirst.carhome.repository.Repository
import com.liyaan.myffmepgfirst.carhome.repository.SubjectRepositoryImpl
import com.liyaan.myffmepgfirst.carhome.sdb.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@InstallIn(ActivityComponent::class)
@Module
object RepositoryModule {
    @ActivityScoped
    @Provides
    fun provideSubjectRepository(api:SubjectService,database: AppDatabase):Repository{
        return SubjectRepositoryImpl(api,database,EntityItemModelMapper())
    }
}