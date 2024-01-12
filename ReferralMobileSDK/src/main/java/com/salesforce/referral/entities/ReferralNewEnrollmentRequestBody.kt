/*
 * Copyright (c) 2023, Salesforce, Inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.referral.entities

import com.google.gson.annotations.SerializedName

const val ATTRIBUTES_STATE = "State__c"
const val ATTRIBUTES_COUNTRY = "Country__c"

/**
 * Request body to enroll new member with member info.
 * In this case, membershipNumber is generated randomly and send it to the enrollment API
 */
data class ReferralNewEnrollmentRequestBody(
    val additionalMemberFieldValues: ReferralAttributes?,
    val associatedPersonAccountDetails: AssociatedPersonAccountDetails,
    val enrollmentChannel: String,
    val memberStatus: String,
    val membershipNumber: String,
    val transactionJournalStatementFrequency: String,
    val transactionJournalStatementMethod: String
)

data class AssociatedPersonAccountDetails(
    val additionalPersonAccountFieldValues: ReferralAttributes?,
    val allowDuplicateRecords: String,
    val email: String,
    val firstName: String,
    val lastName: String
)

data class ReferralAttributes(
    @SerializedName("attributes")
    val attributes: Map<String, Any?>? = mutableMapOf()
)

