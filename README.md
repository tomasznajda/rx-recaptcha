# rx-recaptcha

An easy way to use [SafetyNet reCAPTCHA](https://developer.android.com/training/safetynet/recaptcha.html) with [RxJava2](https://github.com/ReactiveX/RxJava)


## Usage

### 1. Register a reCAPTCHA key pair
To register a key pair for use with the SafetyNet reCAPTCHA API, navigate to the [reCAPTCHA Android signup site](https://g.co/recaptcha/androidsignup)

### 2. Use reCAPTCHA verification inside your Android app
```kotlin

ReCaptcha(context = this)
        .verify(YOUR_SITE_KEY)
        .subscribeBy(
                onSuccess = { printToken(token = it) },
                onError = { printError(error = it) })
```

### 3. Validate reCAPTCHA response token
To learn how to validate the user's response token, see [Verifying the user's response](https://developers.google.com/recaptcha/docs/verify)

## Usage of sample

1. Register a reCAPTCHA key pair: [reCAPTCHA Android signup site](https://g.co/recaptcha/androidsignup)
2. Put your "Site key" inside MainActivity#YOUR_SITE_KEY constant
3. Compile & run the app

## Useful links
- [SafetyNet reCAPTCHA API](https://developer.android.com/training/safetynet/recaptcha.html)
- [reCAPTCHA Android signup site](https://g.co/recaptcha/androidsignup)
- [Validating reCAPTCHA token](https://developers.google.com/recaptcha/docs/verify)
- [SafetyNetStatusCodes](https://developers.google.com/android/reference/com/google/android/gms/safetynet/SafetyNetStatusCodes)
- [CommonStatusCodes](https://developers.google.com/android/reference/com/google/android/gms/common/api/CommonStatusCodes)