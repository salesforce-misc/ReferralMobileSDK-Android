package com.salesforce.referral.entities


data class ReferralExistingEnrollmentRequest(
    val membershipNumber: String? = null,
    val contactId: String? = null
)