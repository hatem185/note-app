package com.example.notepad.ui.editornote


import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notepad.model.Note
import com.example.notepad.model.NotePadDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class EditorNoteViewModel @Inject constructor(
    private val notePadDB: NotePadDB
) : ViewModel() {
    var editNote by mutableStateOf<Note?>(null)
        private set
    var txtTitle by mutableStateOf("")
    var txtContent by mutableStateOf("")
    var updateNoteDialogState by mutableStateOf(false)
        private set
    var cancelEditDialogState by mutableStateOf(false)
        private set

    fun addNewNote() {
        if (txtContent.trim().isNotEmpty() && txtTitle.trim().isNotEmpty()) {
            viewModelScope.launch {
                notePadDB.dao.insertNewNote(Note(nTitle = txtTitle, nContent = txtContent))
            }
        }

    }

    fun updateNote() {
        editNote?.let {
            viewModelScope.launch {
                notePadDB.dao.updateNote(
                    it.copy(
                        nTitle = txtTitle,
                        nContent = txtContent
                    )
                )
            }
        }
    }

    fun isNoteChangeOnUpdate() {
        editNote?.let {
            updateNoteDialogState =
                it.nContent.trim() != txtContent.trim() || it.nTitle.trim() != txtTitle.trim()
        }
    }

    fun setEditNoteProperty(note: Note) {
        txtTitle = note.nTitle.trim()
        txtContent = note.nContent.trim()
        editNote = note.copy()
    }

    fun changeUpdateDialogState(dialogState: Boolean) {
        updateNoteDialogState = dialogState
    }

    fun changeCancelEditDialogState(dialogState: Boolean) {
        cancelEditDialogState = dialogState
    }


}