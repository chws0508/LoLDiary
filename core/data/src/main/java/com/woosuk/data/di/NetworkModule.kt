package com.woosuk.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import com.woosuk.data.BuildConfig
import com.woosuk.data.network.AsiaService
import com.woosuk.data.network.KrService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AsiaRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class KrRetrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            encodeDefaults = true
            isLenient = true
        }

        val jsonMediaType = "application/json".toMediaType()
        return json.asConverterFactory(jsonMediaType)
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideTokenInterceptor(): Interceptor = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
        var apiKey = BuildConfig.X_RIOT_TOKEN

        requestBuilder.addHeader("X-Riot-Token", apiKey)
        chain.proceed(requestBuilder.build())
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        tokenInterceptor: Interceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(tokenInterceptor)
            .build()
    }

    @Singleton
    @AsiaRetrofit
    @Provides
    fun provideAsiaRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://asia.api.riotgames.com/")
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .build()
    }

    @Singleton
    @KrRetrofit
    @Provides
    fun provideKrRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://kr.api.riotgames.com/")
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideAsiaService(
        @AsiaRetrofit retrofit: Retrofit,
    ): AsiaService = retrofit.create(AsiaService::class.java)

    @Singleton
    @Provides
    fun provideKrService(
        @KrRetrofit retrofit: Retrofit,
    ): KrService = retrofit.create(KrService::class.java)
}
