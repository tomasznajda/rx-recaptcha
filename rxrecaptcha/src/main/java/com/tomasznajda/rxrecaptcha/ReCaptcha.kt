package com.tomasznajda.rxrecaptcha

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ApiException
import com.tomasznajda.rxrecaptcha.exception.*
import com.tomasznajda.rxrecaptcha.exception._base.ReCaptchaException
import com.tomasznajda.rxrecaptcha.util.RECAPTCHA_INVALID_KEYTYPE
import com.tomasznajda.rxrecaptcha.util.RECAPTCHA_INVALID_PACKAGE_NAME
import com.tomasznajda.rxrecaptcha.util.RECAPTCHA_INVALID_SITEKEY
import com.tomasznajda.rxrecaptcha.util.UNSUPPORTED_SDK_VERSION
import io.reactivex.Single

class ReCaptcha {

    private val safetyNetProvider: SafetyNetProvider
    private val googleApiAvailability: GoogleApiAvailability

    constructor() : this(SafetyNetProvider(), GoogleApiAvailability.getInstance())
    internal constructor(safetyNetProvider: SafetyNetProvider,
                         googleApiAvailability: GoogleApiAvailability) {
        this.safetyNetProvider = safetyNetProvider
        this.googleApiAvailability = googleApiAvailability
    }

    fun verify(context: Context, siteKey: String) =
            if (isGooglePlayServicesAvailable(context)) {
                Single.create<String> { emitter ->
                    safetyNetProvider
                            .getClient(context)
                            .verifyWithRecaptcha(siteKey)
                            .addOnSuccessListener {
                                it.tokenResult.let {
                                    if (it.isNotBlank()) emitter.onSuccess(it)
                                    else emitter.onError(ReCaptchaEmptyTokenException())
                                }
                            }
                            .addOnFailureListener { emitter.onError(it.mapToReCaptchaException()) }
                }!!
            } else {
                Single.error(ReCaptchaPlayServicesUnavailableException())
            }

    private fun isGooglePlayServicesAvailable(context: Context) =
            googleApiAvailability.isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS

    private fun Exception.mapToReCaptchaException() =
            (this as? ApiException)?.mapToReCaptchaException() ?: ReCaptchaException(this)

    private fun ApiException.mapToReCaptchaException() =
            when (statusCode) {
                RECAPTCHA_INVALID_SITEKEY -> ReCaptchaInvalidSiteKeyException(this)
                RECAPTCHA_INVALID_KEYTYPE -> ReCaptchaInvalidKeyTypeException(this)
                RECAPTCHA_INVALID_PACKAGE_NAME -> ReCaptchaInvalidPackageNameException(this)
                UNSUPPORTED_SDK_VERSION -> ReCaptchaUnsupportedSdkVersionException(this)
                else -> ReCaptchaException(this)
            }
}