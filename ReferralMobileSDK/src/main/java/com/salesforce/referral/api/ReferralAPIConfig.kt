/*
 * Copyright (c) 2023, Salesforce, Inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.referral.api

object ReferralAPIConfig {
    const val DATE_FORMAT_YYYYMMDDTHHMMSS = "yyyy-MM-dd'T'HH:mm:ss"
    const val MEMBER_SHIP_RANDOM_NUMBER_COUNT = 8

    private const val MEMBER_API_SERVICES_PATH = "/services/data/"
    private const val API_VERSION_59 = "v59.0"
    lateinit var instanceUrl: String

    const val HEADER_SFORCE_OPTION_KEY = "Sforce-Call-Options"
    const val HEADER_SFORCE_OPTION_Value = "client=referralMobileSdk-Android"

    /**
     * Sealed class that is used to define the Resources and its corresponding parameters.
     */
    sealed class Resource {
        class ReferralMemberEnrolment(val programName: String, val promotionName: String): Resource()
        object ReferralEvent : Resource()
    }

    /**
     * Get the request URL with appropriate end path for various resources using their corresponding parameters.
     *
     * @param resource Resource for which url is generated.
     * @return String The request URL to be used for API call.
     */
    fun getRequestUrl(resource: Resource): String {
        return when (resource) {
            is Resource.ReferralMemberEnrolment -> {
                instanceUrl + MEMBER_API_SERVICES_PATH + API_VERSION_59 + "/referral-programs/" + resource.programName + "/promotions/"+resource.promotionName+"/member-enrollments"
            }

            is Resource.ReferralEvent -> "$instanceUrl$MEMBER_API_SERVICES_PATH$API_VERSION_59/referral-program/referral-event"
        }
    }
}