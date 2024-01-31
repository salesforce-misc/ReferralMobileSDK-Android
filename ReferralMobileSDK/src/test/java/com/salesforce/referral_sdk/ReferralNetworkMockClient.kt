package com.salesforce.referral_sdk

import com.salesforce.referral.api.ApiService
import com.salesforce.referral.api.ReferralForceAuthenticator
import com.salesforce.referral.api.UnauthorizedInterceptor
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ReferralNetworkMockClient(
    auth: ReferralForceAuthenticator,
    instanceUrl: String,
    mockWebServer: MockWebServer
) {
    private val unauthorizedInterceptor: UnauthorizedInterceptor = UnauthorizedInterceptor(auth)
    private fun getOkHttpClientBuilder(): OkHttpClient.Builder {
        var mOkHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        mOkHttpClient.addInterceptor(unauthorizedInterceptor)
        mOkHttpClient.connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
        return mOkHttpClient
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(getOkHttpClientBuilder().build())
            .build()

    }

    fun getApiService(): ApiService {
        val apiService: ApiService by lazy {
            retrofit.create(ApiService::class.java)
        }
        return apiService
    }
}