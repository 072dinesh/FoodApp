package com.example.food_app.di

import com.example.food_app.repository.RetroRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

object AppModel {

//    val baseUrl = "https://api.spoonacular.com/recipes/716429/information?includeNutrition=false"
    val baseUrl = "https://api.spoonacular.com/"
    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
 fun provideHttpClient():OkHttpClient{

       return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
 }
    @Singleton
    @Provides
    fun provideConverterFactory():GsonConverterFactory{
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideStarterTemplateApiService(retrofit:Retrofit): RetroServieInstance {
        return retrofit.create(RetroServieInstance::class.java)
    }

    @Singleton
    @Provides
    fun provideDashboardDataSource(dashboardApi: RetroServieInstance): RetroRepository {
        return RetroRepository(dashboardApi)
    }
}