/*
 * Copyright (c) 2023, Salesforce, Inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.referral.api

import com.salesforce.referral.entities.ReferralEnrollmentResponse
import com.salesforce.referral.entities.ReferralExistingEnrollmentRequest
import com.salesforce.referral.entities.ReferralNewEnrollmentRequestBody
import com.salesforce.referral.entities.referral_event.ReferralEventRequest
import com.salesforce.referral.entities.referral_event.ReferralEventResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * ApiService is an interface that defines the contract for making network API calls.
 */
interface ApiService {

    /**
     * Makes a POST request to the given url to enroll new customer as an advocate of a Promotion
     * @param url - Enrollment URL
     * @param requestBody - Request Body with enrollment details
     */
    @POST
    suspend fun enrollNewCustomerAsAdvocateOfPromotion(
        @Url url: String,
        @Body requestBody: ReferralNewEnrollmentRequestBody
    ): Response<ReferralEnrollmentResponse>

    /**
     * Makes a POST request to the given url to enroll existing customer/advocate to a Promotion
     * @param url - Enrollment URL
     * @param requestBody - Request Body with enrollment details
     */
    @POST
    suspend fun enrollExistingAdvocateToPromotion(
        @Url url: String,
        @Body requestBody: ReferralExistingEnrollmentRequest
    ): Response<ReferralEnrollmentResponse>

    /**
     * Makes a POST request to the given url to send referral events/emails to given recipients
     * @param url - Referral Events URL
     * @param requestBody - Request Body with referral emails
     */
    @POST
    suspend fun sendReferrals(
        @Url url: String,
        @Body requestBody: ReferralEventRequest
    ): Response<ReferralEventResponse>
}