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
    @SerializedName("additionalMemberFieldValues")
    val additionalMemberFieldValues: ReferralAttributes?,
    @SerializedName("associatedPersonAccountDetails")
    val associatedPersonAccountDetails: AssociatedPersonAccountDetails,
    @SerializedName("enrollmentChannel")
    val enrollmentChannel: String,
    @SerializedName("memberStatus")
    val memberStatus: String,
    @SerializedName("membershipNumber")
    val membershipNumber: String,
    @SerializedName("transactionJournalStatementFrequency")
    val transactionJournalStatementFrequency: String,
    @SerializedName("transactionJournalStatementMethod")
    val transactionJournalStatementMethod: String
)

data class AssociatedPersonAccountDetails(
    @SerializedName("additionalPersonAccountFieldValues")
    val additionalPersonAccountFieldValues: ReferralAttributes?,
    @SerializedName("allowDuplicateRecords")
    val allowDuplicateRecords: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String
)

data class ReferralAttributes(
    @SerializedName("attributes")
    val attributes: Map<String, Any?>? = mutableMapOf()
)

