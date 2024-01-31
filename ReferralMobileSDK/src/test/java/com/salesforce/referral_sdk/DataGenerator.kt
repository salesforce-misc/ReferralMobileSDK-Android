package com.salesforce.referral_sdk

import com.salesforce.referral.*
import com.salesforce.referral.entities.*
import com.salesforce.referral.entities.referral_event.Emails
import com.salesforce.referral.entities.referral_event.ReferralEventRequest
import com.salesforce.referral.entities.referral_event.ReferralEventResponse

object DataGenerator {

    const val REFERRAL_CODE = "KB6T7WZJ-TESTRM"
    const val JOINING_DATE = "2024-01-17T20:29:48"
    const val REFERRAL_EVENT_TYPE = "Refer"
    const val DUMMY_EMAIL = "abc@salesforce.com"
    const val DUMMY_FIRST_NAME = "testFirstName"
    const val DUMMY_LAST_NAME = "testSecondName"
    const val CONTACT_ID = "0031Q00002jbmK6"
    const val REFERRAL_ID = "0wi1Q0000008W05"
    const val MEMBERSHIP_NUMBER = "WGDEDIK1"
    const val MEMBER_ID = "0lM1Q000000L4za"
    const val PROGRAM_NAME = "Referral Program"
    const val PROMOTION_REFERRAL_CODE = "WGDEDIK1-TESTRM"
    const val VOUCHER_ID = "123abc"
    const val TRANSACTION_JOURNAL_ID = "0lV1Q000000Ch32"

    fun getReferralEventRequest(): ReferralEventRequest {
        return ReferralEventRequest(
            referralCode = REFERRAL_CODE,
            joiningDate = JOINING_DATE,
            eventType = REFERRAL_EVENT_TYPE,
            referralEmails = Emails(emails = listOf(DUMMY_EMAIL))
        )
    }

    fun getReferralEventResponse(): ReferralEventResponse {
        return ReferralEventResponse(
            contactId = CONTACT_ID,
            referralId = REFERRAL_ID,
            referralStage = "123",
            transactionJournalIds = mutableListOf(),
            voucherId = VOUCHER_ID
        )
    }

    fun getReferralNewEnrollmentRequestBody(membershipNumber: String): ReferralNewEnrollmentRequestBody {
        return ReferralNewEnrollmentRequestBody(
            ReferralAttributes(mapOf(ATTRIBUTES_STATE to "California")),
            AssociatedPersonAccountDetails(
                ReferralAttributes(mapOf(ATTRIBUTES_COUNTRY to "US")),
                allowDuplicateRecords = "false",
                email = DUMMY_EMAIL,
                firstName = DUMMY_FIRST_NAME, lastName = DUMMY_LAST_NAME
            ),
            EnrollmentChannel.EMAIL.channel,
            MemberStatus.ACTIVE.status,
            membershipNumber,
            TransactionalJournalStatementFrequency.MONTHLY.frequency,
            TransactionalJournalStatementMethod.EMAIL.method
        )
    }

    fun getReferralExistingEnrollmentRequest(): ReferralExistingEnrollmentRequest {
        return ReferralExistingEnrollmentRequest(
            membershipNumber = MEMBERSHIP_NUMBER,
            contactId = CONTACT_ID, memberStatus = MemberStatus.ACTIVE.status
        )
    }

    fun getReferralEnrollmentResponse(): ReferralEnrollmentResponse {
        return ReferralEnrollmentResponse(
            contactId = CONTACT_ID,
            memberId = MEMBER_ID,
            membershipNumber = MEMBERSHIP_NUMBER,
            programName = PROGRAM_NAME,
            promotionReferralCode = PROMOTION_REFERRAL_CODE,
            transactionJournals = listOf(
                TransactionJournal(
                    activityDate = "2024-01-17T22:57:22.000Z",
                    journalSubType = "Advocate Enrollment",
                    journalType = "Referral",
                    membershipNumber = MEMBERSHIP_NUMBER,
                    programName = PROGRAM_NAME,
                    status = EnrollmentStatus.PROCESSED.status,
                    transactionJournalId = TRANSACTION_JOURNAL_ID
                )
            )
        )
    }
}