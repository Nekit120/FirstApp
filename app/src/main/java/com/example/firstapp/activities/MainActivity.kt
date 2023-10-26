package com.example.firstapp.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.preference.PreferenceManager
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
    private var currentMenuItemId = R.id.notes
    private lateinit var defPref:SharedPreferences
    private var currentTheme = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        defPref= PreferenceManager.getDefaultSharedPreferences(this)
        currentTheme = defPref.getString("theme_key","peach").toString()
        setTheme(getSelectedTheme())
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        bind.navView.menu.getItem(1).setChecked(false)
        FragmentManager.setFragment(NoteFragment.newInstance(),this)
        setBottomNavListener()

    }
    //обработчик нажатий на нижнее меню
    private fun setBottomNavListener() {
        bind.navView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.notes -> {
                    currentMenuItemId = R.id.notes
                    FragmentManager.setFragment(NoteFragment.newInstance(),this)
                }

                R.id.newItem -> {
                    FragmentManager.currentFrag?.onClickNew()
                }

                R.id.settings -> {
                   startActivity(Intent(this,SettingsActivity::class.java))
                }

                R.id.shopList -> {
                    currentMenuItemId = R.id.shopList
                    FragmentManager.setFragment(ShopListNamesFragment.newInstance(),this)
                }

            }
            true
        }
    }

    private fun getSelectedTheme():Int{
        return if (defPref.getString("theme_key","peach")=="peach") {
            R.style.Theme_FirstApp
        } else{ R.style.Theme_FirstAppGreen }
    }

    override fun onResume() {
        super.onResume()
        bind.navView.selectedItemId= currentMenuItemId
        if(defPref.getString("theme_key","peach") != currentTheme) recreate()

    }
    override fun onClick(name: String) {    }
}