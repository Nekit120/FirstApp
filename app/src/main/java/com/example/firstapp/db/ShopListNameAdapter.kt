package com.example.firstapp.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.R
import com.example.firstapp.databinding.ListNameItemBinding
import com.example.firstapp.entities.ShopListNameItem

class ShopListNameAdapter(private var listenner: Listener): ListAdapter<ShopListNameItem, ShopListNameAdapter.ItemHolder>(ShopListNameAdapter.ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
      return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
     holder.setData(getItem(position),listenner)
    }

    class ItemHolder(view:View) : RecyclerView.ViewHolder(view){
        private val bind = ListNameItemBinding.bind(view)

        fun setData(shopListNameItem:ShopListNameItem, listener: Listener) = with(bind){

            tvListName.text = shopListNameItem.name
            tvTime.text = shopListNameItem.time
            itemView.setOnClickListener{
                listener.onClickItem(shopListNameItem)
                }
            imDelete.setOnClickListener{
                listener.deleteItem(shopListNameItem.id!!)
            }
            imEdit.setOnClickListener {
                listener.editItem(shopListNameItem)
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

    class ItemComparator: DiffUtil.ItemCallback<ShopListNameItem>(){
        override fun areItemsTheSame(oldItem: ShopListNameItem, newItem: ShopListNameItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShopListNameItem, newItem: ShopListNameItem): Boolean {
           return oldItem == newItem
        }

    }

    interface Listener{
        fun deleteItem(id:Int)
        fun editItem(shopListNameItem: ShopListNameItem)
        fun onClickItem(ShopListName:ShopListNameItem)

    }


}