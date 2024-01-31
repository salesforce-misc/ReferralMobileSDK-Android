package com.salesforce.referral_sdk

import com.salesforce.referral.api.ReferralForceAuthenticator

class ReferralForceAuthImpl: ReferralForceAuthenticator {
    override fun getAccessToken(): String? {
        return "AccessToken1234"
    }

    override suspend fun grantAccessToken(): String? {
        return "AccessToken1234"
    }
}