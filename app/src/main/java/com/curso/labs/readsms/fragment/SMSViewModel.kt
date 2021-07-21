package com.curso.labs.readsms.fragment

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.curso.labs.readsms.Sms
import com.curso.labs.readsms.convertLongToDateString

class SMSViewModel(private val application: Application) : ViewModel() {
    val SMS = Uri.parse("content://sms")

    var messages = MutableLiveData<ArrayList<Sms>>()

    var listMessage = ArrayList<Sms>()

    init {
        messages.postValue(ArrayList())
        readSMS()
    }

    private fun readSMS() {
        val cursor = application.contentResolver.query(SMS,
            arrayOf(
                SmsColumns.ID,
                SmsColumns.ADDRESS,
                SmsColumns.DATE,
                SmsColumns.BODY),
            null,
            null,
            SmsColumns.DATE + " DESC")

            if (cursor != null) {
                cursor.moveToFirst()
                do {
                    val eachNumber = cursor.getString(cursor.getColumnIndexOrThrow(SmsColumns.ADDRESS))
                    // convertir la fecha a una fecha legible
                    val eachDate = cursor.getLong(cursor.getColumnIndexOrThrow(SmsColumns.DATE))
                    val eachMessage = cursor.getString(cursor.getColumnIndexOrThrow(SmsColumns.BODY))
                    val sms = Sms(eachNumber, eachDate, eachMessage)
                    listMessage.add(sms)


                }while (cursor.moveToNext())
                cursor.close()
                messages.postValue(listMessage)
                Log.v("SMSVM", "SMS: ${ messages.value?.get(0) }")
            }
            else{
                Log.v("SMSVM", "SMS: El cursor es null")
            }

    }


    object SmsColumns {
        val ID = "_id"
        val ADDRESS = "address"
        val DATE = "date"
        val BODY = "body"
    }
}