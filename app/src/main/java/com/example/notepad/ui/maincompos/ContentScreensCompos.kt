package com.example.notepad.ui.maincompos

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notepad.R
import com.example.notepad.model.Note
import com.example.notepad.ui.destinations.DeatailsScreenDestination
import com.example.notepad.ui.home.RetrieveNotesViewModel
import com.example.notepad.util.NoteColors
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox


@Composable
fun EmptyHomeImage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.main_image),
            contentDescription = "",
            alignment = Alignment.Center
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteCard(
    nav: DestinationsNavigator,
    viewModel: RetrieveNotesViewModel,
    note: Note,
) {
    val delete = listOf(
        SwipeAction(
            onSwipe = { viewModel.deleteNote(note) },
            icon = painterResource(id = R.drawable.delete_24),
            background = Color(0xFFC53030), isUndo = true
        )
    )
    SwipeableActionsBox(endActions = delete, swipeThreshold = (365 / 2).dp) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .width(365.dp)
                .padding(10.dp)
                .clickable { nav.navigate(DeatailsScreenDestination(note.nId)) },
            backgroundColor = Color(note.nColor),
        ) {
            Text(
                text = note.nTitle,
                fontSize = 25.sp,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 50.dp, vertical = 35.dp),
                textAlign = TextAlign.Center
            )

        }
    }


}


@Composable
fun ShowToast(showToast: () -> Boolean, dismissToastState: () -> Unit, message: String) {
    if (showToast())
        Toast.makeText(
            LocalContext.current.applicationContext,
            message,
            Toast.LENGTH_SHORT
        ).show()
    dismissToastState()

}