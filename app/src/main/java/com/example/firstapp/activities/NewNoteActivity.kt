package com.example.firstapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.example.firstapp.R
import com.example.firstapp.databinding.ActivityNewNoteBinding
import com.example.firstapp.entities.NoteItem
import com.example.firstapp.fragments.NoteFragment
import java.text.SimpleDateFormat
import java.util.*

class NewNoteActivity : AppCompatActivity() {

    private lateinit var bind: ActivityNewNoteBinding
    private var note: NoteItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(bind.root)
        actionBarSettings()
        getNote()
    }

    private fun getNote(){
        val sNote = intent.getSerializableExtra(NoteFragment.NEW_NOTE_KEY)
        sNote?.let {
            note = intent.getSerializableExtra(NoteFragment.NEW_NOTE_KEY) as NoteItem
            fillNote()
        }

    }

    private fun fillNote() = with(bind){
        edTitle.setText(note?.title)
        edDescription.setText(note?.content)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nw_note_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
//обработчик нажатий
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
     if(item.itemId == R.id.id_save){
         setMainResult()
     } else if (item.itemId== android.R.id.home){
         finish()
     }
        return super.onOptionsItemSelected(item)
    }

    private fun updateNote(): NoteItem? = with(bind){
        return note?.copy(
            title=edTitle.text.toString(),
            content = edDescription.text.toString())


    }


// получение результата
    private fun setMainResult(){
    var editState = "new"
    val tempNote:NoteItem? = if (note == null){
       createNewNote()
    } else {
        editState = "update"
        updateNote()
    }
        val i = intent.apply {
         putExtra(NoteFragment.NEW_NOTE_KEY,tempNote)
         putExtra(NoteFragment.EDIT_STATE_KEY,editState)
        }
    setResult(RESULT_OK,i)
    finish()
    }

    private fun createNewNote(): NoteItem{
        return NoteItem(
            null,
            bind.edTitle.text.toString(),
            bind.edDescription.text.toString(),
            getCurrentTime(),
            ""

        )
    }

    private fun getCurrentTime(): String{
        val formatter = SimpleDateFormat("hh:mm:ss - yyyy/MM/dd", Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }
    private fun actionBarSettings(){
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }
}