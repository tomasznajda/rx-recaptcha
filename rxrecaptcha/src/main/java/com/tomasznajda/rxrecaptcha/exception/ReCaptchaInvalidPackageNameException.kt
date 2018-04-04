package com.tomasznajda.rxrecaptcha.exception

import com.google.android.gms.common.api.ApiException
import com.tomasznajda.rxrecaptcha.exception._base.ReCaptchaException

class ReCaptchaInvalidPackageNameException(cause: ApiException) : ReCaptchaException(cause) {

    override val message =
            "The calling app's package name doesn't match any of the names that you've associated with " +
                    "the site key. Add the calling app's package name to the site key on the " +
                    "<a href=\"https://www.google.com/recaptcha/admin\">reCAPTCHA Admin Console</a>, " +
                    "or disable package name validation for your site key."
}