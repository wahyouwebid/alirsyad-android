package com.alirsyad.app.di

import android.content.Context
import com.alirsyad.app.data.database.RoomDB
import com.alirsyad.app.data.repository.local.LocalRepository
import com.alirsyad.app.data.session.Sessions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    fun provideLocalRepository(
        roomDB: RoomDB,
        sessions: Sessions,
        disposable: CompositeDisposable,
        @ApplicationContext context: Context
    ): LocalRepository =
        LocalRepository(roomDB, sessions, disposable, context)

    @Provides
    fun provideRemoteRepository(
        disposable: CompositeDisposable,
        apiService: com.alirsyad.app.data.network.ApiService,
    ): com.alirsyad.app.data.repository.remote.RemoteRepository =
        com.alirsyad.app.data.repository.remote.RemoteRepository(disposable, apiService)

    @Provides
    fun provideDataRepository(
        localRepository: LocalRepository,
        remoteRepository: com.alirsyad.app.data.repository.remote.RemoteRepository
    ): com.alirsyad.app.data.repository.DataRepository =
        com.alirsyad.app.data.repository.DataRepository(localRepository, remoteRepository)
}