package com.salesforce.referral.repository

import com.salesforce.referral.EnrollmentChannel
import com.salesforce.referral.MemberStatus
import com.salesforce.referral.TransactionalJournalStatementFrequency
import com.salesforce.referral.TransactionalJournalStatementMethod
import com.salesforce.referral.api.ApiResponse
import com.salesforce.referral.api.ApiService
import com.salesforce.referral.api.ReferralAPIConfig
import com.salesforce.referral.api.ReferralAPIConfig.MEMBER_SHIP_RANDOM_NUMBER_COUNT
import com.salesforce.referral.api.ReferralAPIConfig.getRequestUrl
import com.salesforce.referral.api.safeApiCall
import com.salesforce.referral.entities.AssociatedPersonAccountDetails
import com.salesforce.referral.entities.ReferralEnrollmentResponse
import com.salesforce.referral.entities.ReferralExistingEnrollmentRequest
import com.salesforce.referral.entities.ReferralNewEnrollmentRequestBody
import com.salesforce.referral.entities.referral_event.Emails
import com.salesforce.referral.entities.referral_event.ReferralEventRequest
import com.salesforce.referral.entities.referral_event.ReferralEventResponse
import com.salesforce.referral.utils.getCurrentDateTime
import com.salesforce.referral.utils.getRandomString
import javax.inject.Inject

/**
 * @ReferralsRepository class processes requests from the client app and then responds with an ApiResponse based on network requests.
 * @param apiService ApiService instance to make network API calls
 */
open class ReferralsRepository @Inject constructor(
    private val apiService: ApiService
) {

    /**
     * This function is responsible for making a network request to enroll new member as an advocate of a promotion with the given member details.
     * @param promotionName Promotion/Program Name, that member is trying to enroll
     * @param promotionCode Promotion, that member is trying to enroll
     * @param firstName First Name of the Customer
     * @param lastName Last Name of the Customer
     * @param email Email of the Customer
     * @param state State of the Customer
     * @param country Country of the Customer
     * @param enrollmentChannel Enrollment channel type. Default type is Email.
     * @param memberStatus Status, whether member is active or not. Default is Active
     * @param transactionalJournalFrequency Transactional Frequency. Default is Monthly
     * @param transactionalJournalMethod Transactional Method. Default is EMail
     */
    suspend fun enrollNewCustomerAsAdvocateOfPromotion(
        promotionName: String,
        promotionCode: String,
        firstName: String,
        lastName: String,
        email: String,
        state: String? = null,
        country: String? = null,
        enrollmentChannel: EnrollmentChannel = EnrollmentChannel.EMAIL,
        memberStatus: MemberStatus = MemberStatus.ACTIVE,
        transactionalJournalFrequency: TransactionalJournalStatementFrequency = TransactionalJournalStatementFrequency.MONTHLY,
        transactionalJournalMethod: TransactionalJournalStatementMethod = TransactionalJournalStatementMethod.EMAIL
    ): ApiResponse<ReferralEnrollmentResponse> {
        return safeApiCall {
            // State and Country fields are custom fields, which are not supported as of now, hence commented below
            apiService.enrollNewCustomerAsAdvocateOfPromotion(
                getRequestUrl(ReferralAPIConfig.Resource.ReferralMemberEnrolment(promotionName, promotionCode)),
                ReferralNewEnrollmentRequestBody(
//                    state?.let { ReferralAttributes(mapOf(ATTRIBUTES_STATE to state)) },
                    null,
                    AssociatedPersonAccountDetails(
//                        country?.let { ReferralAttributes(mapOf(ATTRIBUTES_COUNTRY to country)) },
                        null,
                        "false",
                        email,
                        firstName,
                        lastName
                    ),
                    enrollmentChannel.channel,
                    memberStatus.status,
                    getRandomString(MEMBER_SHIP_RANDOM_NUMBER_COUNT),
                    transactionalJournalFrequency.frequency,
                    transactionalJournalMethod.method
                )
            )
        }
    }

    /**
     * This function is responsible for making a network request to enroll existing advocate to a given promotion with Membership Number.
     * @param promotionName Promotion/Program Name, that member is trying to enroll
     * @param promotionCode Promotion, that member is trying to enroll
     * @param membershipNumber Membership Number of the existing advocate
     */
    suspend fun enrollExistingAdvocateToPromotionWithMembershipNumber(
        promotionName: String,
        promotionCode: String,
        membershipNumber: String,
        memberStatus: MemberStatus = MemberStatus.ACTIVE
    ): ApiResponse<ReferralEnrollmentResponse> {
        return enrollExistingAdvocateToNewPromotion(
            promotionName,
            promotionCode,
            ReferralExistingEnrollmentRequest(membershipNumber = membershipNumber, memberStatus = memberStatus.status)
        )
    }

    /**
     * This function is responsible for making a network request to enroll existing advocate to a given promotion with Contact ID
     * @param promotionName Promotion/Program Name, that member is trying to enroll
     * @param promotionCode Promotion, that member is trying to enroll
     * @param contactId Contact ID of the existing advocate
     */
    suspend fun enrollExistingAdvocateToPromotionWithContactId(
        promotionName: String,
        promotionCode: String,
        contactId: String,
        memberStatus: MemberStatus = MemberStatus.ACTIVE
    ): ApiResponse<ReferralEnrollmentResponse> {
        return enrollExistingAdvocateToNewPromotion(
            promotionName,
            promotionCode,
            ReferralExistingEnrollmentRequest(contactId = contactId, memberStatus = memberStatus.status)
        )
    }

    private suspend fun enrollExistingAdvocateToNewPromotion(
        promotionName: String,
        promotionCode: String,
        existingMemberRequest: ReferralExistingEnrollmentRequest
    ) = safeApiCall {
        apiService.enrollExistingAdvocateToPromotion(
            getRequestUrl(ReferralAPIConfig.Resource.ReferralMemberEnrolment(promotionName, promotionCode)),
            existingMemberRequest
        )
    }

    /**
     * This function is responsible for making a POST network request to send referral events/emails to given recipients
     * @param referralCode - Referral Code to share with given recipients
     * @param emails - Emails list to share the referral code
     */
    suspend fun sendReferrals(
        referralCode: String,
        emails: List<String>
    ): ApiResponse<ReferralEventResponse> {
        val requestBody = ReferralEventRequest(
            referralCode = referralCode,
            joiningDate = getCurrentDateTime().orEmpty(),
            referralEmails = Emails(emails)
        )
        return safeApiCall {
            apiService.sendReferrals(
                getRequestUrl(ReferralAPIConfig.Resource.ReferralEvent),
                requestBody
            )
        }
    }

    /**
     * Set Instance or Base URL based on environment selection at client side
     */
    fun setInstanceUrl(instanceUrl: String) {
        ReferralAPIConfig.instanceUrl = instanceUrl
    }
}