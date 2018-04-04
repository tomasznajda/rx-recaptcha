package com.tomasznajda.rxrecaptcha.util

/**
 * The site key is invalid. Check that you've registered an API key successfully
 * and that you've correctly copied the site key as a parameter when calling the API.
 */
const val RECAPTCHA_INVALID_SITEKEY = 12007

/**
 * The type of site key is invalid. Create a new site key by navigating to the
 * <a href="https://g.co/recaptcha/androidsignup">reCAPTCHA Android signup site</a>
 */
const val RECAPTCHA_INVALID_KEYTYPE = 12008

/**
 * The calling app's package name doesn't match any of the names that you've associated with
 * the site key. Add the calling app's package name to the site key on the
 * <a href="https://www.google.com/recaptcha/admin">reCAPTCHA Admin Console</a>,
 * or disable package name validation for your site key.
 */
const val RECAPTCHA_INVALID_PACKAGE_NAME = 12013

/**
 * The API isn't supported on the device's Android SDK version.
 * Upgrade to a new version of the Android SDK, then try communicating with the API again.
 */
const val UNSUPPORTED_SDK_VERSION = 12006