package com.rifalcompany.comebackapps.features.home.di

import com.rifalcompany.comebackapps.features.home.data.remote.HomeRemoteDataSource
import com.rifalcompany.comebackapps.features.home.data.remote.HomeRemoteDataSourceImpl
import com.rifalcompany.comebackapps.features.home.data.repository.HomeRepository
import com.rifalcompany.comebackapps.features.home.data.repository.HomeRepositoryImpl
import com.rifalcompany.comebackapps.features.home.data.service.HomeService
import com.rifalcompany.comebackapps.retrofit.AppRetrofit
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class HomeModule {
    @Provides
    @Singleton
    fun provideRepository(homeRemoteDataSource: HomeRemoteDataSource): HomeRepository {
        return HomeRepositoryImpl(homeRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(homeService: HomeService): HomeRemoteDataSource {
        return HomeRemoteDataSourceImpl(homeService)
    }

    @Provides
    @Singleton
    fun provideHomeService(): HomeService {
        return AppRetrofit.homeService
    }
}