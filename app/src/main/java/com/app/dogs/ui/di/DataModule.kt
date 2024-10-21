package com.app.dogs.ui.di

import com.app.commondata.repository.DogsRepositoryImpl
import com.app.commondomain.repository.DogsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindDogsRepository(
        analyticsServiceImpl: DogsRepositoryImpl
    ): DogsRepository
}
