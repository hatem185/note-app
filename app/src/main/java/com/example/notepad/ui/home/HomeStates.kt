package com.example.notepad.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


class HomeStates(openDialog: Boolean = true, openSearchBar: Boolean = false) {
    var openDialog by mutableStateOf(openDialog)
    var openSearchBar by mutableStateOf(openSearchBar)
    fun changeDialogState(setState: Boolean) {
        openDialog = setState
    }

    fun changeSearchBarState(setState: Boolean) {
        openSearchBar = setState
    }
}