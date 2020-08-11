package com.appcent.smsotpprovider

import android.content.BroadcastReceiver
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.auth.api.phone.SmsRetriever

internal class BroadCastReceiverLifeCycleManager(
    private val activity: AppCompatActivity,
    private val broadCastReceiver: BroadcastReceiver
) :
    LifecycleObserver {

    private var lifecycleOwner = activity.lifecycle


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        activity.unregisterReceiver(broadCastReceiver)
    }

    private fun registerBroadcastReceiver(
        activity: AppCompatActivity,
        receiver: BroadcastReceiver
    ) {
        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        activity.registerReceiver(
            receiver,
            intentFilter,
            SmsRetriever.SEND_PERMISSION,
            null
        )
    }

    fun start() {
        registerBroadcastReceiver(activity, broadCastReceiver)
        lifecycleOwner.addObserver(this)
    }


}