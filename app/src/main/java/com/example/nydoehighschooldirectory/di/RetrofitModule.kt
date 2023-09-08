package com.example.nydoehighschooldirectory.di

import com.example.nydoehighschooldirectory.service.SchoolService
import com.example.nydoehighschooldirectory.util.Utils.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    /**
     * Provides a configured Retrofit instance for making network requests.
     *
     * @return A Retrofit instance.
     */
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Provides an implementation of the [SchoolService] interface using Retrofit.
     *
     * @param retrofit The Retrofit instance for creating the service.
     * @return An instance of the [SchoolService] interface.
     */
    @Provides
    fun provideSchoolService(retrofit: Retrofit): SchoolService {
        return retrofit.create(SchoolService::class.java)
    }
}