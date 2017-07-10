package com.michalkarmelita.hiketracker.common.dagger.modules

import com.michalkarmelita.busarrivals.network.interceptors.RequestInterceptor
import com.michalkarmelita.hiketracker.BuildConfig
import com.michalkarmelita.hiketracker.picturefeed.model.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by MK on 10/07/2017.
 */
@Module
class NetworkModule {

    private val url = BuildConfig.API_URL

    @Provides
    fun provideLoginInterceptor() = RequestInterceptor()

    @Provides
    fun provideOkHttpClient(loginInterceptor: RequestInterceptor) = OkHttpClient.Builder()
            .addInterceptor(loginInterceptor)
            .build()

    @Provides
    fun providesApiService(client: OkHttpClient) = Retrofit.Builder()
            .baseUrl(url)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .client(client)
            .build()
            .create(ApiService::class.java)

}