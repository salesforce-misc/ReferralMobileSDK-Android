/*
 * Copyright (c) 2023, Salesforce, Inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.referral

/**
 * Enum class that holds the status of the loyalty program member.
 * @param status Member Status i.e Active/InActive
 */
enum class MemberStatus(val status: String) {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    CUSTOM("Custom")
}