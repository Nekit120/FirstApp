package com.example.firstapp.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.firstapp.R
import com.example.firstapp.databinding.EditListItemDialogBinding
import com.example.firstapp.databinding.NewListDialogBinding
import com.example.firstapp.db.NoteAdapter
import com.example.firstapp.entities.ShopListItem

object EditListItemDialog {
    fun showDialog(context: Context,item: ShopListItem ,listener: Listener){
        var dialog :AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val bind = EditListItemDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(bind.root)
        bind.apply {
            edName.setText(item.name)
            if(item.itemInfo != null) edInfo.setText(item.itemInfo)
                bUpdate.setOnClickListener{
                    if(edName.text.toString().isNotEmpty()){
                        val itemInfo = if(edInfo.text.toString().isEmpty()) null else edInfo.text.toString()
                        listener.onClick(item.copy(name = edName.text.toString(), itemInfo = itemInfo))
                    }
                    dialog?.dismiss()
                }
                dialog?.dismiss()

        }
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }

    interface Listener {
        fun onClick(item: ShopListItem)
    }
}