package com.tomasznajda.rxrecaptcha.exception

import com.tomasznajda.rxrecaptcha.exception._base.ReCaptchaException

class ReCaptchaEmptyTokenException : ReCaptchaException() {

    override val message = "Token provided by the SafetyNet reCAPTCHA API is empty."
}