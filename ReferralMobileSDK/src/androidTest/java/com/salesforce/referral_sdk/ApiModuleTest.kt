package com.salesforce.referral_sdk

import com.salesforce.referral.api.ApiService
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ApiModuleTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var apiService: ApiService

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testApiServiceApi() {
        assert(apiService != null)
    }
}