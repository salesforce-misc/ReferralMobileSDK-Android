package com.salesforce.referral_sdk

import com.salesforce.referral.*
import com.salesforce.referral.api.ReferralAPIConfig
import com.salesforce.referral.entities.*
import com.salesforce.referral.entities.referral_event.Emails
import com.salesforce.referral.entities.referral_event.ReferralEventRequest
import com.salesforce.referral.entities.referral_event.ReferralEventResponse
import com.salesforce.referral.entities.referral_event.ReferralEventType
import com.salesforce.referral.utils.getRandomString
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Test

class ReferralEntitiesTest {

    @Test
    fun testReferralEventRequest() {
        val referralRequest = ReferralEventRequest(
            referralCode = "KB6T7WZJ-TESTRM",
            joiningDate = "2024-01-17T20:29:48",
            eventType = "Refer",
            referralEmails = Emails(emails = listOf("abc@salesforce.com"))
        )
        assertEquals(referralRequest.referralCode, "KB6T7WZJ-TESTRM")
        assertEquals(referralRequest.joiningDate, "2024-01-17T20:29:48")
        assertEquals(referralRequest.eventType, ReferralEventType.REFER.eventType)
        val emails = referralRequest.referralEmails
        assertNotNull(emails)
        assertEquals(emails.emails.size, 1)
        assertEquals(emails.emails[0], "abc@salesforce.com")
    }

    @Test
    fun testReferralEventResponse() {
        val referralEventResponse = ReferralEventResponse(
            contactId = "0031Q00002jbmK6",
            referralId = "0wi1Q0000008W05",
            referralStage = "123",
            transactionJournalIds = mutableListOf(),
            voucherId = "123abc"
        )
        assertEquals(referralEventResponse.contactId, "0031Q00002jbmK6")
        assertEquals(referralEventResponse.referralId, "0wi1Q0000008W05")
        assertEquals(referralEventResponse.referralStage, "123")
        assertEquals(referralEventResponse.voucherId, "123abc")
        assertEquals(referralEventResponse.transactionJournalIds.size, 0)
    }

    @Test
    fun testReferralNewEnrollmentRequest() {
        val membershipNumber = getRandomString(ReferralAPIConfig.MEMBER_SHIP_RANDOM_NUMBER_COUNT)
        val referralNewEnrollmentRequestBody = ReferralNewEnrollmentRequestBody(
            ReferralAttributes(mapOf(ATTRIBUTES_STATE to "California")),
            AssociatedPersonAccountDetails(
                ReferralAttributes(mapOf(ATTRIBUTES_COUNTRY to "US")),
                allowDuplicateRecords = "false",
                email = "test@mail.com",
                firstName = "testFirstName", lastName = "testSecondName"
            ),
            EnrollmentChannel.EMAIL.channel,
            MemberStatus.ACTIVE.status,
            membershipNumber,
            TransactionalJournalStatementFrequency.MONTHLY.frequency,
            TransactionalJournalStatementMethod.EMAIL.method
        )
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
        assertEquals(personAccountDetails.email, "test@mail.com")
        assertEquals(personAccountDetails.firstName, "testFirstName")
        assertEquals(personAccountDetails.lastName, "testSecondName")
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
        val referralExistingEnrollmentRequestBody = ReferralExistingEnrollmentRequest(
            membershipNumber = "WGDEDIK1",
            contactId = "003B000000N5kgwIAB", memberStatus = MemberStatus.ACTIVE.status
        )
        assertEquals(referralExistingEnrollmentRequestBody.membershipNumber, "WGDEDIK1")
        assertEquals(referralExistingEnrollmentRequestBody.contactId, "003B000000N5kgwIAB")
        assertEquals(referralExistingEnrollmentRequestBody.memberStatus, "Active")
    }

    @Test
    fun testReferralEnrollmentResponse() {
        val referralEnrollmentResponse = ReferralEnrollmentResponse(
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
        assertEquals(referralEnrollmentResponse.contactId, "003B000000N5kgwIAB")
        assertEquals(referralEnrollmentResponse.membershipNumber, "WGDEDIK1")
        assertEquals(referralEnrollmentResponse.memberId, "0lM1Q000000L4za")
        assertEquals(referralEnrollmentResponse.programName, "Referral Program")
        assertEquals(referralEnrollmentResponse.promotionReferralCode, "WGDEDIK1-TESTRM")
        val transactionJournals = referralEnrollmentResponse.transactionJournals
        assertNotNull(transactionJournals)
        assertEquals(transactionJournals.size, 1)
        val transactionJournal = transactionJournals[0]
        assertEquals(transactionJournal.status, EnrollmentStatus.PROCESSED.status)
        assertEquals(transactionJournal.activityDate, "2024-01-17T22:57:22.000Z")
        assertEquals(transactionJournal.journalSubType, "Advocate Enrollment")
        assertEquals(transactionJournal.journalType, "Referral")
        assertEquals(transactionJournal.membershipNumber, "WGDEDIK1")
        assertEquals(transactionJournal.programName, "Referral Program")
        assertEquals(transactionJournal.transactionJournalId, "0lV1Q000000Ch32")
    }
}