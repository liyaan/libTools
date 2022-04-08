package com.liyaan.myffmepgfirst.carhome.di

import android.util.Log
import com.liyaan.myffmepgfirst.carhome.SubjectService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object NetWorkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient():OkHttpClient = OkHttpClient.Builder().build()
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient):Retrofit{
        val interceptor = HttpLoggingInterceptor(object:HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
                Log.i("data",message)
            }
        })
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://www.wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideSubjectService(retrofit: Retrofit):SubjectService = retrofit.create(SubjectService::class.java)
}