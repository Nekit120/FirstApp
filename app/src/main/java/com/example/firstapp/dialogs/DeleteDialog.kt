package com.example.firstapp.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.firstapp.databinding.DeleteDialogBinding
import com.example.firstapp.databinding.NewListDialogBinding
import com.example.firstapp.db.NoteAdapter

object DeleteDialog {
    fun showDialog(context: Context,listener: Listener){
        var dialog :AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val bind = DeleteDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(bind.root)
        bind.apply {

            bDelete.setOnClickListener{
                listener.onClick()
                dialog?.dismiss()
            }
            bCancel.setOnClickListener{
                dialog?.dismiss()
            }
        }
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }

    interface Listener {
        fun onClick()
    }
}