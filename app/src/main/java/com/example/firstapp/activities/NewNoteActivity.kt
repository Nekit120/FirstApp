package com.example.firstapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.firstapp.R
import com.example.firstapp.databinding.ActivityNewNoteBinding
import com.example.firstapp.fragments.NoteFragment

class NewNoteActivity : AppCompatActivity() {

    private lateinit var bind: ActivityNewNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(bind.root)
        actionBarSettings()
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
// получение результата
    private fun setMainResult(){
        val i = intent.apply {
            putExtra(NoteFragment.TITLE_KEY,bind.edTitle.text.toString())
            putExtra(NoteFragment.DESCRIPTION_KEY,bind.edDescription.text.toString())
        }
    setResult(RESULT_OK,i)
    finish()
    }

    private fun actionBarSettings(){
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }
}