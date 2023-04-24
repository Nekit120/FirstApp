package com.example.firstapp.fragments

import android.app.Activity
import android.content.Intent
import android.icu.text.CaseMap.Title
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.example.firstapp.R
import com.example.firstapp.activities.MainApp
import com.example.firstapp.activities.NewNoteActivity
import com.example.firstapp.databinding.FragmentNoteBinding
import com.example.firstapp.db.MainDataBase
import com.example.firstapp.db.MainViewModel


class NoteFragment : BaseFragment() {
    private lateinit var bind: FragmentNoteBinding
    private lateinit var editLauncher: ActivityResultLauncher<Intent>
    private val mainViewModel : MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }
    override fun onClickNew() {
     editLauncher.launch(Intent(activity,NewNoteActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        onEditResult()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       bind = FragmentNoteBinding.inflate(inflater,container,false)

        return bind.root
    }

    //получение результата с активити
    private fun onEditResult(){
        editLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK)
            {
                bind.TeVe.text=it.data?.getStringExtra(TITLE_KEY)
            }
        }
    }
    companion object {
        const val TITLE_KEY = "title_key"
        const val DESCRIPTION_KEY = "desk_key"
        @JvmStatic
        fun newInstance() = NoteFragment()

    }
}