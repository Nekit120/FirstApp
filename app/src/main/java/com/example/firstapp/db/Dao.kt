package com.example.firstapp.db

import androidx.room.Insert
import androidx.room.Query

import androidx.room.Update

import com.example.firstapp.entities.NoteItem
import com.example.firstapp.entities.ShoppingListNames
import kotlinx.coroutines.flow.Flow

@androidx.room.Dao

interface Dao {

  @Query ("SELECT * FROM note_list")
  fun getAllItems(): kotlinx.coroutines.flow.Flow<List<NoteItem>>
  @Query ("SELECT * FROM shopping_list_names")
  fun getAllShopListNames(): kotlinx.coroutines.flow.Flow<List<ShoppingListNames>>
  @Query ("DELETE FROM note_list WHERE id IS :id")
  suspend fun deleteNote(id:Int)
  @Insert
  suspend fun insertNote(note:NoteItem)
  @Update
  suspend fun updateNote(note:NoteItem)
  @Insert
  suspend fun insertShopListName(name:ShoppingListNames)




}