package com.example.firstapp.db

import androidx.room.Insert
import androidx.room.Query
import com.example.firstapp.entities.NoteItem
import kotlinx.coroutines.flow.Flow

@androidx.room.Dao

interface Dao {
  @Query("SELECT * FROM NoteItem")
  fun getAllNotes(): Flow<List<NoteItem>>

  @Insert
  suspend fun insertNow(note:NoteItem)
}