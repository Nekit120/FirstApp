package com.example.firstapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firstapp.R
import com.example.firstapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var bind : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater).apply {setContentView(this.root)}

        setBottomNavListener()

    }

    private fun setBottomNavListener() = with(bind){
       navView.setOnItemSelectedListener {

           when(it.itemId){
               R.id.notes -> {
                   val toast = Toast.makeText(applicationContext,"notes",Toast.LENGTH_SHORT)
                   toast.show()
               }

               R.id.newItem -> {
                   val toast = Toast.makeText(applicationContext, "newItem",Toast.LENGTH_SHORT)
                   toast.show()
               }

               R.id.settings -> {
                   val toast = Toast.makeText(applicationContext, "settings",Toast.LENGTH_SHORT)
                   toast.show()
               }

               R.id.shopList -> {
                   val toast = Toast.makeText(applicationContext, "shopList",Toast.LENGTH_SHORT)
                   toast.show()
               }

           }
           true
       }
    }
}