package com.example.notepad.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notepad.ui.destinations.EditorScreenDestination
import com.example.notepad.ui.maincompos.*
import com.example.notepad.util.NoteColors
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RequiresApi(Build.VERSION_CODES.R)
@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(nav: DestinationsNavigator, viewModel: RetrieveNotesViewModel = hiltViewModel()) {
    val states = viewModel.states
    Scaffold(
        topBar = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Center) {
                TopBarHome(
                    viewModel = viewModel
                )
            }
        },
        backgroundColor = NoteColors.backgroundColor,
        floatingActionButton = {
            FAB(icon = Default.Add) { nav.navigate(EditorScreenDestination()) }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it), contentAlignment = TopCenter
        ) {
            ContentHome(nav, viewModel)
        }
    }
    InfoDialog(
        openDialog = { states.openDialog },
        onDiscardRequest = { states.changeDialogState(false) }
    )
}

@Composable
fun TopBarHome(
    viewModel: RetrieveNotesViewModel
) {
    val states = viewModel.states
//    viewModel.deleteAllNote()
    TopAppBar(
        title = {
            if (states.openSearchBar) {
                SearchBarField(viewModel = viewModel)
            } else {
                Text(
                    text = "Notes",
                    fontSize = 43.sp,
                    color = Color(0xFFFFFFFF),
                    modifier = Modifier
                        .padding(0.dp)
                )
            }
        },
        actions = {
            if (!states.openSearchBar) {
                TopBarButton(icon = Icons.Outlined.Search) {
                    states.changeSearchBarState(true)
                }
                Spacer(modifier = Modifier.width(15.dp))
                TopBarButton(icon = Icons.Outlined.Info) {
                    states.changeDialogState(true)
                }
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
fun ContentHome(nav: DestinationsNavigator, viewModel: RetrieveNotesViewModel) {
    if (viewModel.notesList.isEmpty()) {
        EmptyHomeImage()
    } else {
        LazyColumn {
            items(viewModel.notesList) { note ->
                NoteCard(nav, viewModel, note)
            }
        }
    }
}

@Composable
fun FAB(icon: ImageVector, onClickAction: () -> Unit) {
    FloatingActionButton(
        onClick = onClickAction,
        modifier = Modifier.padding(20.dp),
        backgroundColor = NoteColors.backgroundColor,
        elevation = FloatingActionButtonDefaults.elevation(16.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "",
            tint = Color.White
        )
    }
}

@Composable
fun InfoDialog(onDiscardRequest: () -> Unit, openDialog: () -> Boolean) {
    if (openDialog()) {
        AlertDialog(
            onDismissRequest = onDiscardRequest,
            text = {
                Column {
                    Text(
                        text = "Designed by - \n\n" +
                                "Redesigned by - \n\n" +
                                "Illustrations - \n\n" +
                                "Icons - \n\n" +
                                "Font -\n\n",
                        modifier = Modifier
                            .width(250.dp)
                            .wrapContentHeight(), color = Color(0xFFFCFCFC)

                    )
                    Text(
                        text = "Made By",
                        Modifier.fillMaxWidth(),
                        color = Color(0xFFFCFCFC),
                        textAlign = TextAlign.Center
                    )
                }
            },
            buttons = { },
            backgroundColor = NoteColors.backgroundColor
        )
    }
}
