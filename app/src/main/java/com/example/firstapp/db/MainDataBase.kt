package com.example.firstapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.firstapp.entities.LibraryItem
import com.example.firstapp.entities.NoteItem
import com.example.firstapp.entities.ShopListItem
import com.example.firstapp.entities.ShopListNameItem

@Database(entities = [LibraryItem::class, NoteItem::class,
    ShopListItem::class, ShopListNameItem::class], version = 1)
abstract class MainDataBase: RoomDatabase() {
    abstract fun getDao():Dao
    companion object{
        @Volatile
        private var INCTANCE: MainDataBase?=null
        
        fun getDatabase(context:Context):MainDataBase{
        return INCTANCE ?: synchronized(this){
            val instance = Room.databaseBuilder(
                context.applicationContext,
                MainDataBase::class.java,
                "shopping_list.db"
            ).build()
            instance }
        }
    }
}