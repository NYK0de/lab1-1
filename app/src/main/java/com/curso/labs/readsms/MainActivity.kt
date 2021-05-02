package com.curso.labs.readsms

import android.Manifest
import android.app.ListActivity
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.CursorAdapter
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : ListActivity() {

    val SMS = Uri.parse("content://sms")
    val PERMISSION_REQUEST_READ_SMS = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        // Step 2: Check for permissions
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
        //val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Log.v("PERM-EXISTS", "El permiso ha sido dado antes")
            readSMS()
        }
        else{
            Log.v("PERM-EXISTS", "Pediremos al usuario que asigne el permiso")
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_SMS),
                    PERMISSION_REQUEST_READ_SMS
            )
        }

        // step 4: fixing problems in layout.xml to show sms. Each Textview is showed above others


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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode){
            PERMISSION_REQUEST_READ_SMS -> {
                // if request is cancelled, the result arrays are empty.
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // Permission was granted
                    Log.v("PERM-EXISTS", "El permiso brindado por el usuario")
                    readSMS()
                }
                else{
                    // Permission denied, you can't access to SMS
                    Log.v("PERM-EXISTS", "No Fue Dado")
                }
                return
            }
        }
    }
}