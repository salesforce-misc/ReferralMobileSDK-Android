package com.salesforce.referral_sdk

import com.salesforce.referral.EnrollmentStatus
import com.salesforce.referral.api.ReferralAPIConfig
import com.salesforce.referral.entities.ATTRIBUTES_COUNTRY
import com.salesforce.referral.entities.ATTRIBUTES_STATE
import com.salesforce.referral.entities.referral_event.ReferralEventType
import com.salesforce.referral.utils.getRandomString
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Test

class ReferralEntitiesTest {

    @Test
    fun testReferralEventRequest() {
        val referralRequest = DataGenerator.getReferralEventRequest()
        assertEquals(referralRequest.referralCode, DataGenerator.REFERRAL_CODE)
        assertEquals(referralRequest.joiningDate, DataGenerator.JOINING_DATE)
        assertEquals(referralRequest.eventType, ReferralEventType.REFER.eventType)
        val emails = referralRequest.referralEmails
        assertNotNull(emails)
        assertEquals(emails.emails.size, 1)
        assertEquals(emails.emails[0], DataGenerator.DUMMY_EMAIL)
    }

    @Test
    fun testReferralEventResponse() {
        val referralEventResponse = DataGenerator.getReferralEventResponse()
        assertEquals(referralEventResponse.contactId, DataGenerator.CONTACT_ID)
        assertEquals(referralEventResponse.referralId, DataGenerator.REFERRAL_ID)
        assertEquals(referralEventResponse.referralStage, "123")
        assertEquals(referralEventResponse.voucherId, DataGenerator.VOUCHER_ID)
        assertEquals(referralEventResponse.transactionJournalIds.size, 0)
    }

    @Test
    fun testReferralNewEnrollmentRequest() {
        val membershipNumber = getRandomString(ReferralAPIConfig.MEMBER_SHIP_RANDOM_NUMBER_COUNT)
        val referralNewEnrollmentRequestBody = DataGenerator.getReferralNewEnrollmentRequestBody(membershipNumber)
        assertNotNull(referralNewEnrollmentRequestBody.additionalMemberFieldValues)
        assertEquals(
            referralNewEnrollmentRequestBody.additionalMemberFieldValues?.attributes?.size,
            1
        )
        assertEquals(
            referralNewEnrollmentRequestBody.additionalMemberFieldValues?.attributes?.get(
                ATTRIBUTES_STATE
            ), "California"
        )
        val personAccountDetails = referralNewEnrollmentRequestBody.associatedPersonAccountDetails
        assertNotNull(personAccountDetails)
        assertEquals(personAccountDetails.email, DataGenerator.DUMMY_EMAIL)
        assertEquals(personAccountDetails.firstName, DataGenerator.DUMMY_FIRST_NAME)
        assertEquals(personAccountDetails.lastName, DataGenerator.DUMMY_LAST_NAME)
        assertEquals(personAccountDetails.allowDuplicateRecords, "false")
        assertEquals(personAccountDetails.additionalPersonAccountFieldValues?.attributes?.size, 1)
        assertEquals(
            personAccountDetails.additionalPersonAccountFieldValues?.attributes?.get(
                ATTRIBUTES_COUNTRY
            ), "US"
        )
        assertEquals(referralNewEnrollmentRequestBody.enrollmentChannel, "Email")
        assertEquals(referralNewEnrollmentRequestBody.memberStatus, "Active")
        assertEquals(referralNewEnrollmentRequestBody.membershipNumber, membershipNumber)
        assertEquals(
            referralNewEnrollmentRequestBody.transactionJournalStatementFrequency,
            "Monthly"
        )
        assertEquals(referralNewEnrollmentRequestBody.transactionJournalStatementMethod, "Email")
    }

    @Test
    fun testReferralExistingEnrollmentRequest() {
        val referralExistingEnrollmentRequestBody = DataGenerator.getReferralExistingEnrollmentRequest()
        assertEquals(referralExistingEnrollmentRequestBody.membershipNumber, DataGenerator.MEMBERSHIP_NUMBER)
        assertEquals(referralExistingEnrollmentRequestBody.contactId, DataGenerator.CONTACT_ID)
        assertEquals(referralExistingEnrollmentRequestBody.memberStatus, "Active")
    }

    @Test
    fun testReferralEnrollmentResponse() {
        val referralEnrollmentResponse = DataGenerator.getReferralEnrollmentResponse()
        assertEquals(referralEnrollmentResponse.contactId, DataGenerator.CONTACT_ID)
        assertEquals(referralEnrollmentResponse.membershipNumber, DataGenerator.MEMBERSHIP_NUMBER)
        assertEquals(referralEnrollmentResponse.memberId, DataGenerator.MEMBER_ID)
        assertEquals(referralEnrollmentResponse.programName, DataGenerator.PROGRAM_NAME)
        assertEquals(referralEnrollmentResponse.promotionReferralCode, DataGenerator.PROMOTION_REFERRAL_CODE)
        val transactionJournals = referralEnrollmentResponse.transactionJournals
        assertNotNull(transactionJournals)
        assertEquals(transactionJournals.size, 1)
        val transactionJournal = transactionJournals[0]
        assertEquals(transactionJournal.status, EnrollmentStatus.PROCESSED.status)
        assertEquals(transactionJournal.activityDate, "2024-01-17T22:57:22.000Z")
        assertEquals(transactionJournal.journalSubType, "Advocate Enrollment")
        assertEquals(transactionJournal.journalType, "Referral")
        assertEquals(transactionJournal.membershipNumber, DataGenerator.MEMBERSHIP_NUMBER)
        assertEquals(transactionJournal.programName, DataGenerator.PROGRAM_NAME)
        assertEquals(transactionJournal.transactionJournalId, DataGenerator.TRANSACTION_JOURNAL_ID)
    }
}