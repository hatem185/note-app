package com.example.notepad.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notepad.model.Note
import com.example.notepad.model.NotePadDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RetrieveNotesViewModel @Inject constructor(private val notePadDB: NotePadDB) : ViewModel() {
    var notesList by mutableStateOf(emptyList<Note>())
        private set
    var note by mutableStateOf<Note?>(null)
        private set
    var openDialog by mutableStateOf(true)
        private set
    var openSearchBar by mutableStateOf(false)


    init {
        loadNotesList()
    }


    fun loadNotesList() {
        notePadDB.dao.getAllNotes().onEach { notes -> notesList = notes }.launchIn(viewModelScope)
    }

    fun getNoteWithId(id: Int) {
        notePadDB.dao.getNoteById(id).onEach { note -> this.note = note }.launchIn(viewModelScope)
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            notePadDB.dao.deleteNote(note)
        }
    }

    fun searchNotes(pattren: String) {
        notePadDB.dao.getNotesWithPattrenSearch(pattren).onEach { notes -> notesList = notes }
            .launchIn(viewModelScope)
    }

    fun changeDialogState(setState: Boolean) {
        openDialog = setState
    }

    fun changeSearchBarState(setState: Boolean) {
        openSearchBar = setState
    }
}