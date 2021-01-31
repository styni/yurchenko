package com.example.developerslife.di.modules

import android.app.Application
import com.example.developerslife.di.ForApplication
import com.example.developerslife.network.Api
import com.example.developerslife.network.UnsafeOkHttpClient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    @ForApplication
    fun provideApplicationContext(): Application = application

    @Provides
    @Singleton
    fun provideHttpClient() = UnsafeOkHttpClient.unsafeOkHttpClient

    @Provides
    fun provideGson() = GsonBuilder().create()

    @Provides
    fun provideGsonApi(client: OkHttpClient, gson: Gson): Api {
        return Retrofit.Builder()
            .baseUrl(Api.BACK_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(Api::class.java)
    }
}