package com.appcent.sampleapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val appSignature = AppSignatureHelper(this)
        val hashList = appSignature.appSignatures
        hashList.forEach {
            Log.e("signature", it)
        }

        tv_redirect.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

    }

}