package com.curso.labs.readsms

import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat

fun convertLongToDateString(systemTime: Long?): String {
    return SimpleDateFormat("yyyy-dd-MM' 'HH:mm")
        .format(systemTime).toString()
}