/*
 * Copyright (c) 2023, Salesforce, Inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.referral.entities

import com.google.gson.annotations.SerializedName

/**
 * Request body to enroll existing member with either Membership number or contact id.
 * @property membershipNumber - Membership number of existing customer
 * @property contactId - Contact ID of existing customer
 * @property memberStatus - Status, whether active or inactive
 */
data class ReferralExistingEnrollmentRequest(
    @SerializedName("membershipNumber")
    val membershipNumber: String? = null,
    @SerializedName("contactId")
    val contactId: String? = null,
    @SerializedName("memberStatus")
    val memberStatus: String
)