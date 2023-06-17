package com.example.firstapp.db

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.R
import com.example.firstapp.activities.MainApp
import com.example.firstapp.databinding.NoteListItemBinding
import com.example.firstapp.entities.NoteItem
import com.example.firstapp.utils.HtmlManager
import com.example.firstapp.utils.TimeMeneger

class NoteAdapter(private var listener: Listener, private val defPref: SharedPreferences,var mainViewModel: MainViewModel, val lifecycleOwner: LifecycleOwner): ListAdapter<NoteItem, NoteAdapter.ItemHolder>(NoteAdapter.ItemComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
      return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
     holder.setData(getItem(position),listener,defPref,mainViewModel,lifecycleOwner)
     }
    class ItemHolder(view:View) : RecyclerView.ViewHolder(view){
        private val bind = NoteListItemBinding.bind(view)
        fun setData(note:NoteItem, listener: Listener,defPref: SharedPreferences,mainViewModel: MainViewModel,lifecycleOwner: LifecycleOwner) = with(bind){
            tvTittle.text = note.title
            tvDescription.text = HtmlManager.getFromHtml(note.content).trim()
            tvTime.text = TimeMeneger.getTimeFormat(note.time,defPref)
            itemView.setOnClickListener{
                listener.onClickItem(note)
            }
            imDelete.setOnClickListener{
                listener.deleteItem(note.id!!)
            }
        }

        companion object{
            fun create (parent:ViewGroup) : ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context).
                    inflate(R.layout.note_list_item,parent,false))
            }
        }
    }

    class ItemComparator: DiffUtil.ItemCallback<NoteItem>(){
        override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
           return oldItem == newItem
        }

    }

    interface Listener{
        fun deleteItem(id:Int)
        fun onClickItem(note:NoteItem)

    }


}