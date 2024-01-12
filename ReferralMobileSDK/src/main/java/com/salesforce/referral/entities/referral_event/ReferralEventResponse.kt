/*
 * Copyright (c) 2023, Salesforce, Inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.referral.entities.referral_event

import com.google.gson.annotations.SerializedName

/**
 * Referral Events Response body
 */
data class ReferralEventResponse(
    @SerializedName("contactId")
    val contactId: String,
    @SerializedName("referralId")
    val referralId: String,
    @SerializedName("referralStage")
    val referralStage: String,
    @SerializedName("transactionjournalIds")
    val transactionJournalIds: List<String>,
    @SerializedName("voucherId")
    val voucherId: String
)