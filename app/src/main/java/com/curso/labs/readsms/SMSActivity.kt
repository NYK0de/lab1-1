package com.curso.labs.readsms

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.curso.labs.readsms.fragment.SMSFragment

class SMSActivity : AppCompatActivity() {

    val SMS = Uri.parse("content://sms")
    val PERMISSION_REQUEST_READ_SMS = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sms_activity)

    }
}