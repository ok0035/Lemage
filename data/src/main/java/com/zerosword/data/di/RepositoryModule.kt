package com.zerosword.data.di

import com.zerosword.data.repositoryimpl.KakaoRepositoryImpl
import com.zerosword.domain.reporitory.KakaoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideMainRepository(kakaoRepositoryImpl: KakaoRepositoryImpl): KakaoRepository

}