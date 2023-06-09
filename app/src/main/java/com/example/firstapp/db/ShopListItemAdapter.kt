package com.example.firstapp.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.R
import com.example.firstapp.databinding.ListNameItemBinding
import com.example.firstapp.databinding.ShopListItemBinding
import com.example.firstapp.entities.ShopListNameItem
import com.example.firstapp.entities.ShopListItem

class ShopListItemAdapter(private var listenner: Listener): ListAdapter<ShopListItem, ShopListItemAdapter.ItemHolder>(ShopListItemAdapter.ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
      return if(viewType == 0) ItemHolder.createShopItem(parent)
      else ItemHolder.createLibraryItem(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
     if (getItem(position).itemType == 0) {holder.setItemData(getItem(position),listenner)}
        else {holder.setLibraryData(getItem(position),listenner)}
    }

    class ItemHolder(val view:View) : RecyclerView.ViewHolder(view){


        fun setItemData(shopListItem:ShopListItem, listener: Listener){
           val bind = ShopListItemBinding.bind(view)
            bind.apply {
                tvName.text = shopListItem.name
                tvInfo.text = shopListItem.itemInfo
                tvInfo.visibility = infoVisibility(shopListItem)
            }
        }

        fun infoVisibility(shopListItem:ShopListItem): Int{
            return if (shopListItem.itemInfo.isNullOrEmpty()){
                View.GONE
            } else {View.VISIBLE}

        }

        fun setLibraryData(shopListItem:ShopListItem, listener: Listener){

        }

        companion object{
            fun createShopItem (parent:ViewGroup) : ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context).
                    inflate(R.layout.shop_list_item,parent,false))
            }

            fun createLibraryItem (parent:ViewGroup) : ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context).
                    inflate(R.layout.shop_library_list_item,parent,false))
            }
        }
    }

    class ItemComparator: DiffUtil.ItemCallback<ShopListItem>(){
        override fun areItemsTheSame(oldItem: ShopListItem, newItem: ShopListItem): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: ShopListItem, newItem: ShopListItem): Boolean {
           return oldItem == newItem
        }

    }

    interface Listener{
        fun deleteItem(id:Int)
        fun editItem(shopListNameItem: ShopListNameItem)
        fun onClickItem(ShopListName:ShopListNameItem)

    }


}