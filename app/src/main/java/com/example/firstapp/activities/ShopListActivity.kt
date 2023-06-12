package com.example.firstapp.activities

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.MenuItem.OnActionExpandListener
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstapp.R
import com.example.firstapp.databinding.ActivityShopListBinding
import com.example.firstapp.db.MainViewModel
import com.example.firstapp.db.ShopListItemAdapter
import com.example.firstapp.dialogs.EditListItemDialog
import com.example.firstapp.entities.ShopListItem
import com.example.firstapp.entities.ShopListNameItem
import com.example.firstapp.utils.ShareHelper

@Suppress("DEPRECATION")
class ShopListActivity : AppCompatActivity(), ShopListItemAdapter.Listener {
    private lateinit var bind : ActivityShopListBinding
    private var shopListNameItem: ShopListNameItem? = null
    private lateinit var saveItem: MenuItem
    private var edItem: EditText? =null
    private var adapter: ShopListItemAdapter? = null
    private lateinit var textWatcher: TextWatcher

    //Объясвление ViewModel
    private val mainViewModel : MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory((applicationContext as MainApp).database)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityShopListBinding.inflate(layoutInflater).apply { setContentView(this.root) }
        init()
        initRcView()
        listItemObserver()
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
        edItem = newItem.actionView?.findViewById(R.id.edNewShopItem) as EditText
        newItem.setOnActionExpandListener(expandActionView())
        saveItem.isVisible = false
        textWatcher = textWatcher()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.save_item){
            addNewShopItem()
        }else if(item.itemId == R.id.delete_list){
            mainViewModel.deleteShopList(shopListNameItem?.id!!)
            finish()
        }else if(item.itemId == R.id.clear_list){
            mainViewModel.deleteShopListFullItem(shopListNameItem?.id!!)

        }else if(item.itemId == R.id.share_list){
            startActivity(Intent.createChooser(ShareHelper.shareShopList(adapter?.currentList!!, shopListNameItem?.name!!),"Share by"))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addNewShopItem(){
        if(edItem?.text.toString().isEmpty()) return
        val item = ShopListItem(
            null,
            edItem?.text.toString(),
            null,
            false,
            shopListNameItem?.id!!,
            0
        )
        edItem?.setText("")
        mainViewModel.insertShopListItem(item)
    }
//observer
    private fun listItemObserver(){
        mainViewModel.getAllItemsFromList(shopListNameItem?.id!!).observe(this,{
            adapter?.submitList(it)
            bind.tvEmpty.visibility = if(it.isEmpty()) {View.VISIBLE} else {View.GONE} })
    }

    private fun initRcView() = with(bind){
        adapter = ShopListItemAdapter(this@ShopListActivity)
        rcView.layoutManager =LinearLayoutManager(this@ShopListActivity)
        rcView.adapter= adapter
    }

//слушатель открыт ли plainText или нет, чтобы сделать видимой кнопку сохранения
    private fun expandActionView(): MenuItem.OnActionExpandListener{
        return object : MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                saveItem.isVisible = true
                edItem?.addTextChangedListener(textWatcher)
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                saveItem.isVisible = false
                edItem?.removeTextChangedListener(textWatcher)
                invalidateOptionsMenu()
                return true
            }

        }
    }

    //ключи
    companion object{
        const val SHOP_LIST_NAME = "shop_list_name"
    }

    override fun onClickItem(shopListItem: ShopListItem,state:Int) {
        when(state){
            ShopListItemAdapter.EDIT -> editListItem(shopListItem)
            ShopListItemAdapter.CHECK_BOX ->   mainViewModel.updateListItem(shopListItem)
        }

    }
    private fun textWatcher(): TextWatcher{
        return object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        }
    }
    private fun editListItem(item: ShopListItem){
        EditListItemDialog.showDialog(this,item, object : EditListItemDialog.Listener{
            override fun onClick(item: ShopListItem) {
               mainViewModel.updateListItem(item)
            }

        })
    }
}