package com.example.firstapp.utils

import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*

object TimeMeneger {
    const val DEF_TIME_FORMAT = "hh:mm:ss - yyyy/MM/dd"
    //считывает время и дату
    fun getCurrentTime(): String{
        val formatter = SimpleDateFormat(DEF_TIME_FORMAT, Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }
    fun getTimeFormat(time:String,defPreferences: SharedPreferences):String{
        val defFormatter = SimpleDateFormat(DEF_TIME_FORMAT, Locale.getDefault())
        val defDate = defFormatter.parse(time)
        val newFormat = defPreferences.getString("time_format_key", DEF_TIME_FORMAT)
        val newFormatter = SimpleDateFormat(newFormat, Locale.getDefault())
        return if (defDate != null){
            newFormatter.format(defDate)
        }else {time}
    }
}