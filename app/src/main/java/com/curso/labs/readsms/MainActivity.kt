package com.curso.labs.readsms

import android.app.ListActivity
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.CursorAdapter
import android.widget.TextView

class MainActivity : ListActivity() {

    val SMS = Uri.parse("content://sms")
    val PERMISSION_REQUEST_READ_SMS = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        readSMS()
    }

    object SmsColumns {
        val ID = "_id"
        val ADDRESS = "address"
        val DATE = "date"
        val BODY = "body"
    }

    private inner class SmsCursorAdapter(
        context: Context,
        c: Cursor,
        autoRequery: Boolean
    ): CursorAdapter(context, c, autoRequery){

        override fun newView(context: Context?, cursor: Cursor?, viewGroup: ViewGroup?): View {
            return View.inflate(context, R.layout.activity_main, null)
        }

        override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
            view?.findViewById<TextView>(R.id.smsOrigin)?.text = cursor?.getString(cursor.getColumnIndexOrThrow(SmsColumns.ADDRESS))
            view?.findViewById<TextView>(R.id.smsBody)?.text = cursor?.getString(cursor.getColumnIndexOrThrow(SmsColumns.BODY))
            view?.findViewById<TextView>(R.id.smsDate)?.text = cursor?.getString(cursor.getColumnIndexOrThrow(SmsColumns.DATE))
        }
    }

    private fun readSMS() {
        val cursor = contentResolver.query(SMS,
                arrayOf(
                    SmsColumns.ID,
                    SmsColumns.ADDRESS,
                    SmsColumns.DATE,
                    SmsColumns.BODY),
                null,
                null,
                SmsColumns.DATE + " DESC")

        val adapter = cursor?.let { SmsCursorAdapter(this, it, true) }
        listAdapter = adapter
    }

    // at this point, the application won't run. There is an error, we need to check if the user has granted
    // the intented permission and request for permission in case it is required

}