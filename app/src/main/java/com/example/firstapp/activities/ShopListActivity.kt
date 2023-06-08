package com.example.firstapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MenuItem.OnActionExpandListener
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.example.firstapp.R
import com.example.firstapp.databinding.ActivityShopListBinding
import com.example.firstapp.db.MainViewModel
import com.example.firstapp.entities.ShopListNameItem

@Suppress("DEPRECATION")
class ShopListActivity : AppCompatActivity() {
    private lateinit var bind : ActivityShopListBinding
    private var shopListNameItem: ShopListNameItem? = null
    private lateinit var saveItem: MenuItem

    //Объясвление ViewModel
    private val mainViewModel : MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory((applicationContext as MainApp).database)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityShopListBinding.inflate(layoutInflater).apply { setContentView(this.root) }
        init()
    }
    //получение информации с фрагмента
    private fun init(){
        shopListNameItem = intent.getSerializableExtra(SHOP_LIST_NAME) as ShopListNameItem
        bind.tvTest.text = shopListNameItem?.name
    }
//Создание меню
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shop_list_menu, menu)
        saveItem= menu?.findItem(R.id.save_item)!!
        val newItem= menu.findItem(R.id.new_item)
        newItem.setOnActionExpandListener(expandActionView())
        saveItem.isVisible = false
        return true
    }
//слушатель открыт ли plainText или нет, чтобы сделать видимой кнопку сохранения
    private fun expandActionView(): MenuItem.OnActionExpandListener{
        return object : MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                saveItem.isVisible = true
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                saveItem.isVisible = false
                invalidateOptionsMenu()
                return true
            }

        }
    }

    //ключи
    companion object{
        const val SHOP_LIST_NAME = "shop_list_name"
    }
}