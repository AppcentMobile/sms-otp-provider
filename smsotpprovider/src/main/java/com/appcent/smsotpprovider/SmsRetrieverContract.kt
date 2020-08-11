package com.appcent.smsotpprovider

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.gms.auth.api.phone.SmsRetriever


internal class SmsRetrieverContract(private val intent: Intent) :
    ActivityResultContract<Int, String>() {

    override fun createIntent(context: Context, input: Int): Intent {
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String {
        val message = intent?.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
        return if (resultCode == Activity.RESULT_OK && message != null) message
        else ""
    }
}