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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstapp.R
import com.example.firstapp.activities.MainApp
import com.example.firstapp.activities.NewNoteActivity
import com.example.firstapp.databinding.FragmentNoteBinding
import com.example.firstapp.db.MainDataBase
import com.example.firstapp.db.MainViewModel
import com.example.firstapp.db.NoteAdapter
import com.example.firstapp.entities.NoteItem


class NoteFragment : BaseFragment(),NoteAdapter.Listener {
    private lateinit var bind: FragmentNoteBinding
    private lateinit var editLauncher: ActivityResultLauncher<Intent>
    private lateinit var adapter: NoteAdapter

//Создание бд
    private val mainViewModel : MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }
//переход на активити, при создании новой заметки
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

//инициализация RcView
    private fun initRcView() = with(bind){
     rcViewNote.layoutManager = LinearLayoutManager(activity)
        adapter = NoteAdapter(listener = this@NoteFragment)
        rcViewNote.adapter=adapter
    }

//постоянно смотрит на изменения ( LiveData )
    private fun observer(){
        mainViewModel.allNotes.observe(viewLifecycleOwner,{
            adapter.submitList(it)
        })
    }

//получение результата с активити
    private fun onEditResult(){
        editLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK)
            {
                val editState = it.data?.getStringExtra(EDIT_STATE_KEY)
                if(editState == "new"){
                mainViewModel.insertNote(it.data?.getSerializableExtra(NEW_NOTE_KEY) as NoteItem )
                } else{ mainViewModel.updateNote(it.data?.getSerializableExtra(NEW_NOTE_KEY) as NoteItem)}
            }
        }
    }
//удаление заметки
    override fun deleteItem(id: Int) {
        mainViewModel.deleteNote(id)
    }
//запуск активити, и передача заметки при нажатии на элемент
    override fun onClickItem(note: NoteItem) {
        val intent = Intent(activity,NewNoteActivity::class.java).apply {
            putExtra(NEW_NOTE_KEY,note)
        }
        editLauncher.launch(intent)
    }

// ключи тут, чтобы использовать везде
    companion object {
        const val NEW_NOTE_KEY = "note_key"
        const val EDIT_STATE_KEY = "edit_key"
        @JvmStatic
        fun newInstance() = NoteFragment()

    }

}