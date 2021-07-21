package com.curso.labs.readsms

import android.telephony.PhoneNumberUtils
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("smsDateFormatted")
fun TextView.setSmsDateFormatted(item: Sms){
    text = convertLongToDateString(item.date)
}

@BindingAdapter("smsPhoneFormatted")
fun TextView.setPhoneNumberFormat(item: Sms){
    text = PhoneNumberUtils.formatNumber(item.number, "US")
}