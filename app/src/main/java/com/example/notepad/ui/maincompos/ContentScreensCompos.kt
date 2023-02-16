package com.example.notepad.ui.maincompos

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun NoteCard(nav: DestinationsNavigator, note: Note, cardColor: Color) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .width(365.dp)
            .padding(10.dp)
            .clickable { nav.navigate(DeatailsScreenDestination(note.nId)) },
        backgroundColor = cardColor,
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

