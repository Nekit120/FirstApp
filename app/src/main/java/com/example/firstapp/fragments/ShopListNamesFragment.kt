package com.example.firstapp.fragments


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstapp.activities.MainApp
import com.example.firstapp.activities.ShopListActivity
import com.example.firstapp.databinding.FragmentShopListNamesBinding
import com.example.firstapp.db.MainViewModel
import com.example.firstapp.db.ShopListNameAdapter
import com.example.firstapp.dialogs.DeleteDialog
import com.example.firstapp.dialogs.NewListDialog
import com.example.firstapp.entities.ShopListNameItem
import com.example.firstapp.utils.TimeMeneger


class ShopListNamesFragment : BaseFragment(), ShopListNameAdapter.Listener{
    private lateinit var bind: FragmentShopListNamesBinding
    private lateinit var adapter: ShopListNameAdapter
    private lateinit var defPref: SharedPreferences



    //Объясвление ViewModel
    private val mainViewModel : MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }


    override fun onClickNew() {
        NewListDialog.showDialog(activity as AppCompatActivity, object : NewListDialog.Listener{
            override fun onClick(name: String) {
                val shopListName = ShopListNameItem(
                    null,
                    name,
                    TimeMeneger.getCurrentTime(),
                    0,
                    0,
                    ""
                )
                mainViewModel.insertShopListName(shopListName)
            }

        },"")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind =  FragmentShopListNamesBinding.inflate(inflater,container,false)

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){

        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    //инициализация RcView
    private fun initRcView() = with(bind){
        rcView.layoutManager = LinearLayoutManager(activity)
        defPref = activity?.let { PreferenceManager.getDefaultSharedPreferences(it) }!!
        adapter = ShopListNameAdapter(this@ShopListNamesFragment,defPref)
        rcView.adapter=adapter
    }

    //постоянно смотрит на изменения ( LiveData )
    private fun observer(){
        mainViewModel.allShopListNameItem.observe(viewLifecycleOwner,{
            adapter.submitList(it)
        })
    }

    // ключи тут, чтобы использовать везде
    companion object {
        @JvmStatic
        fun newInstance() = ShopListNamesFragment()

    }

    override fun deleteItem(id: Int) {
        DeleteDialog.showDialog(context as AppCompatActivity, object : DeleteDialog.Listener {
            override fun onClick() {
                mainViewModel.deleteShopList(id)
            }

        })
    }

    override fun editItem(shopListNameItem: ShopListNameItem) {
        NewListDialog.showDialog(activity as AppCompatActivity, object : NewListDialog.Listener{
            override fun onClick(name: String) {
                mainViewModel.updateListName(shopListNameItem.copy(name = name))
    } }, shopListNameItem.name)}

    override fun onClickItem(ShopListName: ShopListNameItem) {
        val i = Intent(activity,ShopListActivity::class.java).apply {
            putExtra(ShopListActivity.SHOP_LIST_NAME,ShopListName)
        }
    startActivity(i)
    }


}