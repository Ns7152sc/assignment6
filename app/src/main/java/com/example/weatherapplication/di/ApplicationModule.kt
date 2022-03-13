package com.example.weatherapplication.di

import com.example.weatherapplication.network.Api
import com.example.weatherapplication.network.DataUseCase
import com.example.weatherapplication.network.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    private const val BASE_URL = "http://api.openweathermap.org/data/2.5/"

    @Provides
    @Singleton
    fun providesBaseUrl(): String {
        return BASE_URL
    }

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()

        okHttpClient.callTimeout(40, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(40, TimeUnit.SECONDS)
        okHttpClient.readTimeout(40, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(40, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.build()
        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory {
        return MoshiConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient,
        baseUrl: String,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideRestApiService(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: Api): NetworkRepository {
        return NetworkRepository(api)
    }

    @Provides
    @Singleton
    fun provideDataUseCase(repository: NetworkRepository): DataUseCase {
        return DataUseCase(repository)
    }
}
