package com.salesforce.referral_sdk

import com.salesforce.referral.EnrollmentStatus
import com.salesforce.referral.api.ApiResponse
import com.salesforce.referral.api.ApiService
import com.salesforce.referral.api.ReferralAPIConfig
import com.salesforce.referral.api.safeApiCall
import com.salesforce.referral.repository.ReferralsRepository
import com.salesforce.referral_sdk.DataGenerator.CONTACT_ID
import com.salesforce.referral_sdk.DataGenerator.DUMMY_EMAIL
import com.salesforce.referral_sdk.DataGenerator.DUMMY_FIRST_NAME
import com.salesforce.referral_sdk.DataGenerator.DUMMY_LAST_NAME
import com.salesforce.referral_sdk.DataGenerator.MEMBERSHIP_NUMBER
import com.salesforce.referral_sdk.DataGenerator.PROGRAM_NAME
import com.salesforce.referral_sdk.DataGenerator.PROMOTION_REFERRAL_CODE
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response


class ReferralsRepositoryTest {

    private lateinit var apiService: ApiService

    private lateinit var repository: ReferralsRepository

    @Before
    fun setUp() {
        apiService = mockk<ApiService>(relaxed = true)

        repository = ReferralsRepository(apiService)
    }

    @Test
    fun `send referrals success response`() {
        runBlocking {
            val mockReferralEventResponse = DataGenerator.getReferralEventResponse()
            coEvery {
                apiService.sendReferrals(any(), any())
            } returns Response.success(mockReferralEventResponse)


            val response = repository.sendReferrals(
                DataGenerator.REFERRAL_CODE,
                listOf(DataGenerator.DUMMY_EMAIL)
            )
            assertNotNull(response)
            when (response) {
                is ApiResponse.Success -> {
                    val data = response.data
                    assertNotNull(data)
                    assertEquals(data.contactId, DataGenerator.CONTACT_ID)
                    assertEquals(data.referralId, DataGenerator.REFERRAL_ID)
                    assertEquals(data.referralStage, "123")
                    assertEquals(data.voucherId, DataGenerator.VOUCHER_ID)
                }
                else -> {

                }
            }
        }
    }

    @Test
    fun `send referrals failure response`() {
        runBlocking {

            coEvery {
                safeApiCall { apiService.sendReferrals(any(), any()) }
            } returns ApiResponse.Error("HTTP 401 Unauthorized")


            val response = repository.sendReferrals("KB6T7WZJ-TESTRM", listOf("abc@salesforce.com"))
            assertNotNull(response)
            when (response) {
                is ApiResponse.Error -> {
                    val message = response.errorMessage
                    assertNotNull(message)
                }
                else -> {

                }
            }
        }
    }

    @Test
    fun `set instance url test`() {
        val instanceUrl = "mock_instance_url"
        repository.setInstanceUrl(instanceUrl)
        TestCase.assertEquals(ReferralAPIConfig.instanceUrl, instanceUrl)
    }

    @Test
    fun `enroll new customer as advocate of promotion success`() {
        runBlocking {
            val mockEnrollmentResponse = DataGenerator.getReferralEnrollmentResponse()
            coEvery {
                apiService.enrollNewCustomerAsAdvocateOfPromotion(any(), any())
            } returns Response.success(mockEnrollmentResponse)

            val response = repository.enrollNewCustomerAsAdvocateOfPromotion(
                promotionName = PROGRAM_NAME, promotionCode = PROMOTION_REFERRAL_CODE,
                firstName = DUMMY_FIRST_NAME, lastName = DUMMY_LAST_NAME, email = DUMMY_EMAIL
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
            val mockEnrollmentResponse = DataGenerator.getReferralEnrollmentResponse()
            coEvery {
                apiService.enrollExistingAdvocateToPromotion(any(), any())
            } returns Response.success(mockEnrollmentResponse)

            val response = repository.enrollExistingAdvocateToPromotionWithMembershipNumber(
                promotionName = PROGRAM_NAME, promotionCode = PROMOTION_REFERRAL_CODE,
                membershipNumber = MEMBERSHIP_NUMBER
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
                    assertEquals(data.membershipNumber, MEMBERSHIP_NUMBER)
                }
                else -> {

                }
            }
        }
    }

    @Test
    fun `enroll existing advocate to promotion with contact id success`() {
        runBlocking {
            val mockEnrollmentResponse = DataGenerator.getReferralEnrollmentResponse()
            coEvery {
                apiService.enrollExistingAdvocateToPromotion(any(), any())
            } returns Response.success(mockEnrollmentResponse)

            val response = repository.enrollExistingAdvocateToPromotionWithContactId(
                promotionName = PROGRAM_NAME, promotionCode = PROMOTION_REFERRAL_CODE,
                contactId = CONTACT_ID
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
                    assertEquals(data.contactId, CONTACT_ID)
                }
                else -> {

                }
            }
        }
    }
}