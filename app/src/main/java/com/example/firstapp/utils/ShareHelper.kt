package com.example.firstapp.utils

import android.content.Intent
import com.example.firstapp.entities.ShopListItem

object ShareHelper {
    fun shareShopList(shopList : List<ShopListItem>, listName :String): Intent{
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plane"
        intent.apply {
            putExtra(Intent.EXTRA_TEXT, makeShareText(shopList,listName))
        }
        return intent
    }
    private fun makeShareText(shopList: List<ShopListItem>, listName: String): String{
        val sBuider = StringBuilder()
        sBuider.append("<<$listName>>")
        sBuider.append("\n")

        var counter = 0
        shopList.forEach{
            val itemInfo = if (it.itemInfo !=null) "(${it.itemInfo})" else ""
            if (it.itemChecked == false){
            sBuider.append("${++counter} -${it.name} ${itemInfo}")
            sBuider.append("\n")}
        }
        return sBuider.toString()
    }
}