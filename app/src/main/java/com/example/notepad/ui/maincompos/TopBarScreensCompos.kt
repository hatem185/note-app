package com.example.notepad.ui.maincompos

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.notepad.ui.editornote.EditorNoteViewModel
import com.example.notepad.ui.home.RetrieveNotesViewModel
import com.example.notepad.util.NoteColors

@Composable
fun SearchBarField(viewModel: RetrieveNotesViewModel) {
    val states = viewModel.states
    var textSearch by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = textSearch,
        onValueChange = { textSearch = it },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            viewModel.searchNotes("%$textSearch%")
            focusManager.clearFocus()
        }),
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color(0xFFCCCCCC),
            backgroundColor = Color(0xFF3B3B3B),
            cursorColor = Color.White,
            focusedBorderColor = Color.Transparent
        ),
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "",
                modifier = Modifier.clickable {
                    states.changeSearchBarState(false)
                    viewModel.loadNotesList()
                },
                tint = Color(0xFFCCCCCC)
            )
        }, leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "",
                tint = Color.White
            )
        }, placeholder = { Text(text = "Search by the keyword...", color = Color(0xFFCCCCCC)) },
        shape = RoundedCornerShape(75.dp), maxLines = 1
    )
}

@Composable
fun TopBarButton(
    icon: ImageVector,
    modifier: Modifier = Modifier.size(50.dp),
    hide: () -> Boolean = { false },
    onClickBtn: () -> Unit
) {
    if (hide()) {
        println("${hide()}")
        return
    }
    Button(
        onClick = onClickBtn,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3B3B3B)),
        modifier = modifier,
        shape = RoundedCornerShape(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "",
            tint = Color(0xFFFFFFFF),
            modifier = Modifier.size(35.dp)
        )
    }
}

@Composable
fun ColorMenuPicker(viewModel: EditorNoteViewModel) {
    val colors = NoteColors.cardsColorsLong
    var expanded by remember { mutableStateOf(false) }
    var selectedColor by remember { mutableStateOf(viewModel.editNote?.nColor ?: colors[0]) }
    var cardSize by remember { mutableStateOf(Size.Zero) }
    viewModel.noteColor = selectedColor
    Card(
        modifier = Modifier
            .size(45.dp)
            .onGloballyPositioned { coordinates ->
                cardSize = coordinates.size.toSize()
            }
            .clickable { expanded = !expanded },
        backgroundColor = Color(selectedColor),
        shape = RoundedCornerShape(18.dp), border = BorderStroke(1.dp, Color.White)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { cardSize.width.toDp() }),
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                colors.forEach { colorLong ->
                    DropdownMenuItem(
                        onClick = {
                            selectedColor = colorLong
                            viewModel.noteColor = selectedColor
                            expanded = false
                        },
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .size(40.dp),
                            backgroundColor = Color(colorLong),
                            shape = RoundedCornerShape(16.dp)
                        ) {}
                    }
                }
            }
        }
    }
}