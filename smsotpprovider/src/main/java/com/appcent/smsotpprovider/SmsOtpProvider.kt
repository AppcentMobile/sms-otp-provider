package com.appcent.smsotpprovider

import android.content.BroadcastReceiver
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetrieverClient

class SmsOtpProvider(private val activity: AppCompatActivity) {

    private var mRetrieverClient: SmsRetrieverClient = SmsRetriever.getClient(activity)
    private lateinit var mSmsRetrieverCallBack: SmsRetrieverCallBack
    private lateinit var mOtpDigits: OtpDigits


    init {

        getOtpData().observe(activity, Observer { result ->
            result.fold(
                onSuccess = {
                    val otp = findOtp(it)
                    mSmsRetrieverCallBack.onSuccess(otp)
                },
                onFailure = {
                    mSmsRetrieverCallBack.onError(it.toString())
                }
            )
        })

    }


    fun startForHashCode(
        otpDigits: OtpDigits,
        callBack: SmsRetrieverCallBack
    ) {
        this.mSmsRetrieverCallBack = callBack
        this.mOtpDigits = otpDigits

        mRetrieverClient.startSmsRetriever().addOnFailureListener {
            mSmsRetrieverCallBack.onError(it.toString())
        }.addOnSuccessListener {
            val broadcastReceiver = SmsOtpBroadCastReceiver()
            registerToLifeCycleManager(broadcastReceiver)

        }
    }


    fun startForOneTimeVerification(
        phoneNumber: String,
        otpDigits: OtpDigits,
        callBack: SmsRetrieverCallBack
    ) {
        this.mSmsRetrieverCallBack = callBack
        this.mOtpDigits = otpDigits

        mRetrieverClient.startSmsUserConsent(phoneNumber).addOnFailureListener {
            mSmsRetrieverCallBack.onError(it.toString())
        }.addOnSuccessListener {
            val broadcastReceiver = OneTimeVerificationBroadCastReceiver(activity)
            registerToLifeCycleManager(broadcastReceiver)
        }
    }

    private fun findOtp(message: String): String? {
        val regex = Regex(mOtpDigits.s)
        val otp = regex.find(message)
        return otp?.value
    }

    private fun registerToLifeCycleManager(broadcastReceiver: BroadcastReceiver) {
        val lifeCycleManager = BroadCastReceiverLifeCycleManager(activity, broadcastReceiver)
        lifeCycleManager.start()
    }


}