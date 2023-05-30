package com.example.yogayu.ui.main.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun SearchScreen() {

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Search Screen",
            fontSize = 20.sp
        )
    }
}