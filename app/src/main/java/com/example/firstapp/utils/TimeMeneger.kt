package com.example.firstapp.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeMeneger {

    //считывает время и дату
    fun getCurrentTime(): String{
        val formatter = SimpleDateFormat("hh:mm:ss - yyyy/MM/dd", Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }
}