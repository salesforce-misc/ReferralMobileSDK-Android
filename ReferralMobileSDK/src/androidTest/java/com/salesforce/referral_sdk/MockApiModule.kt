package com.salesforce.referral_sdk

import com.salesforce.referral.api.ReferralForceAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MockApiModule {

    @Provides
    @Singleton
    fun provideReferralForceAuthenticatorImpl(): ReferralForceAuthenticator =
        ReferralForceAuthImpl()

    @Provides
    @Singleton
    fun provideBaseUrl(): String =
        "https://abc.com"
}