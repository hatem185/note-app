package com.example.notepad.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
abstract class NotePadDB : RoomDatabase() {
    abstract val dao: NotePadDao
}