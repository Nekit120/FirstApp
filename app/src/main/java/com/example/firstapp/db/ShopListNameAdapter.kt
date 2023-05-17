package com.example.firstapp.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.R
import com.example.firstapp.databinding.ListNameItemBinding
import com.example.firstapp.databinding.NoteListItemBinding
import com.example.firstapp.entities.NoteItem
import com.example.firstapp.entities.ShoppingListNames
import com.example.firstapp.utils.HtmlManager

class ShopListNameAdapter(): ListAdapter<ShoppingListNames, ShopListNameAdapter.ItemHolder>(ShopListNameAdapter.ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
      return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
     holder.setData(getItem(position))
    }

    class ItemHolder(view:View) : RecyclerView.ViewHolder(view){
        private val bind = ListNameItemBinding.bind(view)

        fun setData(shopListNameItem:ShoppingListNames) = with(bind){

            tvListName.text = shopListNameItem.name
            tvTime.text = shopListNameItem.time
            itemView.setOnClickListener{
                }
            imDelete.setOnClickListener{
            }
        }

        companion object{
            fun create (parent:ViewGroup) : ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context).
                    inflate(R.layout.list_name_item,parent,false))
            }
        }
    }

    class ItemComparator: DiffUtil.ItemCallback<ShoppingListNames>(){
        override fun areItemsTheSame(oldItem: ShoppingListNames, newItem: ShoppingListNames): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShoppingListNames, newItem: ShoppingListNames): Boolean {
           return oldItem == newItem
        }

    }

    interface Listener{
        fun deleteItem(id:Int)
        fun onClickItem(note:NoteItem)

    }


}