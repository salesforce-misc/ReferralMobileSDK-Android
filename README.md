# ReferralMobileSDK-Android

ReferralMobileSDK-Android is designed to facilitate the integration of referral program functionalities in Android applications. It simplifies the process of enrolling users in referral programs, managing referral events, and handling various referral-related operations, leveraging the capabilities of the Referral API.

**Where:** This SDK is applicable in all Android environments supporting Kotlin and can be integrated into any Android application.
**How:** Integrate the ReferralMobileSDK-Android into your project, configure the necessary parameters, and start using the available APIs to manage referral activities.

### Supported Versions of Tools and Components

| Tool or Component       | Supported Version    | Installation Details                    |
|-------------------------|----------------------|-----------------------------------------|
| Android Studio          | Electric Eel         | Install from the Android Developer site |
| Android SDK and APIs    | 8.0+ (API level 26+) | Install from the Android Developer site |
| Android SDK Tools       | 8.0+                 | Install from the Android Developer site |
| Android Virtual Device  | 8.0+                 | Install from Android Studio             |
| Kotlin                  | 1.8.0+               | Installed by Android Studio             |

### Installation

To integrate ReferralMobileSDK-Android with your Android project, add it as a package dependency.

In the Terminal, execute the following commands within your project:
`git submodule add git@github.com:loyaltysampleapp/ReferralMobileSDK-Android.git`
`git submodule sync`
`git submodule update`
`cd ReferralMobileSDK-Android`
`git submodule update --init --recursive --remote`


## Import SDK in an Android Project

Adding the above lines in gradle file will automatically download and manage the external dependencies.
Start using the SDK files by importing the appropriate SDK package.

`import com.salesforce.referral.api.*`
`import com.salesforce.referral.entities.*`
`import com.salesforce.referral.repository.*`
`import com.salesforce.referral.utils.*`

## ReferralsRepository

The `ReferralsRepository` class is the core of the SDK, providing functionalities to manage referral related operations. Key functionalities include:
- Enrolling users in referral programs using different identifiers like membership numbers or contact IDs.
- Managing referral events and tracking their outcomes.
- Customizing API calls based on the referral program's needs.

### Usage

Enrolling a New Member
```
let repository: ReferralsRepository // Inject using Dagger constructs
val result: ApiResponse<ReferralEnrollmentResponse> = repository.enrollNewCustomerAsAdvocateOfPromotion(firstName = "John",
                                                                                                        lastName = "Doe",
                                                                                                        email = "john.doe@example.com",
                                                                                                        promotionName  = "Referral Program",
                                                                                                        promotionCode  = "PromoCode"
                                                                                                    )
```
Enrolling an Existing Member with Membership Number
```
val result: ApiResponse<ReferralEnrollmentResponse> = repository.enrollExistingAdvocateToPromotionWithMembershipNumber(
                        promotionName = "Referral Program", promotionCode = "PromoCode", membershipNumber = "1234567890"
                    )
```
Enrolling an Existing Member with Contact Id
```
val result: ApiResponse<ReferralEnrollmentResponse> = repository.enrollExistingAdvocateToPromotionWithContactId(
                        promotionName = "Referral Program", promotionCode = "PromoCode", contactId = "0c12sed3456kpPl"
                    )
```
Handling a Referral Event
```
val eventOutput: ApiResponse<ReferralEventResponse> = repository.sendReferrals(referralCode = "ReferralCode", emails = listOf("john.doe@example.com")
```

For a detailed understanding of each method and its parameters, please refer to the comments in the provided `ReferralsRepository` code.

## Contribute to the SDK

You can contribute to the development of the Referral Mobile SDK.
1. Fork the Referral Mobile SDK for Android [repository](https://github.com/loyaltysampleapp/ReferralMobileSDK-Android)
2. Create a branch with a descriptive name.
3. Implement your changes.
4. Test your changes.
5. Submit a pull request.

See also:
[Fork a repo](https://docs.github.com/en/get-started/quickstart/fork-a-repo)

## License

ReferralMobileSDK-Android is available under the BSD 3-Clause License.

Copyright (c) 2023, Salesforce Industries
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
