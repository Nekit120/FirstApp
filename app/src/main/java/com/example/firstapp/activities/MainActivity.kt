package com.example.firstapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firstapp.R
import com.example.firstapp.databinding.ActivityMainBinding
import com.example.firstapp.dialogs.NewListDialog
import com.example.firstapp.fragments.FragmentManager
import com.example.firstapp.fragments.NoteFragment

class MainActivity : AppCompatActivity(), NewListDialog.listener {
    lateinit var bind : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater).apply {setContentView(this.root)}

        setBottomNavListener()

    }
    //обработчик нажатий на нижнее меню
    private fun setBottomNavListener() {
        bind.navView.setOnItemSelectedListener {

            when(it.itemId){
                R.id.notes -> {
                    FragmentManager.setFragment(NoteFragment.newInstance(),this)
                }

                R.id.newItem -> {
                    FragmentManager.currentFrag?.onClickNew()
                }

                R.id.settings -> {
                    val toast = Toast.makeText(applicationContext, "settings",Toast.LENGTH_SHORT)
                    toast.show()
                }

                R.id.shopList -> {
                    NewListDialog.showDialog(   this,this)
                    val toast = Toast.makeText(applicationContext, "shopList",Toast.LENGTH_SHORT)
                    toast.show()
                }

            }
            true
        }
    }
    
    override fun onClick(name: String) {
        TODO("Not yet implemented")
    }
}