package com.salesforce.referral_sdk

import com.google.gson.Gson
import com.salesforce.referral.api.ApiService
import com.salesforce.referral.entities.referral_event.Emails
import com.salesforce.referral.entities.referral_event.ReferralEventRequest
import com.salesforce.referral.repository.ReferralsRepository
import com.salesforce.referral_sdk.DataGenerator.DUMMY_EMAIL
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import java.net.HttpURLConnection

class ApiServiceTest {

    private lateinit var repository: ReferralsRepository

    private lateinit var mockWebServer: MockWebServer

    private lateinit var apiService: ApiService

    private lateinit var referralNetworkMockClient: ReferralNetworkMockClient

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mockWebServer = MockWebServer()
        mockWebServer.start()
        referralNetworkMockClient = ReferralNetworkMockClient(
            ReferralMockAuthenticator,
            "https://instanceUrl",
            mockWebServer
        )
        apiService = referralNetworkMockClient.getApiService()
        repository =
            ReferralsRepository(apiService)
    }

    @Test
    fun testSendReferralsAPISuccess() {
        runBlocking {
            val mockReferralEventResponse = DataGenerator.getReferralEventResponse()
            val response = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(Gson().toJson(mockReferralEventResponse))
            mockWebServer.enqueue(response)
            val actualResponse = apiService.sendReferrals(
                mockWebServer.url("/").toString(),
                ReferralEventRequest(
                    referralCode = DataGenerator.REFERRAL_CODE,
                    joiningDate = DataGenerator.JOINING_DATE,
                    eventType = DataGenerator.REFERRAL_EVENT_TYPE,
                    referralEmails = Emails(emails = listOf(DUMMY_EMAIL))
                )
            )
            mockWebServer.takeRequest()
            TestCase.assertEquals(actualResponse.isSuccessful, true)
        }
    }

    @Test
    fun testSendReferralsAPIUnauthorized() {
        runBlocking {
            val response1 = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED)
            mockWebServer.enqueue(response1)
            val mockReferralEventResponse = DataGenerator.getReferralEventResponse()
            val response2 = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(Gson().toJson(mockReferralEventResponse))
            mockWebServer.enqueue(response2)

            val actualResponse = apiService.sendReferrals(
                mockWebServer.url("/").toString(),
                ReferralEventRequest(
                    referralCode = DataGenerator.REFERRAL_CODE,
                    joiningDate = DataGenerator.JOINING_DATE,
                    eventType = DataGenerator.REFERRAL_EVENT_TYPE,
                    referralEmails = Emails(emails = listOf(DUMMY_EMAIL))
                )
            )
            mockWebServer.takeRequest()
            TestCase.assertEquals(actualResponse.isSuccessful, true)
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}