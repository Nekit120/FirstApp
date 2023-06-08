package com.example.firstapp.db

import androidx.room.Insert
import androidx.room.Query

import androidx.room.Update

import com.example.firstapp.entities.NoteItem
import com.example.firstapp.entities.ShopListNameItem

@androidx.room.Dao

interface Dao {

  @Query ("SELECT * FROM note_list")
  fun getAllItems(): kotlinx.coroutines.flow.Flow<List<NoteItem>>
  @Query ("SELECT * FROM shopping_list_names")
  fun getAllShopListNames(): kotlinx.coroutines.flow.Flow<List<ShopListNameItem>>
  @Query ("DELETE FROM note_list WHERE id IS :id")
  suspend fun deleteNote(id:Int)
  @Query ("DELETE FROM shopping_list_names WHERE id IS :id")
  suspend fun deleteShopListName(id:Int)
  @Insert
  suspend fun insertNote(note:NoteItem)
  @Update
  suspend fun updateNote(note:NoteItem)
  @Update
  suspend fun updateListName(ShopListName:ShopListNameItem)
  @Insert
  suspend fun insertShopListName(name:ShopListNameItem)




}