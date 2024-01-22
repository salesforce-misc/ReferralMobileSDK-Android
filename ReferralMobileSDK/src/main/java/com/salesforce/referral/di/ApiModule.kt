/*
 * Copyright (c) 2023, Salesforce, Inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.referral.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.salesforce.mobile.referrals.BuildConfig
import com.salesforce.referral.api.ApiService
import com.salesforce.referral.api.ReferralForceAuthenticator
import com.salesforce.referral.api.UnauthorizedInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideBaseUrl() = "https://hutl.my.salesforce.com"
    // This BASE URL should be passed from client app and provided here the default base URL for Retrofit Instance

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Singleton
    @Provides
    fun provideOkHttpClient(unauthorizedInterceptor: UnauthorizedInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(unauthorizedInterceptor)
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
                addInterceptor(loggingInterceptor)
            }
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, gson: Gson, client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    fun provideAPiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideUnauthorizedInterceptor(authenticator: ReferralForceAuthenticator)
        = UnauthorizedInterceptor(authenticator)
}