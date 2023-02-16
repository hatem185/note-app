package com.example.notepad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.notepad.ui.NavGraphs
import com.example.notepad.ui.theme.NotePadTheme
import com.ramcosta.composedestinations.DestinationsNavHost

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotePadTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
//        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                println("hello")
//            }
//        })
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NotePadTheme {
    }
}