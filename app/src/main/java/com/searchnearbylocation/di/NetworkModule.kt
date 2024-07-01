package com.searchnearbylocation.di

import com.searchnearbylocation.data.api.NearbyPlaceRestAPI
import com.searchnearbylocation.data.api.NetworkInterceptor
import com.searchnearbylocation.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideRestAPI(retrofit: Retrofit): NearbyPlaceRestAPI {
        return retrofit.create(NearbyPlaceRestAPI::class.java)
    }

    @Provides
    fun provideOkHttpClient(networkInterceptor: NetworkInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(networkInterceptor).build()
    }
}