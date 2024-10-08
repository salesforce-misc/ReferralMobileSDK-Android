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
import com.salesforce.referral_sdk.DataGenerator.ERROR_MESSAGE
import com.salesforce.referral_sdk.DataGenerator.MEMBERSHIP_NUMBER
import com.salesforce.referral_sdk.DataGenerator.PROGRAM_NAME
import com.salesforce.referral_sdk.DataGenerator.PROMOTION_REFERRAL_CODE
import com.salesforce.referral_sdk.DataGenerator.REFERRAL_CODE
import com.salesforce.referral_sdk.DataGenerator.REFERRAL_ERROR
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
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

        repository.setInstanceUrl("mock_instance_url")
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
                    // Receiving any other response than Success should fail the test case.
                    assert(false)
                }
            }
        }
    }

    @Test
    fun `send referrals failure response`() {
        runBlocking {
            val responseBody = REFERRAL_ERROR.toResponseBody("application/json".toMediaTypeOrNull())
            coEvery {
                apiService.sendReferrals(any(), any())
            } returns Response.error(401, responseBody)


            val response = repository.sendReferrals(REFERRAL_CODE, listOf(DUMMY_EMAIL))
            assertNotNull(response)
            when (response) {
                is ApiResponse.Error -> {
                    val message = response.errorMessage
                    assertNotNull(message)
                    assertEquals(message, ERROR_MESSAGE)
                }
                else -> {
                    // Receiving any other response than Error should fail the test case.
                    assert(false)
                }
            }
        }
    }

    @Test
    fun `send referrals network error response`() {
        runBlocking {

            coEvery {
                safeApiCall { apiService.sendReferrals(any(), any()) }
            } returns ApiResponse.NetworkError


            val response = repository.sendReferrals(REFERRAL_CODE, listOf(DUMMY_EMAIL))
            assertNotNull(response)
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