package com.example.firstapp.activities

import android.app.Application
import com.example.firstapp.db.MainDataBase

class MainApp: Application() {
    val database by lazy { MainDataBase.getDatabase(this) }
}