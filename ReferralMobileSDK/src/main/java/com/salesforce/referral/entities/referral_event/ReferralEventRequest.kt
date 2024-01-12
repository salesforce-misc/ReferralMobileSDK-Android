/*
 * Copyright (c) 2023, Salesforce, Inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.referral.entities.referral_event

import com.google.gson.annotations.SerializedName

/**
 * Request body to send referral events with given referral code to the emails
 */
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