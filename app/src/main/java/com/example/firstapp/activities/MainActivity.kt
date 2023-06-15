package com.example.firstapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firstapp.R
import com.example.firstapp.databinding.ActivityMainBinding
import com.example.firstapp.dialogs.NewListDialog
import com.example.firstapp.fragments.FragmentManager
import com.example.firstapp.fragments.NoteFragment
import com.example.firstapp.fragments.ShopListNamesFragment
import com.example.firstapp.settings.SettingsActivity
import com.example.firstapp.settings.SettingsFragment

class MainActivity : AppCompatActivity(), NewListDialog.Listener {
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
                   startActivity(Intent(this,SettingsActivity::class.java))
                }

                R.id.shopList -> {
                    FragmentManager.setFragment(ShopListNamesFragment.newInstance(),this)
                }

            }
            true
        }
    }
    
    override fun onClick(name: String) {    }
}