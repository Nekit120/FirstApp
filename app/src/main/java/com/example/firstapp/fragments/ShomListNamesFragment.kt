package com.example.firstapp.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.firstapp.activities.MainApp
import com.example.firstapp.databinding.FragmentShomListNamesBinding
import com.example.firstapp.db.MainViewModel
import com.example.firstapp.dialogs.NewListDialog


class ShopListNamesFragment : BaseFragment() {
    private lateinit var bind: FragmentShomListNamesBinding


    //Создание бд
    private val mainViewModel : MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }


    override fun onClickNew() {
    NewListDialog.showDialog(activity as AppCompatActivity, object : NewListDialog.listener{
        override fun onClick(name: String) {

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

    }

    //постоянно смотрит на изменения ( LiveData )
    private fun observer(){
        mainViewModel.allNotes.observe(viewLifecycleOwner,{
        })
    }

    // ключи тут, чтобы использовать везде
    companion object {
        @JvmStatic
        fun newInstance() = ShopListNamesFragment()

    }

}