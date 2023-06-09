package com.example.firstapp.db

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
                chBox.isChecked = shopListItem.itemChecked
                setPaintFlagAndColor(bind)
                chBox.setOnClickListener{
                  //  setPaintFlagAndColor(bind)
                    listener.onClickItem(shopListItem.copy(itemChecked = chBox.isChecked))
                }
            }
        }

        private fun  setPaintFlagAndColor(bind: ShopListItemBinding){
            bind.apply {
                if (chBox.isChecked){
                    tvName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    tvInfo.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    tvName.setTextColor(ContextCompat.getColor(bind.root.context,R.color.grey_light))
                }else{
                    tvName.paintFlags = Paint.ANTI_ALIAS_FLAG
                    tvInfo.paintFlags = Paint.ANTI_ALIAS_FLAG
                    tvName.setTextColor(ContextCompat.getColor(bind.root.context,R.color.black))
                }
            }
        }


        private fun infoVisibility(shopListItem:ShopListItem): Int{
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
        fun onClickItem(ShopListNItem:ShopListItem)

    }


}