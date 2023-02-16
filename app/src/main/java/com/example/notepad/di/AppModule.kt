package com.example.notepad.di

import android.app.Application
import android.widget.Toast
import androidx.room.Room
import com.example.notepad.model.NotePadDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNotePadDB(app: Application): NotePadDB =
        Room.databaseBuilder(app, NotePadDB::class.java, "notePad").build()

}