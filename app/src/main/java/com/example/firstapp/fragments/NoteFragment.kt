package com.example.firstapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.firstapp.R
import com.example.firstapp.activities.MainApp
import com.example.firstapp.databinding.FragmentNoteBinding
import com.example.firstapp.db.MainDataBase
import com.example.firstapp.db.MainViewModel


class NoteFragment : BaseFragment() {
    private lateinit var bind: FragmentNoteBinding
    private val mainViewModel : MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }
    override fun onClickNew() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       bind = FragmentNoteBinding.inflate(inflater,container,false)
        return bind.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = NoteFragment()

    }
}