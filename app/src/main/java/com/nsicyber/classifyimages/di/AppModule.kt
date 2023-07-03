package com.nsicyber.classifyimages.di

import android.app.Application
import com.nsicyber.classifyimages.interfaces.ContextProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideContextProvider(application: Application): ContextProvider {
        return AppContextProvider(application)
    }

//
//    @Singleton
//    @Provides
//    fun provideYoutubeRepository(api: ApiInterface) = YoutubeRepository(api)
//
//    @Singleton
//    @Provides
//    fun provideYoutubeApi(): ApiInterface {
//        return Retrofit.Builder().addConverterFactory((GsonConverterFactory.create()))
//            .baseUrl(BASE_URL).build().create(ApiInterface::class.java)
//    }

}