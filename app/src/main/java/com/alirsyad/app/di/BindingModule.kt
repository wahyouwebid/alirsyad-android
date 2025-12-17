package com.alirsyad.app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindingModule {
    @Binds
    abstract fun bindingRepository(
        dataRepository: com.alirsyad.app.data.repository.DataRepository
    ): com.alirsyad.app.data.repository.Repository
}