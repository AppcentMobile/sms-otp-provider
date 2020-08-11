package com.appcent.smsotpprovider

import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

internal class OneTimeVerificationBroadCastReceiver(private val activity: AppCompatActivity) :
    BroadcastReceiver() {

    private val SMS_CONSENT_REQUEST = 8078

    override fun onReceive(context: Context, intent: Intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            val extras: Bundle? = intent.extras
            val status: Status? = extras?.get(SmsRetriever.EXTRA_STATUS) as Status
            when (status?.statusCode) {
                CommonStatusCodes.SUCCESS -> {

                    val consentIntent =
                        extras.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                    try {
                        val res =
                            activity.registerForActivityResult(SmsRetrieverContract(consentIntent!!)) { message ->
                                publishMessage(Result.success(message!!))
                            }

                        res.launch(SMS_CONSENT_REQUEST)

                    } catch (e: ActivityNotFoundException) {
                        publishMessage(Result.failure(e))
                    }
                }

                CommonStatusCodes.TIMEOUT -> {
                    publishMessage(Result.failure(Throwable("Time Out")))
                }


            }
        }
    }

}
