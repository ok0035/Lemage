package com.zerosword.data.di

import com.zerosword.data.services.KakaoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideMainServices(retrofit: Retrofit): KakaoService =
        retrofit.create(KakaoService::class.java)

}