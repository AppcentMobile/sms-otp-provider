package com.appcent.smsotpprovider

interface SmsRetrieverCallBack {
    fun onSuccess(otp: String?)
    fun onError(error: String)
}