package com.example.notepad.ui.editornote


import android.annotation.SuppressLint
import android.content.Context
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
    var txtTitle by mutableStateOf("")
    var txtContent by mutableStateOf("")
    var editNote by mutableStateOf<Note?>(null)
        private set
    var updateNoteDialogState by mutableStateOf(false)
        private set
    var rollbackUpdateNoteDialogState by mutableStateOf(false)
        private set
    var successSaveToast by mutableStateOf(false)
        private set
    var fieldSaveToast by mutableStateOf(false)
        private set

    fun addNewNote() {
        if (txtContent.trim().isNotEmpty() && txtTitle.trim().isNotEmpty()) {
            viewModelScope.launch {
                notePadDB.dao.insertNewNote(Note(nTitle = txtTitle, nContent = txtContent))
                successSaveToast = true
            }
            return
        }
        fieldSaveToast = true
    }

    fun updateNote() {
        editNote?.let {
            viewModelScope.launch {
                notePadDB.dao.updateNote(it.copy(nTitle = txtTitle, nContent = txtContent))
            }
        }
    }

    private fun isNoteUpdated(): Boolean {
        return if (editNote != null)
            editNote?.let {
                it.nContent.trim() != txtContent.trim() || it.nTitle.trim() != txtTitle.trim()
            }!!
        else false
    }

    fun onUpdatedNote(): Boolean {
        updateNoteDialogState = isNoteUpdated()
        return updateNoteDialogState
    }

    fun checkNoteChangeAtRollback(): Boolean {
        rollbackUpdateNoteDialogState = isNoteUpdated()
        return rollbackUpdateNoteDialogState
    }

    fun setEditNoteProperty(note: Note) {
        txtTitle = note.nTitle.trim()
        txtContent = note.nContent.trim()
        editNote = note.copy()
    }

    fun changeUpdateDialogState(dialogState: Boolean) {
        updateNoteDialogState = dialogState
    }

    fun changeSuccessSaveToastState(toastState: Boolean) {
        successSaveToast = toastState
    }

    fun changeFieldSaveToastState(toastState: Boolean) {
        successSaveToast = toastState
    }

    fun changeRollbackUpdateNoteDialogState(dialogState: Boolean) {
        rollbackUpdateNoteDialogState = dialogState
    }
}