package com.alirsyad.app.di

import android.content.Context
import android.content.Intent
import com.alirsyad.app.BuildConfig
import com.alirsyad.app.data.repository.local.LocalRepository
import com.alirsyad.app.ui.login.LoginActivity
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivex.disposables.CompositeDisposable
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = when (BuildConfig.DEBUG) {
                true -> HttpLoggingInterceptor.Level.BODY
                false -> HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    fun providesChucker(@ApplicationContext context: Context): ChuckerInterceptor {
        return ChuckerInterceptor(context)
    }

    @Provides
    fun providesApiKey(@ApplicationContext context: Context, repository: LocalRepository): Interceptor = Interceptor { chain ->
        var request: Request = chain.request()
        val url: HttpUrl = request.url.newBuilder()
            .build()
        request = request.newBuilder().url(url).build()

        val response = chain.proceed(request)
        if (response.code == 401) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
            repository.setIsLogin(false)
        }

        return@Interceptor response
    }

    @Provides
    fun providesHttpClient(
        interceptor: HttpLoggingInterceptor,
        chucker: ChuckerInterceptor,
        apiKey: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            retryOnConnectionFailure(true)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            addInterceptor(interceptor)
            if (BuildConfig.DEBUG) addInterceptor(chucker)
            addInterceptor(apiKey)
        }.build()
    }

    @Provides
    fun providesHttpAdapter(clients: OkHttpClient): Retrofit {
        return Retrofit.Builder().apply {
            client(clients)
            baseUrl(BuildConfig.baseUrl)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        }.build()
    }

    @Provides
    fun providesApiEndPoint(retrofit: Retrofit): com.alirsyad.app.data.network.ApiService {
        return retrofit.create(com.alirsyad.app.data.network.ApiService::class.java)
    }

    @Provides
    fun provideDisposable(): CompositeDisposable = CompositeDisposable()
}