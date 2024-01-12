/*
 * Copyright (c) 2023, Salesforce, Inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.referral.entities

/**
 * Request body to enroll existing member with either Membership number or contact id.
 * @property membershipNumber - Membership number of existing customer
 * @property contactId - Contact ID of existing customer
 */
data class ReferralExistingEnrollmentRequest(
    val membershipNumber: String? = null,
    val contactId: String? = null
)