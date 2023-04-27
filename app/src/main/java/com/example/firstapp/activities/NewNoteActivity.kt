package com.example.firstapp.activities

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.style.StyleSpan
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import com.example.firstapp.R
import com.example.firstapp.databinding.ActivityNewNoteBinding
import com.example.firstapp.entities.NoteItem
import com.example.firstapp.fragments.NoteFragment
import com.example.firstapp.utils.HtmlManager
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
//забирает данные из фрагмента( если мы передаем )
    private fun getNote(){
        val sNote = intent.getSerializableExtra(NoteFragment.NEW_NOTE_KEY)
        sNote?.let {
            note = intent.getSerializableExtra(NoteFragment.NEW_NOTE_KEY) as NoteItem
            fillNote()
        }

    }
//заполняет разметку по новому
    private fun fillNote() = with(bind){
        edTitle.setText(note?.title)
        edDescription.setText(HtmlManager.getFromHtml(note?.content!!).trim())
    }

//раздувка меню
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
     else if (item.itemId== R.id.id_bold){
      setBoldForSelectedText()
     }
        return super.onOptionsItemSelected(item)
    }
//жирный шрифт
    private fun setBoldForSelectedText() =with(bind){
    val startPosition = edDescription.selectionStart
    val endPosition = edDescription.selectionEnd
    val oldText = edDescription.text.toString()
    val styles = edDescription.text.getSpans(startPosition,endPosition,StyleSpan::class.java)
    var boldStyle:StyleSpan? = null
    if(styles.isNotEmpty()){

   edDescription.setText(oldText)
    }
    else{
        boldStyle = StyleSpan(Typeface.BOLD)
        edDescription.text.setSpan(boldStyle,startPosition,endPosition,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }


    edDescription.text.trim()
    edDescription.setSelection(startPosition)
    }
//обновление заметок
    private fun updateNote(): NoteItem? = with(bind){
        return note?.copy(
            title=edTitle.text.toString(),
            content = HtmlManager.toHtml(edDescription.text)

        )
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
// создает новую разметку
    private fun createNewNote(): NoteItem{
        return NoteItem(
            null,
            bind.edTitle.text.toString(),
            HtmlManager.toHtml(bind.edDescription.text),
            getCurrentTime(),
            ""

        )
    }
//считывает время и дату
    private fun getCurrentTime(): String{
        val formatter = SimpleDateFormat("hh:mm:ss - yyyy/MM/dd", Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }

//кнопка назад
    private fun actionBarSettings(){
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }
}