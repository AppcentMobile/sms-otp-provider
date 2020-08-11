package com.appcent.sampleapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.appcent.smsotpprovider.OtpDigits
import com.appcent.smsotpprovider.SmsOtpProvider
import com.appcent.smsotpprovider.SmsRetrieverCallBack

class SecondActivity : AppCompatActivity(), SmsRetrieverCallBack {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val provider = SmsOtpProvider(this)
        // provider.startForHashCode(OtpDigits.SIX_DIGITS, this)
        provider.startForOneTimeVerification("+phonenumber", OtpDigits.SIX_DIGITS, this)

    }

    override fun onSuccess(otp: String?) {
        Toast.makeText(this, otp, Toast.LENGTH_SHORT).show()
    }

    override fun onError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}