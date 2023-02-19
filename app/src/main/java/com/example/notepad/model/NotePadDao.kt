package com.example.notepad.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NotePadDao {
    @Insert
    suspend fun insertNewNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note): Int

    @Query("DELETE FROM Note")
    suspend fun deleteAllNote(): Int

    @Query("SELECT * FROM Note")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM Note WHERE nId = :id")
    fun getNoteById(id: Int): Flow<Note>

    @Query("SELECT * FROM Note WHERE nTitle LIKE :pattren")
    fun getNotesWithPattrenSearch(pattren: String): Flow<List<Note>>
}