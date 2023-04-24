package com.example.firstapp.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.R
import com.example.firstapp.databinding.NoteListItemBinding
import com.example.firstapp.entities.NoteItem

class NoteAdapter: ListAdapter<NoteItem, NoteAdapter.ItemHolder>(NoteAdapter.ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
      return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
     holder.setData(getItem(position))
    }

    class ItemHolder(view:View) : RecyclerView.ViewHolder(view){
        private val bind = NoteListItemBinding.bind(view)

        fun setData(note:NoteItem) = with(bind){

            tvTittle.text = note.title
            tvDescription.text = note.content
            tvTime.text = note.time
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


}