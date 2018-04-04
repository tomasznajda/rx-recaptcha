package com.tomasznajda.rxrecaptcha.exception

import com.google.android.gms.common.api.ApiException
import com.tomasznajda.rxrecaptcha.exception._base.ReCaptchaException

class ReCaptchaInvalidSiteKeyException(cause: ApiException) : ReCaptchaException(cause) {

    override val message =
            "The site key is invalid. Check that you've registered an API key successfully " +
                    "and that you've correctly copied the site key as a parameter when calling the API."
}