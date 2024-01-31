package com.salesforce.referral_sdk

import com.google.gson.Gson
import com.salesforce.referral.EnrollmentStatus
import com.salesforce.referral.api.ApiResponse
import com.salesforce.referral.api.ApiService
import com.salesforce.referral.api.ReferralAPIConfig
import com.salesforce.referral.api.safeApiCall
import com.salesforce.referral.entities.ReferralEnrollmentResponse
import com.salesforce.referral.entities.TransactionJournal
import com.salesforce.referral.entities.referral_event.Emails
import com.salesforce.referral.entities.referral_event.ReferralEventRequest
import com.salesforce.referral.entities.referral_event.ReferralEventResponse
import com.salesforce.referral.repository.ReferralsRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import org.junit.Before
import org.junit.Test


class ReferralsRepositoryTest {

    private lateinit var apiService: ApiService

    private lateinit var repository: ReferralsRepository

    @Before
    fun setUp(){
        apiService = mockk<ApiService>(relaxed = true)

        repository = ReferralsRepository(apiService)
    }

    @Test
    fun `send referrals success response`(){
        runBlocking {
            val mockReferralEventResponse = ReferralEventResponse(
                contactId = "0031Q00002jbmK6",
                referralId = "0wi1Q0000008W05",
                referralStage = "123",
                transactionJournalIds = mutableListOf(),
                voucherId = "123abc"
            )
            coEvery {
                apiService.sendReferrals(any(), any())
            } returns Response.success(mockReferralEventResponse)



            val response = repository.sendReferrals("KB6T7WZJ-TESTRM", listOf("abc@salesforce.com"))
            assertNotNull(response)
            when(response){
                is ApiResponse.Success -> {
                    val data  = response.data
                    assertNotNull(data)
                    assertEquals(data.contactId, "0031Q00002jbmK6")
                    assertEquals(data.referralId, "0wi1Q0000008W05")
                    assertEquals(data.referralStage,"123")
                    assertEquals(data.voucherId, "123abc")
                }
                else -> {

                }
            }
        }
    }

    @Test
    fun `send referrals failure response`(){
        runBlocking {

            coEvery {
                safeApiCall { apiService.sendReferrals(any(), any()) }
            } returns ApiResponse.Error("HTTP 401 Unauthorized")



            val response = repository.sendReferrals("KB6T7WZJ-TESTRM", listOf("abc@salesforce.com"))
            assertNotNull(response)
            when(response){
                is ApiResponse.Error -> {
                    val message  = response.errorMessage
                    assertNotNull(message)
                }
                else -> {

                }
            }
        }
    }

    @Test
    fun `set instance url test`(){
        val instanceUrl = "mock_instance_url"
        repository.setInstanceUrl(instanceUrl)
        TestCase.assertEquals(ReferralAPIConfig.instanceUrl, instanceUrl)
    }

    @Test
    fun `enroll new customer as advocate of promotion success`() {
        runBlocking {
            val mockEnrollmentResponse = ReferralEnrollmentResponse(
                contactId = "0031Q00002jbmBU",
                memberId = "0lM1Q000000L4za",
                membershipNumber = "XNEOT84D",
                programName = "Referral Program",
                promotionReferralCode = "KB6T7WZJ-TESTRM",
                transactionJournals = listOf(
                    TransactionJournal(
                        activityDate = "2024-01-17T22:57:22.000Z",
                        journalSubType = "Advocate Enrollment",
                        journalType = "Referral",
                        membershipNumber = "XNEOT84D",
                        programName = "Referral Program",
                        status = EnrollmentStatus.PROCESSED.status,
                        transactionJournalId = "0lV1Q000000Ch32"
                    )
                )
            )
            coEvery {
                apiService.enrollNewCustomerAsAdvocateOfPromotion(any(), any())
            } returns Response.success(mockEnrollmentResponse)

            val response = repository.enrollNewCustomerAsAdvocateOfPromotion(
                promotionName = "Referral Program", promotionCode = "TESTRM",
                firstName = "testFirstName", lastName = "testSecondName", email = "test@mail.com"
            )
            assertNotNull(response)
            when (response) {
                is ApiResponse.Success -> {
                    val data = response.data
                    assertNotNull(data.transactionJournals)
                    assertEquals(data.transactionJournals.size, 1)
                    assertEquals(
                        data.transactionJournals[0].status,
                        EnrollmentStatus.PROCESSED.status
                    )
                }
                else -> {

                }
            }
        }
    }

    @Test
    fun `enroll existing advocate to promotion with membership number success`() {
        runBlocking {
            val mockEnrollmentResponse = ReferralEnrollmentResponse(
                contactId = "0031Q00002jbmBU",
                memberId = "0lM1Q000000L4za",
                membershipNumber = "WGDEDIK1",
                programName = "Referral Program",
                promotionReferralCode = "WGDEDIK1-TESTRM",
                transactionJournals = listOf(
                    TransactionJournal(
                        activityDate = "2024-01-17T22:57:22.000Z",
                        journalSubType = "Advocate Enrollment",
                        journalType = "Referral",
                        membershipNumber = "WGDEDIK1",
                        programName = "Referral Program",
                        status = EnrollmentStatus.PROCESSED.status,
                        transactionJournalId = "0lV1Q000000Ch32"
                    )
                )
            )
            coEvery {
                apiService.enrollExistingAdvocateToPromotion(any(), any())
            } returns Response.success(mockEnrollmentResponse)

            val response = repository.enrollExistingAdvocateToPromotionWithMembershipNumber(
                promotionName = "Referral Program", promotionCode = "TESTRM",
                membershipNumber = "WGDEDIK1"
            )
            assertNotNull(response)
            when (response) {
                is ApiResponse.Success -> {
                    val data = response.data
                    assertNotNull(data.transactionJournals)
                    assertEquals(data.transactionJournals.size, 1)
                    assertEquals(
                        data.transactionJournals[0].status,
                        EnrollmentStatus.PROCESSED.status
                    )
                    assertEquals(data.membershipNumber, "WGDEDIK1")
                }
                else -> {

                }
            }
        }
    }

    @Test
    fun `enroll existing advocate to promotion with contact id success`() {
        runBlocking {
            val mockEnrollmentResponse = ReferralEnrollmentResponse(
                contactId = "003B000000N5kgwIAB",
                memberId = "0lM1Q000000L4za",
                membershipNumber = "WGDEDIK1",
                programName = "Referral Program",
                promotionReferralCode = "WGDEDIK1-TESTRM",
                transactionJournals = listOf(
                    TransactionJournal(
                        activityDate = "2024-01-17T22:57:22.000Z",
                        journalSubType = "Advocate Enrollment",
                        journalType = "Referral",
                        membershipNumber = "WGDEDIK1",
                        programName = "Referral Program",
                        status = EnrollmentStatus.PROCESSED.status,
                        transactionJournalId = "0lV1Q000000Ch32"
                    )
                )
            )
            coEvery {
                apiService.enrollExistingAdvocateToPromotion(any(), any())
            } returns Response.success(mockEnrollmentResponse)

            val response = repository.enrollExistingAdvocateToPromotionWithContactId(
                promotionName = "Referral Program", promotionCode = "TESTRM",
                contactId = "003B000000N5kgwIAB"
            )
            assertNotNull(response)
            when (response) {
                is ApiResponse.Success -> {
                    val data = response.data
                    assertNotNull(data.transactionJournals)
                    assertEquals(data.transactionJournals.size, 1)
                    assertEquals(
                        data.transactionJournals[0].status,
                        EnrollmentStatus.PROCESSED.status
                    )
                    assertEquals(data.contactId, "003B000000N5kgwIAB")
                }
                else -> {

                }
            }
        }
    }
}