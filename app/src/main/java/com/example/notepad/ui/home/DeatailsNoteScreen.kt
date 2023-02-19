package com.example.notepad.ui.home

import androidx.activity.addCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notepad.ui.destinations.EditorScreenDestination
import com.example.notepad.ui.maincompos.TopBarButton
import com.example.notepad.util.NoteColors
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun DeatailsScreen(
    nav: DestinationsNavigator,
    viewModel: RetrieveNotesViewModel = hiltViewModel(),
    noteId: Int
) {
    val onBackPressed = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    viewModel.getNoteWithId(noteId)
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    TopBarDetails(nav, viewModel = viewModel)
                }
            },
            backgroundColor = NoteColors.backgroundColor
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = it), contentAlignment = Alignment.TopCenter
            ) {
                ContentDetails(viewModel = viewModel)
            }
        }
    }
    onBackPressed?.addCallback { nav.popBackStack() }?.remove()
}


@Composable
fun TopBarDetails(nav: DestinationsNavigator, viewModel: RetrieveNotesViewModel) {
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
            TopBarButton(icon = Icons.Default.Edit) {
                nav.navigate(EditorScreenDestination(viewModel.note))
            }
        },
        backgroundColor = NoteColors.backgroundColor,
        modifier = Modifier
            .width(365.dp)
            .padding(start = 0.dp, top = 10.dp, end = 10.dp, bottom = 10.dp),
        elevation = 0.dp
    )
}

@Composable
fun ContentDetails(viewModel: RetrieveNotesViewModel) {
    Column(
        modifier = Modifier
            .width(365.dp)
            .verticalScroll(rememberScrollState())
            .padding(vertical = 15.dp)
    ) {
        viewModel.note?.apply {
            BasicTextField(
                value = nTitle,
                onValueChange = { },
                readOnly = true,
                textStyle = TextStyle(fontSize = 35.sp, color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            BasicTextField(
                value = nContent,
                onValueChange = { },
                readOnly = true,
                textStyle = TextStyle(fontSize = 23.sp, color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }
    }
}