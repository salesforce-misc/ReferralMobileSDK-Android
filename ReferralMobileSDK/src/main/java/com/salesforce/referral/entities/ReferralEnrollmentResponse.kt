/*
 * Copyright (c) 2023, Salesforce, Inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.referral.entities

import com.google.gson.annotations.SerializedName

/**
 * Referral Enrollment API response body
 */
data class ReferralEnrollmentResponse(
    @SerializedName("contactId")
    val contactId: String,
    @SerializedName("memberId")
    val memberId: String,
    @SerializedName("membershipNumber")
    val membershipNumber: String,
    @SerializedName("programName")
    val programName: String,
    @SerializedName("promotionReferralCode")
    val promotionReferralCode: String,
    @SerializedName("transactionJournals")
    val transactionJournals: List<TransactionJournal>
)

data class TransactionJournal(
    @SerializedName("activityDate")
    val activityDate: String,
    @SerializedName("journalSubType")
    val journalSubType: String,
    @SerializedName("journalType")
    val journalType: String,
    @SerializedName("membershipNumber")
    val membershipNumber: String,
    @SerializedName("programName")
    val programName: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("transactionJournalId")
    val transactionJournalId: String
)