package com.tomasznajda.rxrecaptcha

import android.app.Activity
import com.google.android.gms.common.ConnectionResult.API_UNAVAILABLE
import com.google.android.gms.common.ConnectionResult.SUCCESS
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.safetynet.SafetyNetApi
import com.google.android.gms.safetynet.SafetyNetClient
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.tomasznajda.rxrecaptcha.exception.*
import com.tomasznajda.rxrecaptcha.util.RECAPTCHA_INVALID_KEYTYPE
import com.tomasznajda.rxrecaptcha.util.RECAPTCHA_INVALID_PACKAGE_NAME
import com.tomasznajda.rxrecaptcha.util.RECAPTCHA_INVALID_SITEKEY
import com.tomasznajda.rxrecaptcha.util.UNSUPPORTED_SDK_VERSION
import org.junit.Before
import org.junit.Test
import java.util.concurrent.Executor

class ReCaptchaTest {

    val safetyNetProvider = mock<SafetyNetProvider>()
    val safetyNetClient = mock<SafetyNetClient>()
    val googleApiAvailability = mock<GoogleApiAvailability>()
    val task = ReCaptchaTaskMock()

    val reCaptcha = ReCaptcha(safetyNetProvider, googleApiAvailability)

    @Before
    fun setUp() {
        whenever(safetyNetProvider.getClient(any())).doReturn(safetyNetClient)
        whenever(safetyNetClient.verifyWithRecaptcha(any())).doReturn(task)
        whenever(googleApiAvailability.isGooglePlayServicesAvailable(any())).doReturn(SUCCESS)
    }

    @Test
    fun `verify successfully emits token when token is not empty`() {
        val observer = reCaptcha.verify(mock(), "SiteKey").test()
        task.onSuccess("token")
        observer.assertValue("token")
    }

    @Test
    fun `verify emits ReCaptchaPlayServicesUnavailableException when device has no play services`() {
        whenever(googleApiAvailability.isGooglePlayServicesAvailable(any())).doReturn(API_UNAVAILABLE)
        val observer = reCaptcha.verify(mock(), "SiteKey").test()
        observer.assertError { it is ReCaptchaPlayServicesUnavailableException }
    }

    @Test
    fun `verify emits ReCaptchaEmptyTokenException when token is empty`() {
        val observer = reCaptcha.verify(mock(), "SiteKey").test()
        task.onSuccess("")
        observer.assertError { it is ReCaptchaEmptyTokenException }
    }

    @Test
    fun `verify emits ReCaptchaEmptyTokenException when token is blank`() {
        val observer = reCaptcha.verify(mock(), "SiteKey").test()
        task.onSuccess("   ")
        observer.assertError { it is ReCaptchaEmptyTokenException }
    }

    @Test
    fun `verify emits ReCaptchaInvalidKeyTypeException when key type is invalid`() {
        val observer = reCaptcha.verify(mock(), "SiteKey").test()
        task.onFailure(ApiException(Status(RECAPTCHA_INVALID_KEYTYPE)))
        observer.assertError { it is ReCaptchaInvalidKeyTypeException }
    }

    @Test
    fun `verify emits ReCaptchaInvalidPackageNameException when package name is invalid`() {
        val observer = reCaptcha.verify(mock(), "SiteKey").test()
        task.onFailure(ApiException(Status(RECAPTCHA_INVALID_PACKAGE_NAME)))
        observer.assertError { it is ReCaptchaInvalidPackageNameException }
    }

    @Test
    fun `verify emits ReCaptchaInvalidSiteKeyException when site key is invalid`() {
        val observer = reCaptcha.verify(mock(), "SiteKey").test()
        task.onFailure(ApiException(Status(RECAPTCHA_INVALID_SITEKEY)))
        observer.assertError { it is ReCaptchaInvalidSiteKeyException }
    }

    @Test
    fun `verify emits ReCaptchaUnsupportedSdkVersionException when sdk version is unsupported`() {
        val observer = reCaptcha.verify(mock(), "SiteKey").test()
        task.onFailure(ApiException(Status(UNSUPPORTED_SDK_VERSION)))
        observer.assertError { it is ReCaptchaUnsupportedSdkVersionException }
    }

    class ReCaptchaTaskMock : Task<SafetyNetApi.RecaptchaTokenResponse>() {

        private val onSuccessListeners = mutableListOf<OnSuccessListener<in SafetyNetApi.RecaptchaTokenResponse>>()
        private val onFailureListeners = mutableListOf<OnFailureListener>()

        fun onSuccess(token: String) {
            val response = mock<SafetyNetApi.RecaptchaTokenResponse>()
            whenever(response.tokenResult).thenReturn(token)
            onSuccessListeners.forEach { it.onSuccess(response) }
        }

        fun onFailure(exception: Exception) = onFailureListeners.forEach { it.onFailure(exception) }

        override fun addOnSuccessListener(p0: Executor, onSuccessListener: OnSuccessListener<in SafetyNetApi.RecaptchaTokenResponse>) =
                apply { onSuccessListeners.add(onSuccessListener) }

        override fun addOnSuccessListener(p0: Activity, onSuccessListener: OnSuccessListener<in SafetyNetApi.RecaptchaTokenResponse>) =
                apply { onSuccessListeners.add(onSuccessListener) }

        override fun addOnSuccessListener(onSuccessListener: OnSuccessListener<in SafetyNetApi.RecaptchaTokenResponse>) =
                apply { onSuccessListeners.add(onSuccessListener) }

        override fun addOnFailureListener(p0: Executor, onFailureListener: OnFailureListener) =
                apply { onFailureListeners.add(onFailureListener) }

        override fun addOnFailureListener(onFailureListener: OnFailureListener) =
                apply { onFailureListeners.add(onFailureListener) }

        override fun addOnFailureListener(p0: Activity, onFailureListener: OnFailureListener) =
                apply { onFailureListeners.add(onFailureListener) }

        override fun getResult() = mock<SafetyNetApi.RecaptchaTokenResponse>()

        override fun <X : Throwable?> getResult(p0: Class<X>) = mock<SafetyNetApi.RecaptchaTokenResponse>()

        override fun getException() = mock<Exception>()

        override fun isSuccessful() = true

        override fun isComplete() = true

        override fun isCanceled() = false
    }

}