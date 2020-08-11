package com.appcent.smsotpprovider



private val smsOtpData = SingleLiveEvent<Result<String>>()

internal fun getOtpData(): SingleLiveEvent<Result<String>> {
    return smsOtpData
}

internal fun publishMessage(result: Result<String>) {

    smsOtpData.setValue(result)
}


