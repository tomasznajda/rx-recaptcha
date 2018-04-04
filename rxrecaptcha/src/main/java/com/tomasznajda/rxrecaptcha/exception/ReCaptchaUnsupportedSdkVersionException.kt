package com.tomasznajda.rxrecaptcha.exception

import com.google.android.gms.common.api.ApiException
import com.tomasznajda.rxrecaptcha.exception._base.ReCaptchaException

class ReCaptchaUnsupportedSdkVersionException(cause: ApiException) : ReCaptchaException(cause) {

    override val message =
            "The API isn't supported on the device's Android SDK version. " +
                    "Upgrade to a new version of the Android SDK, then try communicating with the API again."
}