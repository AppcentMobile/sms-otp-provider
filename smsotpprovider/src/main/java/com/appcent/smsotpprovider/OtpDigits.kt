package com.appcent.smsotpprovider

enum class OtpDigits(val s: String) {
    FOUR_DIGITS("\\d{4}"),
    SIX_DIGITS("\\d{6}")
}