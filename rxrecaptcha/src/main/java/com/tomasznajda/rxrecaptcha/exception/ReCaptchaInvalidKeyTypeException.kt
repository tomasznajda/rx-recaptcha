package com.tomasznajda.rxrecaptcha.exception

import com.google.android.gms.common.api.ApiException
import com.tomasznajda.rxrecaptcha.exception._base.ReCaptchaException

class ReCaptchaInvalidKeyTypeException(cause: ApiException) : ReCaptchaException(cause) {

    override val message =
            "The type of site key is invalid. Create a new site key by navigating to the " +
                    "<a href=\"https://g.co/recaptcha/androidsignup\">reCAPTCHA Android signup site</a>"
}