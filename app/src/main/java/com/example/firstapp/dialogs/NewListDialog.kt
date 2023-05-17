package com.example.firstapp.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.firstapp.databinding.NewListDialogBinding
import com.example.firstapp.db.NoteAdapter

object NewListDialog {
    fun showDialog(context: Context,listener: Listener){
        var dialog :AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val bind = NewListDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(bind.root)
        bind.apply {

            bCreate.setOnClickListener{
              val ListText = edNewListName.text
              var listName = ListText.toString()
              if(listName.isNotEmpty()){
                    listener.onClick(listName)
              }
                dialog?.dismiss()
            }
        }
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }

    interface Listener {
        fun onClick(name: String)
    }
}