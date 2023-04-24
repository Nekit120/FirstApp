package com.example.firstapp.fragments

import android.app.Activity
import android.content.Intent
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       bind = FragmentNoteBinding.inflate(inflater,container,false)
        return bind.root
    }
    private fun onEditResult(){
        editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK)
            {}
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() = NoteFragment()

    }
}