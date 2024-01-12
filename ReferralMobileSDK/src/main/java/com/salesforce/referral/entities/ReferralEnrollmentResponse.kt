/*
 * Copyright (c) 2023, Salesforce, Inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.referral.entities

/**
 * Referral Enrollment API response body
 */
data class ReferralEnrollmentResponse(
    val contactId: String,
    val memberId: String,
    val membershipNumber: String,
    val programName: String,
    val promotionReferralCode: String,
    val transactionJournals: List<TransactionJournal>
)

data class TransactionJournal(
    val activityDate: String,
    val journalSubType: String,
    val journalType: String,
    val membershipNumber: String,
    val programName: String,
    val status: String,
    val transactionJournalId: String
)