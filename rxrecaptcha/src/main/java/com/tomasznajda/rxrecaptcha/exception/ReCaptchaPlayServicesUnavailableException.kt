package com.tomasznajda.rxrecaptcha.exception

import com.tomasznajda.rxrecaptcha.exception._base.ReCaptchaException

class ReCaptchaPlayServicesUnavailableException : ReCaptchaException() {

    override val message =
            "ReCaptcha relies on Google Play services, which is not supported by your device. " +
                    "Contact the manufacturer for assistance."
}