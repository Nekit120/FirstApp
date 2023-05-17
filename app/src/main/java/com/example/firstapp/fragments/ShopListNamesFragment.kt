package com.example.firstapp.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstapp.R
import com.example.firstapp.activities.MainApp
import com.example.firstapp.databinding.FragmentShomListNamesBinding
import com.example.firstapp.db.MainViewModel
import com.example.firstapp.db.ShopListNameAdapter
import com.example.firstapp.dialogs.NewListDialog
import com.example.firstapp.entities.ShoppingListNames
import com.example.firstapp.utils.TimeMeneger


class ShopListNamesFragment : BaseFragment(){
    private lateinit var bind: FragmentShomListNamesBinding
    private lateinit var adapter: ShopListNameAdapter



    //Создание бд
    private val mainViewModel : MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }


    override fun onClickNew() {
        NewListDialog.showDialog(activity as AppCompatActivity, object : NewListDialog.Listener{
            override fun onClick(name: String) {
                val shopListName = ShoppingListNames(
                    null,
                    name,
                    TimeMeneger.getCurrentTime(),
                    0,
                    0,
                    ""
                )
                mainViewModel.insertShopListName(shopListName)
            }

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind =  FragmentShomListNamesBinding.inflate(inflater,container,false)

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    //инициализация RcView
    private fun initRcView() = with(bind){
        rcView.layoutManager = LinearLayoutManager(activity)
        adapter = ShopListNameAdapter()
        rcView.adapter=adapter
    }

    //постоянно смотрит на изменения ( LiveData )
    private fun observer(){
        mainViewModel.allShoppingListNames.observe(viewLifecycleOwner,{
            adapter.submitList(it)
        })
    }

    // ключи тут, чтобы использовать везде
    companion object {
        @JvmStatic
        fun newInstance() = ShopListNamesFragment()

    }




}