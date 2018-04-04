package com.tomasznajda.rxrecaptcha.exception._base

open class ReCaptchaException : Exception {

    constructor() : super()
    constructor(cause: Throwable) : super(cause)
}