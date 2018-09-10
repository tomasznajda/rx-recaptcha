package com.tomasznajda.rxrecaptcha

import android.content.Context
import com.google.android.gms.safetynet.SafetyNet

class SafetyNetProvider {

    fun getClient(context: Context) = SafetyNet.getClient(context)!!
}