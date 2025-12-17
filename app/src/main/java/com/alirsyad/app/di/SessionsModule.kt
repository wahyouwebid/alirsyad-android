package com.alirsyad.app.di

import android.content.Context
import com.alirsyad.app.data.session.Sessions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SessionsModule {
    @Provides
    fun provideSessions(@ApplicationContext context: Context): Sessions = Sessions(context)
}