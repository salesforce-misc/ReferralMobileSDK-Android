package com.salesforce.referral.entities.referral_event

import com.google.gson.annotations.SerializedName

data class ReferralEventRequest(
    @SerializedName("referralCode")
    val referralCode: String,
    @SerializedName("joiningDate")
    val joiningDate: String,
    @SerializedName("eventType")
    val eventType: String = ReferralEventType.REFER.eventType,
    @SerializedName("referralEmails")
    val referralEmails: Emails
)

data class Emails(
    @SerializedName("emails")
    val emails: List<String>,
)