/*
 * Copyright (c) 2023, Salesforce, Inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.referral.utils

import com.salesforce.referral.Logger
import com.salesforce.referral.api.ReferralAPIConfig.DATE_FORMAT_YYYYMMDDTHHMMSS
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

const val TAG = "ReferralsUtil"

/**
 * Returns a randomly generated string
 *
 * @param length Length of random string needed
 * @return Random String
 */
fun getRandomString(length: Int): String {
    val allowedChars = ('A'..'Z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

/**
 * Get current date in "2022-01-01T00:00:00" format
 *
 * @param format Format in which the Date Time value should be returned
 * @return Current date and time in specified format
 */
fun getCurrentDateTime(format: String = DATE_FORMAT_YYYYMMDDTHHMMSS): String? {
    return try {
        SimpleDateFormat(format, Locale.getDefault()).format(Calendar.getInstance().timeInMillis)
    } catch (e: java.lang.Exception) {
        Logger.e(TAG, "Exception occurred when retrieving current date time in $format", e)
        null
    }
}