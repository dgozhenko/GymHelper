package com.dh.gymhelper.di

import com.dh.gymhelper.data.datasource.UserDataSource
import com.dh.gymhelper.data.datasource.UserDataSourceImpl
import com.dh.gymhelper.data.network.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatasourceModule {

    @Singleton
    @Provides
    fun provideUserDataSource(api: Api): UserDataSource {
        return UserDataSourceImpl(api)
    }
}