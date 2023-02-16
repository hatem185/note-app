package com.example.notepad.ui.editornote

import androidx.activity.addCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner.current
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notepad.R
import com.example.notepad.model.Note
import com.example.notepad.ui.maincompos.TopBarButton
import com.example.notepad.util.NoteColors
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun EditorScreen(
    nav: DestinationsNavigator,
    viewModel: EditorNoteViewModel = hiltViewModel(),
    note: Note? = null
) {
    val onBackPressed = current?.onBackPressedDispatcher
    note?.let { viewModel.setEditNoteProperty(note) }
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    TopBarEditor(nav, viewModel)
                }
            },
            backgroundColor = NoteColors.backgroundColor,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = it), contentAlignment = Alignment.Center
            ) {
                ContentEditor(viewModel = viewModel)
            }
        }

    }
    onBackPressed?.addCallback { nav.popBackStack() }?.remove()
}

@Composable
fun TopBarEditor(nav: DestinationsNavigator, viewModel: EditorNoteViewModel) {
    TopAppBar(
        title = { },
        navigationIcon = {
            TopBarButton(
                icon = Icons.Default.ArrowBack,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(50.dp)
            ) { nav.popBackStack() }
        },
        actions = {
            if (viewModel.editNote != null)
                TopBarButton(icon = ImageVector.vectorResource(id = R.drawable.visibility_icon)) {
                    viewModel.changeCancelEditDialogState(true)
                }
            Spacer(modifier = Modifier.width(15.dp))
            TopBarButton(icon = ImageVector.vectorResource(id = R.drawable.save_icon)) {
                if (viewModel.editNote != null) {
                    viewModel.isNoteChangeOnUpdate()
                } else {
                    viewModel.addNewNote()
                    nav.popBackStack()
                }
            }
        },
        backgroundColor = NoteColors.backgroundColor,
        modifier = Modifier
            .width(365.dp)
            .padding(start = 0.dp, top = 10.dp, end = 10.dp, bottom = 10.dp),
        elevation = 0.dp
    )
    if (viewModel.updateNoteDialogState)
        UpdateDialog(
            viewModel = viewModel,
            dialogMessage = "Save changes ?",
            confirmMessage = "Save",
        ) {
            viewModel.updateNote()
            nav.popBackStack()
        }
    if (viewModel.cancelEditDialogState)
        UpdateDialog(
            viewModel = viewModel,
            dialogMessage = "Are your sure you want discard your changes ?",
            confirmMessage = "Keep",
        ) {
            nav.popBackStack()
        }
}

@Composable
fun UpdateDialog(
    viewModel: EditorNoteViewModel,
    dialogMessage: String,
    confirmMessage: String,
    onConfirmClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            viewModel.changeUpdateDialogState(false)
            viewModel.changeCancelEditDialogState(false)
        },
        text = {
            Text(
                text = dialogMessage,
                modifier = Modifier
                    .width(250.dp)
                    .wrapContentHeight(),
                color = Color(0xFFFCFCFC)
            )

        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        viewModel.changeUpdateDialogState(false)
                        viewModel.changeCancelEditDialogState(false)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                ) {
                    Text(text = "Discard", color = Color.White)
                }
                Spacer(modifier = Modifier.width(15.dp))
                Button(
                    onClick = onConfirmClick,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
                ) {
                    Text(text = confirmMessage, color = Color.White)
                }

            }
        },
        backgroundColor = NoteColors.backgroundColor
    )
}

@Composable
fun ContentEditor(viewModel: EditorNoteViewModel) {

    Column(
        modifier = Modifier
            .width(365.dp)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 15.dp)
    ) {
        OutlinedTextField(
            value = viewModel.txtTitle,
            onValueChange = { viewModel.txtTitle = it },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.White,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            textStyle = TextStyle(fontSize = 35.sp, color = Color.White),
            placeholder = { Text(text = "Title", fontSize = 35.sp, color = Color.White) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
        OutlinedTextField(
            value = viewModel.txtContent,
            onValueChange = { viewModel.txtContent = it },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.White,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            textStyle = TextStyle(fontSize = 23.sp, color = Color.White),
            placeholder = {
                Text(
                    text = "Type something...",
                    fontSize = 23.sp,
                    color = Color.White
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
    }
}