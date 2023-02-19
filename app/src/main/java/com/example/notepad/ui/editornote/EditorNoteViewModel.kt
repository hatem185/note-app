package com.example.notepad.ui.editornote


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
    var noteTitle by mutableStateOf("")
    var noteContent by mutableStateOf("")
    var noteColor by mutableStateOf(0L)
    var editNote by mutableStateOf<Note?>(null)
        private set
    var states by mutableStateOf(EditorStates())
        private set

    fun addNewNote() {
        if (noteContent.trim().isNotEmpty() && noteTitle.trim().isNotEmpty()) {
            viewModelScope.launch {
                notePadDB.dao.insertNewNote(
                    Note(
                        nTitle = noteTitle,
                        nContent = noteContent,
                        nColor = noteColor
                    )
                )
                states.changeSuccessSaveToastState(true)
            }
            return
        }
        states.changeFieldSaveToastState(true)
    }

    fun updateNote() {
        editNote?.let {
            viewModelScope.launch {
                notePadDB.dao.updateNote(
                    it.copy(
                        nTitle = noteTitle,
                        nContent = noteContent,
                        nColor = noteColor
                    )
                )
            }
        }
    }

    private fun isNoteUpdated(): Boolean {
        return if (editNote != null)
            editNote?.let {
                it.nContent.trim() != noteContent.trim() || it.nTitle.trim() != noteTitle.trim() || it.nColor != noteColor
            }!!
        else false
    }

    fun onUpdatedNote(): Boolean {
        states.changeUpdateDialogState(isNoteUpdated())
        return states.updateNoteDialogState
    }

    fun checkNoteChangeAtRollback(): Boolean {
        states.changeRollbackUpdateNoteDialogState(isNoteUpdated())
        return states.rollbackUpdateNoteDialogState
    }

    fun setEditNoteProperty(note: Note) {
        noteTitle = note.nTitle.trim()
        noteContent = note.nContent.trim()
        editNote = note.copy()
    }

}