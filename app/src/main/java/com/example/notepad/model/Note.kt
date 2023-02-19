package com.example.notepad.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val nId: Int = 0,
    val nTitle: String,
    val nContent: String,
    val nColor: Long
) : Parcelable
