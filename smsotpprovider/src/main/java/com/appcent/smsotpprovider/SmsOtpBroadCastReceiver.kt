package com.appcent.smsotpprovider

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

internal class SmsOtpBroadCastReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {

        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            val extras: Bundle? = intent.extras
            val status: Status? = extras?.get(SmsRetriever.EXTRA_STATUS) as Status
            when (status?.statusCode) {
                CommonStatusCodes.SUCCESS -> {

                    val message: String = extras[SmsRetriever.EXTRA_SMS_MESSAGE] as String
                    publishMessage(Result.success(message))
                }


                CommonStatusCodes.TIMEOUT -> {
                    publishMessage(Result.failure(Throwable("Time Out")))
                }


            }
        }
    }


}

