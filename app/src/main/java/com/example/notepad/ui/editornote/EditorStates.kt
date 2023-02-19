package com.example.notepad.ui.editornote

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.notepad.model.Note

class EditorStates(
    updateNoteDialogState: Boolean = false,
    rollbackUpdateNoteDialogState: Boolean = false,
    successSaveToast: Boolean = false,
    fieldSaveToast: Boolean = false,
) {
    var updateNoteDialogState by mutableStateOf(updateNoteDialogState)
        private set
    var rollbackUpdateNoteDialogState by mutableStateOf(rollbackUpdateNoteDialogState)
        private set
    var successSaveToast by mutableStateOf(successSaveToast)
        private set
    var fieldSaveToast by mutableStateOf(fieldSaveToast)
        private set

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
