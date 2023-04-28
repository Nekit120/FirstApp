package com.example.firstapp.db

import androidx.room.Insert
import androidx.room.Query

import androidx.room.Update

import com.example.firstapp.entities.NoteItem
import kotlinx.coroutines.flow.Flow

@androidx.room.Dao

interface Dao {

  @Query("SELECT * FROM NoteItem")
  fun getAllNotes(): Flow<List<NoteItem>>

  @Insert
  suspend fun insertNow(note:NoteItem)

  @Query ("SELECT * FROM note_list")
  fun getAllItems(): kotlinx.coroutines.flow.Flow<List<NoteItem>>
  @Query ("DELETE FROM note_list WHERE id IS :id")
  suspend fun deleteNote(id:Int)
  @Insert
  suspend fun insertNote(note:NoteItem)
  @Update
  suspend fun updateNote(note:NoteItem)


}