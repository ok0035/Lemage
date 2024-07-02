package com.zerosword.data.di

import com.zerosword.data.repositoryimpl.FavoriteRepositoryImpl
import com.zerosword.data.repositoryimpl.KakaoRepositoryImpl
import com.zerosword.domain.reporitory.FavoriteRepository
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
    abstract fun provideKakaoRepository(
        kakaoRepositoryImpl: KakaoRepositoryImpl
    ): KakaoRepository

    @Singleton
    @Binds
    abstract fun provideFavoriteRepository(
        favoriteRepositoryImpl: FavoriteRepositoryImpl
    ): FavoriteRepository

}